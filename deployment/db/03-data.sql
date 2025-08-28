USE crediya_authentication;

INSERT INTO roles (role_id, role_name) VALUES
  ('00000000-0000-0000-0000-000000000001', 'ADMIN')
ON DUPLICATE KEY UPDATE role_name=VALUES(role_name);

INSERT INTO roles (role_id, role_name) VALUES
  ('00000000-0000-0000-0000-000000000002', 'USER')
ON DUPLICATE KEY UPDATE role_name=VALUES(role_name);

-- Seed a default user so GET by id works out-of-the-box
INSERT INTO users (
  user_id, name, last_name, birth_date, address, phone_number, email, base_salary, identity_document, role_id
) VALUES (
  '11111111-1111-1111-1111-111111111111', 'John', 'Doe', '1990-01-15', '742 Evergreen Terrace', '+1-555-0000', 'john.doe@example.com', 1200000.00, 'ID-123456', '00000000-0000-0000-0000-000000000002'
) ON DUPLICATE KEY UPDATE
  name=VALUES(name),
  last_name=VALUES(last_name),
  birth_date=VALUES(birth_date),
  address=VALUES(address),
  phone_number=VALUES(phone_number),
  email=VALUES(email),
  base_salary=VALUES(base_salary),
  identity_document=VALUES(identity_document),
  role_id=VALUES(role_id);
