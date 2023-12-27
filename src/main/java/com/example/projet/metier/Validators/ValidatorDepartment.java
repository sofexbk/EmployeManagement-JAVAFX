package com.example.projet.metier.Validators;

import com.example.projet.metier.DaoFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ValidatorDepartment {
    static Connection MyCon= DaoFactory.getConnexion();
    public static boolean isDepartmentExists(String departmentId) throws SQLException {
        String query = "SELECT * FROM departement WHERE IdDept = ?";
        try (PreparedStatement preparedStatement = MyCon.prepareStatement(query)) {
            preparedStatement.setString(1, departmentId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

}
