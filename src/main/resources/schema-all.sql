/**
 * Author:  Miguel Castro
 */

DROP TABLE people IF EXISTS;

CREATE TABLE product  (
    person_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    name VARCHAR(20),
    price DECIMAL(10,2)
);

