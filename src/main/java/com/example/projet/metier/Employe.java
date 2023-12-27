package com.example.projet.metier;


import lombok.*;
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Employe {
    private int IdEmp;
    private String NomEmp;
    private Float Salaire;
    private int Age;
    private String RefDept;

    public Employe(String nom, int age, float salaire, String idDepartment) {
        NomEmp=nom;Age=age;Salaire=salaire;RefDept=idDepartment;
    }
}
