package com.example.projet.metier;

import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface EmpManagement<T,PK> {
    boolean addEmploye(T obj) throws SQLException;
    ObservableList<T> getEmployes();
    T getEmployeById(PK id);
    boolean updateEmploye(PK Id,T obj);
    boolean deleteEmployeById(PK Id);
    boolean deleteEmployeByName(String name);
    List<T> getEmployesByIdOfDepartment(String Id);
    List<T> getEmployesByNameOfDepartment(String name);
    boolean reafecterEmploye(T obj,String Idr);
}
