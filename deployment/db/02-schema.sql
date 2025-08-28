USE crediya_authentication;

-- Roles table
CREATE TABLE IF NOT EXISTS roles (
  role_id VARCHAR(36) PRIMARY KEY,
  role_name VARCHAR(100) NOT NULL UNIQUE
) ENGINE=InnoDB;

-- Users table
CREATE TABLE IF NOT EXISTS users (
  user_id VARCHAR(36) PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  birth_date DATE NULL,
  address VARCHAR(200) NULL,
  phone_number VARCHAR(50) NULL,
  email VARCHAR(150) NOT NULL UNIQUE,
  base_salary DECIMAL(15,2) NULL,
  identity_document VARCHAR(100) NOT NULL UNIQUE,
  role_id VARCHAR(36) NULL,
  CONSTRAINT fk_users_role FOREIGN KEY (role_id) REFERENCES roles(role_id)
) ENGINE=InnoDB;
