CREATE KEYSPACE IF NOT EXISTS ocds WITH replication = {'class':'SimpleStrategy', 'replication_factor':1};

CREATE TABLE IF NOT EXISTS  ocds.regulation_data (
    cp_id text,
    json_data text,
    primary key(cp_id)
);

CREATE TABLE IF NOT EXISTS  ocds.regulation_rules (
    created_date timestamp,
    status text,
    stage text,
    primary key(created_date, status, stage)
);


SELECT * FROM ocds.budget_ei;
SELECT * FROM ocds.budget_fs;

TRUNCATE ocds.budget_fs;
TRUNCATE ocds.budget_ei;


