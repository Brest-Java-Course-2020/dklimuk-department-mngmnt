DROP TABLE IF EXISTS department;
CREATE TABLE department (
  department_id INT NOT NULL AUTO_INCREMENT,
  department_name VARCHAR(255) NOT NULL UNIQUE,
  PRIMARY KEY (department_id)
);