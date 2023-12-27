package com.example.projet;

import com.example.projet.metier.DaoEmploye;
import com.example.projet.metier.Departement;
import com.example.projet.metier.Employe;
import com.example.projet.metier.Validators.ValidatorDepartment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import com.example.projet.metier.Validators.ValidatorEmploye;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import com.example.projet.metier.DaoDepartement;
public class EmployeController implements Initializable{

    @FXML
    public ComboBox employeeComboBox;
    @FXML
    public ComboBox departmentComboBox;
    @FXML
    private TableView<Employe> employeeTableView;
    @FXML
    public Label successLabel;
    public TextField updateNomField;
    public TextField updateSalaireField;
    public TextField updateAgeField;
    public TextField updateDepartementField;
    @FXML
    public TextField idSearchField;
    public Button cancelButton;
    public Button updateButton;
    public Button getEmployeeButton;
    public TextField deleteByIdField;
    public Button deleteByIdButton;
    public TextField deleteByNameField;
    public Button deleteByNameButton;


    @FXML
    public TextField departmentInputN;
    @FXML
    private TextField departmentInput;
    @FXML
    private TableView<Employe> employeeTable;
    @FXML
    private TableColumn<Employe, String> nameColumn;
    @FXML
    private TableColumn<Employe, Float> salaryColumn;

    @FXML
    private TableColumn<Employe, String> departmentColumn;

    @FXML
    private Label errorLabel;
    @FXML
    public TextField textNom;
    @FXML
    public TextField testSalaire;
    @FXML
    public TextField textAge;
    @FXML
    public TextField textDepartement;

    public TableColumn<Employe, Integer> ageColumn;

    public void addEmploye() {
        DaoEmploye daoEmploye = new DaoEmploye();
        try {
            String nom = textNom.getText().trim();
            String ageText = textAge.getText().trim();
            String salaireText = testSalaire.getText().trim();
            String idDepartmentText = textDepartement.getText().trim();
            ValidatorEmploye.validateEmployeeFields(nom, ageText, salaireText, idDepartmentText);
            if (!ValidatorDepartment.isDepartmentExists(idDepartmentText)) {
                throw new IllegalArgumentException("Department avec ID " + idDepartmentText + " n'existe pas.");
            }
            errorLabel.setText("");
            int age = Integer.parseInt(ageText);
            float salaire = Float.parseFloat(salaireText);
            String idDepartment = idDepartmentText;
            Employe employe = new Employe(nom, age, salaire, idDepartment);
            daoEmploye.addEmploye(employe);
            successLabel.setText("Employee inserted successfully");
            clearFields();
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(event -> {
                Stage stage = (Stage) successLabel.getScene().getWindow();
                stage.close();
            });
            pause.play();
        } catch (IllegalArgumentException | SQLException e) {
            errorLabel.setText(e.getMessage());
        }
    }

   private void clearFields() {
        textNom.setText("");
        testSalaire.setText("");
        textAge.setText("");
        textDepartement.setText("");
    }
    private void clearFieldsUp() {
        // Clear or reset the fields
        updateNomField.clear();
        updateSalaireField.clear();
        updateAgeField.clear();
        updateDepartementField.clear();
    }

    @FXML
    public void closeDialog() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void updateEmployee() {
        DaoEmploye daoEmploye = new DaoEmploye();
        try {
            if (!updateEmployeeFieldsArePopulated()) {
                fetchEmployeeData();
            }
            Employe selectedEmployee = getUpdatedEmployeeFromFields();
            validateEmployee(selectedEmployee);
            String updatedIdDepartment = updateDepartementField.getText().trim();
            if (!ValidatorDepartment.isDepartmentExists(updatedIdDepartment)) {
                throw new IllegalArgumentException("Le département avec l'ID " + updatedIdDepartment + " n'existe pas.");
            }
            boolean isUpdated = daoEmploye.updateEmploye(selectedEmployee.getIdEmp(), selectedEmployee);
            if (isUpdated) {
                successLabel.setText("Employé mis à jour avec succès");
                clearFieldsUp();
            } else {
                errorLabel.setText("Échec de la mise à jour de l'employé. Employé avec l'ID " + selectedEmployee.getIdEmp() + " non trouvé.");
            }
        } catch (IllegalArgumentException | SQLException e) {
            errorLabel.setText(e.getMessage());
        }
    }



    public void fetchEmployeeData() {
        DaoEmploye daoEmploye = new DaoEmploye();
        try {
            int employeeId = Integer.parseInt(idSearchField.getText().trim());
            Employe fetchedEmployee = daoEmploye.getEmployeById(employeeId);
            validateEmployee(fetchedEmployee);
            clearFieldsUp();
            updateNomField.setText(fetchedEmployee.getNomEmp());
            updateSalaireField.setText(String.valueOf(fetchedEmployee.getSalaire()));
            updateAgeField.setText(String.valueOf(fetchedEmployee.getAge()));
            updateDepartementField.setText(fetchedEmployee.getRefDept());
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    private boolean updateEmployeeFieldsArePopulated() {
        return !updateNomField.getText().isEmpty() ||
                !updateSalaireField.getText().isEmpty() ||
                !updateAgeField.getText().isEmpty() ||
                !updateDepartementField.getText().isEmpty();
    }
    private Employe getUpdatedEmployeeFromFields() {
        int updatedId = Integer.parseInt(idSearchField.getText().trim());
        String updatedNom = updateNomField.getText().trim();
        int updatedAge = Integer.parseInt(updateAgeField.getText().trim());
        float updatedSalaire = Float.parseFloat(updateSalaireField.getText().trim());
        String updatedIdDepartment = updateDepartementField.getText().trim();
        return new Employe(updatedId, updatedNom, updatedSalaire, updatedAge, updatedIdDepartment);
    }

    private void validateEmployee(Employe employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Aucun employé trouvé avec l'ID fourni.");
        }
    }

    public void getEmployeeDetails() {
        DaoEmploye daoEmploye = new DaoEmploye();
        try {
            int employeId = Integer.parseInt(idSearchField.getText().trim());
            Employe fetchedEmployee = daoEmploye.getEmployeById(employeId);
            validateEmployee(fetchedEmployee);
            clearFieldsUp();
            updateNomField.setText(fetchedEmployee.getNomEmp());
            updateSalaireField.setText(String.valueOf(fetchedEmployee.getSalaire()));
            updateAgeField.setText(String.valueOf(fetchedEmployee.getAge()));
            updateDepartementField.setText(fetchedEmployee.getRefDept());
        } catch (IllegalArgumentException e) {
            errorLabel.setText("no id inserted "+e.getMessage());
        }
    }
    @FXML
    public void deleteEmployeeById() {
        DaoEmploye daoEmploye= new DaoEmploye();
        String employeeId = deleteByIdField.getText().trim();
        if (employeeId.isEmpty()) {
            errorLabel.setText("Please enter Employee ID");
            return;
        }
        try {
            int id = Integer.parseInt(employeeId);
            boolean isDeleted = daoEmploye.deleteEmployeById(id);
            if (isDeleted) {
                successLabel.setText("Employee deleted by ID successfully");
            } else {
                errorLabel.setText("Employee avec ID " + id + " n'existe pas");
            }
        } catch (NumberFormatException e) {
            errorLabel.setText("Invalid Employee ID");
        }
    }
    @FXML
    public void deleteEmployeeByName() {
        DaoEmploye daoEmploye= new DaoEmploye();
        String employeeName = deleteByNameField.getText().trim();
        if (employeeName.isEmpty()) {
            errorLabel.setText("Please enter Employee Name");
            return;
        }
        boolean isDeleted = daoEmploye.deleteEmployeByName(employeeName);
        if (isDeleted) {
            successLabel.setText("Employee deleted by Name successfully");
        } else {
            errorLabel.setText("Employee avec Name " + employeeName + " n'existe pas");
        }
    }

    @FXML
    public void reafecterEmploye() {
        Employe selectedEmployee = (Employe) employeeComboBox.getValue();
        Departement selectedDepartment = (Departement) departmentComboBox.getValue();
        if (selectedEmployee != null && selectedDepartment != null) {
            String selectedDepartmentName = selectedDepartment.getNomDept();
            DaoEmploye daoEmploye = new DaoEmploye();
            boolean success = daoEmploye.reafecterEmploye(selectedEmployee, selectedDepartmentName);
            if (success) {
                System.out.println("Employee reassigned successfully!");
            } else {
                System.err.println("Failed to reassign employee.");
            }
        } else {
            System.out.println("Please select both employee and department.");
        }
    }


    @FXML
    public void searchEmployeesByDeptId() {
        DaoEmploye daoEmploye = new DaoEmploye();
        String departmentInputText = departmentInput.getText().trim();
        if (!departmentInputText.isEmpty()) {
            List<Employe> employees = daoEmploye.getEmployesByIdOfDepartment(departmentInputText);
            if (employees.isEmpty()) {
                errorLabel.setText("No employees found for the given department.");
                System.out.println("No employees found for the given department.");
                employeeTable.getItems().clear();
            } else {
                nameColumn.setCellValueFactory(new PropertyValueFactory<>("nomEmp"));
                salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salaire"));
                ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
                departmentColumn.setCellValueFactory(new PropertyValueFactory<>("refDept"));
                employeeTable.setItems(FXCollections.observableArrayList(employees));
            }
        } else {
            System.out.println("Please enter a department name or ID.");
            errorLabel.setText("Please enter a department name or ID.");
        }
    }

    public void searchEmployeesByDeptName() {
        DaoEmploye daoEmploye = new DaoEmploye();
        String departmentInputText = departmentInputN.getText().trim();
        if (!departmentInputText.isEmpty()) {
            List<Employe> employees = daoEmploye.getEmployesByNameOfDepartment(departmentInputText);
            if (employees.isEmpty()) {
                errorLabel.setText("No employees found for the given department.");
                System.out.println("No employees found for the given department.");
                employeeTable.getItems().clear();
            } else {
                nameColumn.setCellValueFactory(new PropertyValueFactory<>("nomEmp"));
                salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salaire"));
                ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
                departmentColumn.setCellValueFactory(new PropertyValueFactory<>("refDept"));
                employeeTable.setItems(FXCollections.observableArrayList(employees));
            }
        } else {
            System.out.println("Please enter a department name or ID.");
            errorLabel.setText("Please enter a department name or ID.");
        }
    }
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initializing EmployeController...");
        System.out.println("employeeTableView: " + employeeTableView);
        DaoEmploye daoEmploye = new DaoEmploye();
        ObservableList<Employe> employeList = daoEmploye.getEmployes();
        if (employeeComboBox != null ) {
        employeeComboBox.setItems(employeList);
        }
        if (employeeTableView != null ) {
            employeeTableView.setItems(employeList);
        } else {
            System.err.println("employeeTableView is null!");
        }
        DaoDepartement daoDepartment = new DaoDepartement();
        ObservableList<Departement> departmentList = daoDepartment.getDepartements();
        if (departmentComboBox != null) {
            departmentComboBox.setItems(departmentList);
        } else {
            System.err.println("departmentComboBox is null!");
        }
    }
}

