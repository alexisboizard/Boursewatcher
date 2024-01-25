package com.alexisboiz.boursewatcher.utils

enum class UserError(val message : String) {
    ERROR("Erreur création compte"),
    LOGIN_ERROR("Erreur lors de la connexion"),
    USER_NOT_FOUND("Utilisateur non trouvé"),
    WRONG_PASSWORD("Mot de passe incorrect"),
    UNKNOWN_ERROR("Erreur inconnue"),
    EMAIL_ALREADY_USED("Email déjà utilisé"),
    EMAIL_NOT_VALID("Email non valide"),
    EMPTY_FIELD("Champ(s) vide(s)"),
    PROFILE_PICTURE_ERROR("Erreur lors de l'upload de l'image"),
    TRY_AGAIN_LATER("Veuillez réessayer plus tard"),
}