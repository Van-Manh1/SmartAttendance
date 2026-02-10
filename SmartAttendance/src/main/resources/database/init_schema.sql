CREATE DATABASE smart_attendance_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE smart_attendance_db;

-- 1. Bảng Phòng ban
CREATE TABLE departments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

-- 2. Bảng Ca làm việc 
CREATE TABLE shifts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    start_time TIME NOT NULL,       
    end_time TIME NOT NULL,         
    break_start_time TIME,     
    break_end_time TIME,      
    grace_period_minutes INT DEFAULT 10 
);

-- 3. Bảng Người dùng
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    card_uid VARCHAR(50) UNIQUE, 
    shift_id BIGINT,
    department_id BIGINT,
    FOREIGN KEY (shift_id) REFERENCES shifts(id) ON DELETE SET NULL,
    FOREIGN KEY (department_id) REFERENCES departments(id) ON DELETE SET NULL
);

-- 4. Bảng Roles
CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE
);

-- 5. Bảng User_Roles
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- 6. Bảng Logs chấm công
CREATE TABLE attendance_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    check_time DATETIME NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 7. Bảng Công chi tiết
CREATE TABLE daily_timesheets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    date DATE NOT NULL,
    check_in TIME,
    check_out TIME,
    late_minutes INT DEFAULT 0,
    early_leave_minutes INT DEFAULT 0,
    overtime_hours DOUBLE DEFAULT 0.0,
    status VARCHAR(20),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE(user_id, date)
);

-- DATA MẪU CHUẨN 
INSERT INTO roles (name) VALUES ('ROLE_ADMIN'), ('ROLE_EMPLOYEE'), ('ROLE_MANAGER');
INSERT INTO departments (name) VALUES ('IT Department'), ('HR Department');
INSERT INTO shifts (name, start_time, end_time, break_start_time, break_end_time) 
VALUES ('Ca Hành Chính', '08:00:00', '17:30:00', '12:00:00', '13:30:00');