-- Create database and default user
CREATE DATABASE IF NOT EXISTS crediya_authentication CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Create application user (optional; MySQL official image uses MYSQL_USER)
-- These statements are safe even if user exists
CREATE USER IF NOT EXISTS 'appuser'@'%' IDENTIFIED BY 'apppass';
GRANT ALL PRIVILEGES ON crediya_authentication.* TO 'appuser'@'%';
FLUSH PRIVILEGES;
