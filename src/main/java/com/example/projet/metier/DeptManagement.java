package com.example.projet.metier;

import javafx.beans.Observable;
import javafx.collections.ObservableList;

import java.util.List;

public interface DeptManagement<T,PK>{
    boolean updateDepartment(T obj,PK Id);
    boolean addDepartment(T obj);
    int totalEmployes();
    T MaxEmployesByDepartment();
    int nomberOfEmployeByDepartment(PK Id);
    Float masseSalarialeEntreprise();
    Float masseSalarialeDepartment(PK Id);
    boolean deleteDepartment(String departmentId);
    ObservableList<T> getDepartements();
    List<String> getDepartmentIds();
}
