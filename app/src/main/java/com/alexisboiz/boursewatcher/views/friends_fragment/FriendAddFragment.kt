package com.alexisboiz.boursewatcher.views.friends_fragment

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.alexisboiz.boursewatcher.R
import com.alexisboiz.boursewatcher.adapters.FriendAddAdapter
import com.alexisboiz.boursewatcher.model.AuthModel.FirebaseUser
import com.alexisboiz.boursewatcher.views.QRScannerActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FriendAddFragment : Fragment() {
    lateinit var firestore: FirebaseFirestore
    val REQUEST_CODE = 200
    var displayName: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friend_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val startCameraButton = view.findViewById<MaterialButton>(R.id.btn_scan)
        val addButton = view.findViewById<MaterialButton>(R.id.btn_add_friend)
        val friendCode = view.findViewById<EditText>(R.id.et_add_friend)
        val recyclerView =
            view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rv_add_friend)
        firestore = FirebaseFirestore.getInstance()
        val listOfUser = mutableListOf<FirebaseUser>()

        recyclerView.apply {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
            adapter = adapter
        }

        startCameraButton.setOnClickListener {
            val intent = Intent(context, QRScannerActivity::class.java)
            startActivity(intent)
        }
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid
        val friendsRef = firestore.collection("users/${uid}/friendList")
        friendsRef.get()
            .addOnSuccessListener {
                for (document in it) {
                    Log.d("friend", "${document.id} => ${document.data}")
                    if (document.data["status"] == "accepted") {
                        //affichage des amis avec un recyclerview
                        val friendRef = firestore.collection("users")
                            .document(document.data["friendId"].toString())
                        friendRef.get().addOnSuccessListener {
                            val user = FirebaseUser(
                                it.data?.get("displayName").toString(),
                                document.data["friendId"].toString(),
                                it.data?.get("profilPicture").toString()
                            )
                            listOfUser.add(user)
                            recyclerView.adapter = FriendAddAdapter(listOfUser)
                        }
                    }

                }
            }

        addButton.setOnClickListener {
            val friendCode = friendCode.text.toString()
            val friendRef = firestore.collection("users")
            firestore.collection("users").document(uid.toString()).get()
                .addOnSuccessListener {
                    if (friendCode == it.data?.get("friendCode").toString()) {
                        Snackbar.make(
                            recyclerView,
                            getString(R.string.ERROR_add_self),
                            Snackbar.LENGTH_LONG
                        )
                            .show()
                    }
                }
            friendRef.get()
                .addOnSuccessListener {
                    for (document in it) {
                        if (document.data["friendCode"] == friendCode) {
                            val friendId = document.id
                            val userFriendList = firestore.collection("users/${uid}/friendList")
                            userFriendList.get()
                                .addOnSuccessListener {
                                    var friendExist = false
                                    for (document2 in it) {
                                        if (document2.data["friendId"] == friendId) {
                                            friendExist = true
                                        }
                                    }
                                    if (!friendExist) {
                                        val friend = hashMapOf(
                                            "status" to "not_accepted",
                                            "friendId" to friendId
                                        )
                                        userFriendList.add(friend)
                                    }else{
                                        Snackbar.make(
                                            recyclerView,
                                            getString(R.string.ERROR_friend_exist),
                                            Snackbar.LENGTH_LONG
                                        )
                                            .show()
                                    }
                                }
                            // ajout dans la liste de l'amis
                            val friendList = firestore.collection("users/${friendId}/friendList")
                            friendList.get()
                                .addOnSuccessListener {
                                    var friendExist = false
                                    for (document2 in it) {
                                        if (document2.data["friendId"] == uid) {
                                            friendExist = true
                                        }
                                    }
                                    if (!friendExist) {
                                        val friend = hashMapOf(
                                            "status" to "pending",
                                            "friendId" to uid
                                        )
                                        friendList.add(friend)
                                    }
                                }
                        }
                    }
                }
            QRScannerActivity.friendNameLiveData.observe(viewLifecycleOwner){
                Snackbar.make(
                    recyclerView,
                    "$it ajouté dans vos amis",
                    Snackbar.LENGTH_LONG
                )
                    .show()
            }
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val builder = AlertDialog.Builder(context)

                // Configure la boîte de dialogue
                builder.setTitle("Supprimer votre ami")
                    .setMessage("Voulez-vous vraiment supprimer votre ami ?")
                    .setPositiveButton("Oui") { dialog, _ ->
                        // Code à exécuter lorsque l'utilisateur appuie sur le bouton "OK"
                        dialog.dismiss() // Ferme la boîte de dialogue
                        // Affiche la boîte de dialogue
                        val dialog = builder.create()
                        dialog.show()
                        val deletedItem = listOfUser.get(viewHolder.bindingAdapterPosition)
                        val position = viewHolder.bindingAdapterPosition
                        listOfUser.remove(deletedItem)
                        recyclerView.adapter?.notifyItemRemoved(position)
                        firestore.collection("users/${uid}/friendList").get()
                            .addOnSuccessListener {
                                for (document in it){
                                    if(deletedItem.uid == document.data["friendId"]){
                                        document.reference.delete()
                                    }
                                }
                            }
                        firestore.collection("users/${deletedItem.uid}/friendList").get()
                            .addOnSuccessListener {
                                for (document in it){
                                    if(uid == document.data["friendId"]){
                                        document.reference.delete()
                                    }
                                }
                            }
                        Snackbar.make(recyclerView, "Deleted your friend ${deletedItem.displayName}", Snackbar.LENGTH_LONG)
                            .show()
                    }
                // Affiche la boîte de dialogue
                val dialog = builder.create()
                dialog.show()

            }
        }).attachToRecyclerView(recyclerView)
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            FriendAddFragment()
    }


}