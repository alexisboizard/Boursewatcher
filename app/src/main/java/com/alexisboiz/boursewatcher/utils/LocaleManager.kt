package com.alexisboiz.boursewatcher.utils

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import java.util.Locale

object LocaleManager {
    private const val LANGUAGE_KEY = "LANGUAGE"

    fun setLocale(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val configuration = Configuration()
        configuration.setLocale(locale)

        // Mettre à jour la configuration avant d'accéder à getResources()
        context.createConfigurationContext(configuration)

        val editor: SharedPreferences.Editor =
            context.getSharedPreferences("settings", Context.MODE_PRIVATE).edit()
        editor.putString(LANGUAGE_KEY, language)
        editor.apply()
    }

    fun getSavedLocale(context: Context): Locale {
        val sharedPreferences =
            context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val language = sharedPreferences.getString(LANGUAGE_KEY, "fr")
        return Locale(language!!)
    }
}