package com.example.projet.metier.Validators;

public class ValidatorEmploye {

    public static void validateEmployeeFields(String nom, String ageText, String salaireText, String idDepartmentText) throws IllegalArgumentException {
        if (nom.isEmpty() || ageText.isEmpty() || salaireText.isEmpty() || idDepartmentText.isEmpty()) {
            throw new IllegalArgumentException("Vous devez remplir tous les champs");
        }
        validateAge(ageText);
        validateSalaire(salaireText);
    }

    private static void validateAge(String ageText) throws IllegalArgumentException {
        try {
            int age = Integer.parseInt(ageText);
            if (age <= 0) {
                throw new IllegalArgumentException("L'âge doit être un nombre entier positif.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Format invalide pour l'âge.");
        }
    }

    private static void validateSalaire(String salaireText) throws IllegalArgumentException {
        try {
            float salaire = Float.parseFloat(salaireText);
            if (salaire < 0) {
                throw new IllegalArgumentException("Le salaire doit être un nombre positif.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Format invalide pour le salaire.");
        }
    }

}
