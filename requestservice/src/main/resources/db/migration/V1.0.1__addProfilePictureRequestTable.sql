CREATE SEQUENCE profile_picture_request_seq
    START WITH 1
    INCREMENT BY 50;

CREATE TABLE profile_picture_request (
    id BIGINT PRIMARY KEY DEFAULT nextval('profile_picture_request_seq'),
    club_name VARCHAR(255),
    instagram_url VARCHAR(255),
    email_address VARCHAR(255),
    notify_via_instagram BOOLEAN DEFAULT FALSE,
    notify_via_email BOOLEAN DEFAULT FALSE,
    message TEXT,
    status VARCHAR(50) DEFAULT 'PENDING',
    is_processed_by BIGINT,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    description TEXT
);
