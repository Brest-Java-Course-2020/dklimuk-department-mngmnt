DROP TABLE IF EXISTS department;
CREATE TABLE department (
  department_id INT NOT NULL AUTO_INCREMENT,
  department_name VARCHAR(20) NOT NULL UNIQUE,
  PRIMARY KEY (department_id)
);

DROP TABLE IF EXISTS employee;
CREATE TABLE employee (
  employee_id INT NOT NULL AUTO_INCREMENT,
  firstname VARCHAR(255) NOT NULL,
  lastname VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  salary INT NOT NULL,
  department_id INT NOT NULL,
  PRIMARY KEY (employee_id),
  FOREIGN KEY (department_id) REFERENCES department(department_id)
);