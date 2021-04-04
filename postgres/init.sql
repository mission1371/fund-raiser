
CREATE USER fund_raiser_server WITH PASSWORD 'frs123';

CREATE DATABASE fund_raiser;

GRANT ALL PRIVILEGES ON DATABASE fund_raiser to fund_raiser_server;
