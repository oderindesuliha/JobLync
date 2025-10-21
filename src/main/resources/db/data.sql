truncate table users cascade;
truncate table job_postings cascade;
truncate table job_applications cascade;

insert into users (first_name, last_name, email, password, role) values
                    ('John', 'Adah', 'adah@gmail.com', 'john123', 'APPLICANT'),
                     ('Jane', 'Smith', 'jane@example.com', 'pass456', 'HR_MANAGER'),
                     ('Admin', 'User', 'admin@example.com', 'adminpass', 'ADMIN');

insert into job_postings (title, description, requirements, created_by) values
                        ('Backend Developer', 'Build REST APIs', 'Java, Spring Boot, PostgreSQL', 2),
                        ('Frontend Developer', 'Create UI components', 'React, CSS, HTML', 2);

insert into  job_applications (applicant_id, job_posting_id, status) values
                              (1, 1, 'PENDING'),
                              (1, 2, 'HIRED');
