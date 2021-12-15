CREATE TABLE films
(
    id      BIGSERIAL PRIMARY KEY,
    name    TEXT        NOT NULL,
    price   INT         NOT NULL CHECK ( price >= 0 ),
    production_year INT NOT NULL CHECK ( production_year>=1895 ),
    country TEXT[] NOT NULL DEFAULT '{}',
    genre TEXT[] NOT NULL DEFAULT '{}',
    rating FLOAT NOT NULL DEFAULT 0,
    views BIGINT NOT NULL DEFAULT 0,
    image   TEXT        NOT NULL ,
    removed BOOL        NOT NULL                    DEFAULT FALSE,
    created timestamptz NOT NULL                    DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE sales
(
    id      BIGSERIAL PRIMARY KEY,
    film_id BIGINT NOT NULL REFERENCES films,
    name       TEXT   NOT NULL,
    price      INT    NOT NULL CHECK ( price >= 0 ),
    created timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP
);



