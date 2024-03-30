CREATE TABLE IF NOT EXISTS user
(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(50) NOT NULL,
    name VARCHAR(50) NOT NULL,
    salt VARCHAR(255),
    token VARCHAR(255),
    accessToken VARCHAR(255),
    invitationToken VARCHAR(255),
    deactivatedDatetime VARCHAR(255),
    role VARCHAR(255) NOT NULL,
    createdDatetime TIMESTAMP NOT NULL,
    updatedDatetime TIMESTAMP NOT NULL,
    );