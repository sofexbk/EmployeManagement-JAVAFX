CREATE TABLE Departement (
    IdDept VARCHAR(255) PRIMARY KEY,
    NomDept VARCHAR(255)
);

CREATE TABLE Employee (
    IdEmp INT PRIMARY KEY AUTO_INCREMENT,
    NomEmp VARCHAR(255),
    Salaire FLOAT,
    Age INT,
    RefDept VARCHAR(255),
    FOREIGN KEY (RefDept) REFERENCES Departement(IdDept)
);
