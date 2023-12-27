// DepartementController.java

package com.example.projet;

import com.example.projet.metier.DaoDepartement;
import com.example.projet.metier.Departement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.lang.String;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DepartementController implements Initializable {
    @FXML
    public TextField departmentIdField;
    @FXML
    public TextField newDepartmentNameField;
    @FXML
    public TextField departmentNameField;
    @FXML
    public Label totalEmployeesLabel;
    @FXML
    public Label maxEmployeesDepartmentLabel;
    @FXML
    public Label totalSalaryCompanyLabel;
    public Text numberOfEmployeesInDepartment;
    public Text totalSalaryDepartment;
    public ChoiceBox departmentChoiceBox;
    public TextField departmentNameTextField;
    public TextField departmentIdTextField;
    public Label statusLabel;
    private DaoDepartement daoDepartement;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.daoDepartement = new DaoDepartement();
        try {
            totalEmployeesLabel.setText(String.valueOf(daoDepartement.totalEmployes()));
            Departement maxEmployeesDepartment = daoDepartement.MaxEmployesByDepartment();
            maxEmployeesDepartmentLabel.setText(maxEmployeesDepartment != null ? maxEmployeesDepartment.getNomDept() : "");
            totalSalaryCompanyLabel.setText(daoDepartement.masseSalarialeEntreprise() + " Dhs");
            List<String> departmentIds = daoDepartement.getDepartmentIds();
            ObservableList<String> departmentIdList = FXCollections.observableArrayList(departmentIds);
            departmentChoiceBox.setItems(departmentIdList);
            departmentChoiceBox.setOnAction(event -> handleDepartmentSelection());
            handleDepartmentSelection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void handleUpdateDepartment() {
        String departmentId = departmentIdField.getText();
        String newDepartmentName = newDepartmentNameField.getText();
        if (departmentId.isEmpty() || newDepartmentName.isEmpty()) {
            showAlert("Error", "Please enter both Department ID and New Department Name.");
            return;
        }
        Departement updatedDepartment = new Departement();
        updatedDepartment.setNomDept(newDepartmentName);
        boolean success = daoDepartement.updateDepartment(updatedDepartment, departmentId);

        if (success) {
            showAlert("Update Successful", "Department updated successfully!");
        } else {
            showAlert("Update Failed", "Failed to update department. Please try again.");
        }
    }

    public void handleDeleteDepartment() {
        String departmentId = departmentIdField.getText();
        if (departmentId.isEmpty()) {
            showAlert("Error", "Please enter Department ID.");
            return;
        }
        boolean success = daoDepartement.deleteDepartment(departmentId);
        if (success) {
            showAlert("Deletion Successful", "Department deleted successfully!");
        } else {
            showAlert("Deletion Failed", "Failed to delete department. Please try again.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearFieldsDept() {
        departmentIdField.clear();
        newDepartmentNameField.clear();
        departmentNameField.clear();
    }
    public void handleCancel() {
        clearFieldsDept();
    }

    @FXML
    public void handleDepartmentSelection() {
        String selectedDepartment = (String) departmentChoiceBox.getValue();
        if (selectedDepartment != null) {
            System.out.println("Selected Department: " + selectedDepartment);
            updateStatistics(selectedDepartment);
        } else {
            System.out.println("No department selected");
        }
    }

    private void updateStatistics(String selectedDepartment) {
        System.out.println("Updating statistics for department: " + selectedDepartment);
        int numberOfEmployees = daoDepartement.nomberOfEmployeByDepartment(selectedDepartment);
        System.out.println("Number of Employees: " + numberOfEmployees);
        numberOfEmployeesInDepartment.setText(String.valueOf(numberOfEmployees));
        float totalSalary = daoDepartement.masseSalarialeDepartment(selectedDepartment);
        System.out.println("Total Salary: " + totalSalary);
        totalSalaryDepartment.setText(totalSalary + " DHS");
    }

    public void addDepartment(ActionEvent actionEvent) {
        String departmentId = departmentIdTextField.getText();
        String departmentName = departmentNameTextField.getText();
        if (departmentId.isEmpty() || departmentName.isEmpty()) {
            showAlert("Error", "Please enter both Department ID and Department Name.");
            return;
        }
        boolean success = daoDepartement.addDepartment(new Departement(departmentId, departmentName));
        if (success) {
            showAlert("Addition Successful", "New department added successfully!");
            departmentNameTextField.setText("");departmentIdTextField.setText("");
        } else {
            showAlert("Addition Failed", "Failed to add new department. Please try again.");
        }
    }
}

