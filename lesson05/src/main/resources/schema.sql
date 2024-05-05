create table tasks (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(100) NOT NULL,
    task_status VARCHAR(20) NOT NULL,
    date_time TIMESTAMP NOT NULL
);