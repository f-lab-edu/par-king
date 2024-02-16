CREATE TABLE member (
    id          BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    member_id     varchar(20) NOT NULL,
    member_name   varchar(255) NOT NULL,
    email       varchar(255) NOT NULL,
    member_status      enum('ACTIVATED', 'REVOKED') NOT NULL,
    no_show_count int,
    start_no_show_time datetime,
    created_at  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  datetime
);