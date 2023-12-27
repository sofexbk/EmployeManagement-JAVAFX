INSERT INTO Departement (IdDept, NomDept) VALUES
    ('RH', 'Ressources Humaines'),
    ('INFO', 'Informatique'),
    ('MRK', 'Marketing');

INSERT INTO Employee (IdEmp, NomEmp, Salaire, Age, RefDept) VALUES
    (1, 'John Doe', 50000.00, 30, 'RH'),
    (2, 'Jane Doe', 60000.00, 35, 'INFO'),
    (3, 'Bob Smith', 55000.50, 28, 'RH'),
    (4, 'Alice Johnson', 75000.75, 40, 'INFO'),
    (5, 'Charlie Brown', 45000.25, 25, 'MRK');