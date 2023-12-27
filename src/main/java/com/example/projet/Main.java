package com.example.projet;

import com.example.projet.metier.*;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        DaoEmploye daoEmploye=new DaoEmploye();
        /*Employe employe=Employe.builder()
                .NomEmp("EMP TEST")
                .Salaire(5200.0F)
                .Age(23)
                .RefDept("RH")
                .build();
        daoEmploye.addEmploye(employe);*/
        //System.out.println(daoEmploye.getEmployes());
        /*Employe employe= Employe.builder()
                .NomEmp("New after update")
                .Salaire(25000F)
                .Age(26)
                .RefDept("RH").build();
        daoEmploye.updateEmploye(1,employe);*/
        //System.out.println(daoEmploye.getEmployeById(2));
        //daoEmploye.deleteEmployeById(5);
        //daoEmploye.deleteEmployeByName("Soufian");
        //System.out.println(daoEmploye.getEmployesByIdOfDepartment("RH"));
        //System.out.println(daoEmploye.getEmployesByNameOfDepartment("Informatique"));
        /*Employe emp=daoEmploye.getEmployeById(4);
        System.out.println(emp);
         boolean emp1= daoEmploye.reafecterEmploye(emp.getIdEmp(),"RH");
        System.out.println(emp1);*/
       // DaoDepartement daoDepartement=new DaoDepartement();
       /* Departement departement=Departement.builder()
                .IdDept("ADV")
                .NomDept("Advertissement")
                .build();
        daoDepartement.addDepartment(departement);*/
        /*System.out.println(daoDepartement.totalEmployes());
        System.out.println(daoDepartement.MaxEmployesByDepartment());
        System.out.println(daoDepartement.nomberOfEmployeByDepartment("INFO"));
        System.out.println(daoDepartement.nomberOfEmployeByDepartment("RH"));
        System.out.println(daoDepartement.nomberOfEmployeByDepartment("ADV"));
        System.out.println(daoDepartement.nomberOfEmployeByDepartment("MRK"));
        System.out.println(daoDepartement.masseSalarialeEntreprise()+" Dhs");
        System.out.println(daoDepartement.masseSalarialeDepartment("INFO")+" Dhs");
        System.out.println(daoDepartement.masseSalarialeDepartment("RH")+" Dhs");
        System.out.println(daoDepartement.masseSalarialeDepartment("ADV")+" Dhs");
        System.out.println(daoDepartement.masseSalarialeDepartment("MRK")+" Dhs");*/
        //daoDepartement.deleteDepartment("ADV");
        //ObservableList<Employe> employeObservableList=daoEmploye.getEmployes();
        //System.out.println(employeObservableList);
        //System.out.println(daoEmploye.getEmployesByNameOfDepartment("Informatique"));
        //System.out.println(daoEmploye.getEmployesByIdOfDepartment("RH"));
        //EmployeController employeController=new EmployeController();
        //employeController.populateEmployeeTable();
        DaoDepartement daoDepartement=new DaoDepartement();
        //System.out.println(daoDepartement.getDepartements());
        //System.out.println(daoDepartement.masseSalarialeEntreprise());
        System.out.println(daoDepartement.nomberOfEmployeByDepartment("RH"));
        System.out.println(daoDepartement.masseSalarialeDepartment("INFO"));
    }
}
