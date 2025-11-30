-- Создание таблицы university
CREATE TABLE IF NOT EXISTS university (
                                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          name VARCHAR(255) NOT NULL
    );

-- Создание таблицы student_group
CREATE TABLE IF NOT EXISTS student_group (
                                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                             name VARCHAR(255) NOT NULL,
    speciality VARCHAR(255),
    university_id BIGINT,
    FOREIGN KEY (university_id) REFERENCES university(id)
    );

-- Создание таблицы address
CREATE TABLE IF NOT EXISTS address (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       city VARCHAR(255) NOT NULL,
    street VARCHAR(255) NOT NULL,
    house VARCHAR(255) NOT NULL,
    apartment VARCHAR(255)
    );

-- Создание таблицы course
CREATE TABLE IF NOT EXISTS course (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      name VARCHAR(255) NOT NULL,
    semester VARCHAR(255),
    hours VARCHAR(255)
    );

-- Создание таблицы student
CREATE TABLE IF NOT EXISTS student (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       last_name VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    patronymic VARCHAR(255),
    gender VARCHAR(255),
    nationality VARCHAR(255),
    height DOUBLE,
    weight DOUBLE,
    birth_date DATE,
    phone_number VARCHAR(255),
    gpa DOUBLE,
    speciality VARCHAR(255),
    address_id BIGINT,
    group_id BIGINT,
    FOREIGN KEY (address_id) REFERENCES address(id),
    FOREIGN KEY (group_id) REFERENCES student_group(id)
    );

-- Создание таблицы связи student_courses
CREATE TABLE IF NOT EXISTS student_courses (
                                               student_id BIGINT,
                                               course_id BIGINT,
                                               PRIMARY KEY (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES student(id),
    FOREIGN KEY (course_id) REFERENCES course(id)
    );