CREATE TABLE IF NOT EXISTS users
(
    id            SERIAL PRIMARY KEY NOT NULL,
    uuid          UUID               NOT NULL,
    name          TEXT
);

CREATE UNIQUE INDEX IF NOT EXISTS idx_users_uuid ON users (uuid);