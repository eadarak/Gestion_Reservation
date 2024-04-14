package sn.uasz.gestion_reservation_uasz.models;

/**
 * Énumération représentant les différents types de rôles dans le système de gestion des réservations.
 */
public enum TypeRole {
    /**
     * Utilisateur standard du système.
     */
    UTILISATEUR,
    
    /**
     * Responsable d'une partie du système, avec des autorisations supplémentaires.
     */
    RESPONSABLE,
    
    /**
     * Administrateur du système, avec des privilèges étendus.
     */
    ADMINISTRATEUR
}
