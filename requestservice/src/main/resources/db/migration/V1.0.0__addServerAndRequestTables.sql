CREATE SEQUENCE server_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE ad_request_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE server (
    id BIGINT NOT NULL PRIMARY KEY,
    server_name VARCHAR(255) NOT NULL
);

CREATE TABLE ad_request (
    id BIGINT NOT NULL PRIMARY KEY,
    club_name VARCHAR(255),
    instagram_url VARCHAR(255),
    email_address VARCHAR(255),
    notify_via_instagram BOOLEAN,
    notify_via_email BOOLEAN,
    message TEXT,
    status VARCHAR(255),
    is_processed_by BIGINT,

    server_id BIGINT,
    owner_name VARCHAR(255),
    size INT,
    description TEXT,
    homepage_url VARCHAR(255),
    youtube_url VARCHAR(255),

    CONSTRAINT fk_adrequest_server FOREIGN KEY (server_id) REFERENCES server(id)
);