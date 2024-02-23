CREATE TABLE member (
    id                  BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    member_id           varchar(20) NOT NULL,
    member_name         varchar(255) NOT NULL,
    email               varchar(255) NOT NULL,
    member_status       enum('ACTIVATED', 'REVOKED') NOT NULL,
    no_show_count       int,
    start_no_show_time  datetime,
    created_at          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          datetime
) engine=InnoDB default character set = utf8;

CREATE TABLE car (
    id                          BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    car_number                  varchar(50) NOT NULL,
    member_id                   int,
    parking_lot_id              int,
    dibs_on_parking_lot_id      int,
    dibs_on_parking_lot_status  enum('DIBS_ON_PARKING_LOT', 'COMPLETE', 'NO_SHOW', 'NORMAL'),
    start_dibs_on_time          datetime,
    created_at                  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                  datetime
) engine=InnoDB default character set = utf8;

CREATE TABLE parking_lot (
    id                          BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    member_id                   int NOT NULL,
    name                        varchar(20) NOT NULL,
    gu_name                     varchar(20) NOT NULL,
    city_name                   varchar(20) NOT NULL,
    full_address                varchar(50),
    total_space                 int NOT NULL,
    occupied_space              int NOT NULL,
    cost                        BIGINT NOT NULL,
    extra_cost                  BIGINT NOT NULL,
    created_at                  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                  datetime
) engine=InnoDB default character set = utf8;

CREATE TABLE no_show (
    id                          BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    member_id                   int NOT NULL,
    car_id                      int NOT NULL,
    parking_lot_id              int NOT NULL,
    no_show_time                datetime,
    created_at                  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                  datetime
) engine=InnoDB default character set = utf8;

CREATE TABLE dibs_on_parking_lot (
    id                          BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    member_id                   int NOT NULL,
    car_id                      int NOT NULL,
    parking_lot_id              int NOT NULL,
    current_status              enum('DIBS_ON_PARKING_LOT', 'COMPLETE', 'NO_SHOW', 'NORMAL') NOT NULL,
    created_at                  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                  datetime
) engine=InnoDB default character set = utf8;