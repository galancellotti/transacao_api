CREATE TABLE transactions(

    id BIGSERIAL NOT NULL PRIMARY KEY,
    sender_id BIGINT NOT NULL,
    receiver_id BIGINT NOT NULL,
    value NUMERIC(15,2) NOT NULL,
    dateCreation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_sender FOREIGN KEY (sender_id) REFERENCES users(id),
    CONSTRAINT fk_receiver FOREIGN KEY (receiver_id) REFERENCES users(id)

)