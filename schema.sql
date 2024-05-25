-- The Database Schema used for user authentication in HuskyHaze:

CREATE DATABASE IF NOT EXISTS huskyHaze;

CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL
);