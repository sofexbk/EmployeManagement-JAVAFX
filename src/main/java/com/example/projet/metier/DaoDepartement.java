package com.example.projet.metier;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoDepartement implements DeptManagement<Departement, String> {
    Connection MyCon = DaoFactory.getConnexion();

    @Override
    public boolean updateDepartment(Departement obj, String Id) {
        try {
            String sql = "UPDATE Departement SET NomDept = ? WHERE IdDept = ?";
            try (PreparedStatement pstmt = MyCon.prepareStatement(sql)) {
                pstmt.setString(1, obj.getNomDept());
                pstmt.setString(2, Id);
                int rowsAffected = pstmt.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addDepartment(Departement obj) {
        try {
            String sql = "INSERT INTO Departement (IdDept, NomDept) VALUES (?, ?)";
            try (PreparedStatement pstmt = MyCon.prepareStatement(sql)) {
                pstmt.setString(1, obj.getIdDept());
                pstmt.setString(2, obj.getNomDept());
                int rowsAffected = pstmt.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int totalEmployes() {
        try {
            String sql = "SELECT COUNT(*) FROM Employee";
            try (PreparedStatement pstmt = MyCon.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Departement MaxEmployesByDepartment() {
        try {
            String sql = "SELECT d.* FROM Departement d JOIN Employee e ON d.IdDept = e.RefDept GROUP BY d.IdDept ORDER BY COUNT(e.IdEmp) DESC ";
            try (PreparedStatement pstmt = MyCon.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Departement(rs.getString("IdDept"), rs.getString("NomDept"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Float masseSalarialeDepartment(String Id) {
        System.out.println("Department ID: " + Id);
        try {
            String sql = "SELECT SUM(Salaire) FROM Employee WHERE RefDept = ?";
            System.out.println("SQL Query: " + sql); // Add this line
            try (PreparedStatement pstmt = MyCon.prepareStatement(sql)) {
                pstmt.setString(1, Id);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        float totalSalary = rs.getFloat(1);
                        System.out.println("Total Salary for Department " + Id + ": " + totalSalary);
                        return totalSalary;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int nomberOfEmployeByDepartment(String Id) {
        System.out.println("Department ID: " + Id);
        try {
            String sql = "SELECT COUNT(*) FROM Employee WHERE RefDept = ?";
            System.out.println("SQL Query: " + sql); // Add this line
            try (PreparedStatement pstmt = MyCon.prepareStatement(sql)) {
                pstmt.setString(1, Id);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int count = rs.getInt(1);
                        System.out.println("Nombres d'employees de department " + Id + ": " + count);
                        return count;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Float masseSalarialeEntreprise() {
        try {
            String sql = "SELECT SUM(Salaire) FROM Employee";
            try (PreparedStatement pstmt = MyCon.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getFloat(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0f;
    }
    @Override
    public boolean deleteDepartment(String departmentId) {
        String query = "DELETE FROM Departement WHERE IdDept = ?";

        try (PreparedStatement pstmt = MyCon.prepareStatement(query)) {
            pstmt.setString(1, departmentId);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Department supprimé avec succès");
                return true;
            } else {
                System.out.println("Failed to delete department. Department with ID " + departmentId + " not found.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error in deleteDepartment query: " + e.getMessage());
            return false;
        }
    }

    @Override
    public ObservableList<Departement> getDepartements() {
        List<Departement> departments = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Departement";
            try (PreparedStatement pstmt = MyCon.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String idDept = rs.getString("IdDept");
                    String nomDept = rs.getString("NomDept");
                    Departement department = new Departement(idDept, nomDept);
                    departments.add(department);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return FXCollections.observableArrayList(departments);
    }


    @Override
    public List<String> getDepartmentIds() {
        List<String> departmentIds = new ArrayList<>();
        try {
            String sql = "SELECT IdDept FROM Departement";
            try (PreparedStatement pstmt = MyCon.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String idDept = rs.getString("IdDept");
                    departmentIds.add(idDept);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departmentIds;
    }

}
