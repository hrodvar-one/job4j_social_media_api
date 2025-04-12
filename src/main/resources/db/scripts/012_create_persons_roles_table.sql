CREATE TABLE persons_roles (
     role_id INT REFERENCES roles(id) ON DELETE CASCADE,
     person_id INT REFERENCES persons(id) ON DELETE CASCADE
 );