package com.example.projet.metier;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoEmploye implements EmpManagement<Employe,Integer>{
    Connection MyCon=DaoFactory.getConnexion();
    @Override
    public boolean addEmploye(Employe employee) throws SQLException {
        String query = "INSERT INTO Employee (NomEmp, Salaire, Age, RefDept) VALUES ( ?, ?, ?, ?)";
        try (
            PreparedStatement pstmt = MyCon.prepareStatement(query)) {
            pstmt.setString(1, employee.getNomEmp());
            pstmt.setFloat(2, employee.getSalaire());
            pstmt.setInt(3, employee.getAge());
            pstmt.setString(4, employee.getRefDept());
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Employé inseré avec succès" );
                return true;
            } else {
                System.out.println("Failed in adding employee.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error in addEmploye query: " + e.getMessage());
            return false;
        }
    }

    @Override
    public ObservableList<Employe> getEmployes() {
        List<Employe> employeList = new ArrayList<>();
        String query = "SELECT * FROM Employee";
        try (
                Connection connection = DaoFactory.getConnexion();
                PreparedStatement pstmt = connection.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Employe employe = Employe.builder()
                        .IdEmp(rs.getInt("IdEmp"))
                        .NomEmp(rs.getString("NomEmp"))
                        .Salaire(rs.getFloat("Salaire"))
                        .Age(rs.getInt("Age"))
                        .RefDept(rs.getString("RefDept"))
                        .build();
                employeList.add(employe);
            }
        } catch (SQLException e) {
            System.out.println("Error in getEmployes query: " + e.getMessage());
        }
        return FXCollections.observableList(employeList);
    }

    @Override
    public Employe getEmployeById(Integer id) {
        String sql = "SELECT * FROM employee WHERE idEmp = ?";
        try (Connection connection = DaoFactory.getConnexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Employe employe = new Employe();
                    employe.setIdEmp(resultSet.getInt("idEmp"));
                    employe.setNomEmp(resultSet.getString("nomEmp"));
                    employe.setAge(resultSet.getInt("age"));
                    employe.setSalaire(resultSet.getFloat("salaire"));
                    employe.setRefDept(resultSet.getString("refDept"));
                    return employe;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public boolean updateEmploye(Integer id, Employe updatedEmploye) {
        String query = "UPDATE Employee SET NomEmp = ?, Salaire = ?, Age = ?, RefDept = ? WHERE IdEmp = ?";

        try (Connection connection = DaoFactory.getConnexion();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, updatedEmploye.getNomEmp());
            pstmt.setFloat(2, updatedEmploye.getSalaire());
            pstmt.setInt(3, updatedEmploye.getAge());
            pstmt.setString(4, updatedEmploye.getRefDept());
            pstmt.setInt(5, id);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Employee modifié avec succès");
                return true;
            } else {
                System.out.println("Failed to update employee. Employee with Id " + id + " not found.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error in updateEmploye query: " + e.getMessage());
            return false;
        }
    }


    @Override
    public boolean deleteEmployeById(Integer Id) {
        try (Connection MyCon = DaoFactory.getConnexion()) {
            String sql = "DELETE FROM Employee WHERE IdEmp = ?";
            try (PreparedStatement pstmt = MyCon.prepareStatement(sql)) {
                pstmt.setInt(1, Id);
                int rowsAffected = pstmt.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteEmployeByName(String name) {
        try (Connection MyCon = DaoFactory.getConnexion()) {
            String sql = "DELETE FROM Employee WHERE NomEmp = ?";
            try (PreparedStatement pstmt = MyCon.prepareStatement(sql)) {
                pstmt.setString(1, name);
                int rowsAffected = pstmt.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Employe> getEmployesByIdOfDepartment(String Id) {
        List<Employe> employes = new ArrayList<>();
        try (Connection MyCon = DaoFactory.getConnexion()) {
            String sql = "SELECT * FROM Employee WHERE RefDept = ?";
            try (PreparedStatement pstmt = MyCon.prepareStatement(sql)) {
                pstmt.setString(1, Id);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        Employe employe = new Employe();
                        employe.setIdEmp(rs.getInt("IdEmp"));
                        employe.setNomEmp(rs.getString("NomEmp"));
                        employe.setSalaire(rs.getFloat("Salaire"));
                        employe.setAge(rs.getInt("Age"));
                        employe.setRefDept(rs.getString("RefDept"));
                        employes.add(employe);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employes;
    }


    @Override
    public List<Employe> getEmployesByNameOfDepartment(String name) {
        List<Employe> employes = new ArrayList<>();
        try (Connection MyCon = DaoFactory.getConnexion()) {
            String sql = "SELECT emp.* FROM Departement dept JOIN Employee emp  ON emp.RefDept = dept.IdDept WHERE dept.NomDept = ?";
            try (PreparedStatement pstmt = MyCon.prepareStatement(sql)) {
                pstmt.setString(1, name);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        Employe employe = new Employe();
                        employe.setIdEmp(rs.getInt("IdEmp"));
                        employe.setNomEmp(rs.getString("NomEmp"));
                        employe.setSalaire(rs.getFloat("Salaire"));
                        employe.setAge(rs.getInt("Age"));
                        employe.setRefDept(rs.getString("RefDept"));
                        employes.add(employe);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employes;
    }

    public boolean reafecterEmploye(Employe employe, String RefDept) {
        if (employe == null) {
            System.err.println("Error: employe is null!");
            return false;
        }

        try (Connection MyCon = DaoFactory.getConnexion()) {
            String sql = "UPDATE Employee SET RefDept = ? WHERE IdEmp = ?";

            try (PreparedStatement pstmt = MyCon.prepareStatement(sql)) {
                pstmt.setString(1, RefDept);
                pstmt.setInt(2, employe.getIdEmp());

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    return true;
                } else {
                    System.err.println("No rows affected. Check if the employee ID exists.");
                    return false;
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            // Handle foreign key constraint violation
            System.err.println("Foreign key constraint violation. Check if the department ID exists.");
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
