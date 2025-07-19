-- Passo 1: Cria o schema para organizar as tabelas, se ele ainda não existir.
CREATE SCHEMA IF NOT EXISTS erp_marcato;

-- Passo 2: Concede ao usuário 'postgres' as permissões necessárias no novo schema.
--          Isso permite que ele veja, acesse e crie objetos dentro do schema.
GRANT USAGE, CREATE ON SCHEMA erp_marcato TO postgres;

-- Passo 3: Define o caminho de busca PARA ESTA SESSÃO.
--          Esta é a linha mais importante para garantir que as tabelas abaixo
--          sejam criadas no lugar certo (erp_marcato).
SET search_path TO erp_marcato, public;

--
-- Criação das Tabelas
--

CREATE TABLE IF NOT EXISTS department
(
    id          SERIAL PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    description TEXT
);

CREATE TABLE IF NOT EXISTS sector
(
    id            SERIAL PRIMARY KEY,
    title         VARCHAR(255) NOT NULL,
    description   TEXT,
    department_id INTEGER      NOT NULL,
    CONSTRAINT fk_department FOREIGN KEY (department_id) REFERENCES department (id) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS role
(
    id          SERIAL PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    description TEXT,
    level       VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS user_erp
(
    id         SERIAL PRIMARY KEY,
    user_uuid  UUID        NOT NULL UNIQUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    username   VARCHAR(255) NOT NULL UNIQUE,
    full_name  VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS employee
(
    id          SERIAL PRIMARY KEY,
    full_name   VARCHAR(255) NOT NULL,
    hire_date   TIMESTAMPTZ,
    status      VARCHAR(50)  NOT NULL,
    role_id     INTEGER,
    sector_id   INTEGER,
    user_erp_id INTEGER      UNIQUE,
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE SET NULL,
    CONSTRAINT fk_sector FOREIGN KEY (sector_id) REFERENCES sector (id) ON DELETE SET NULL,
    CONSTRAINT fk_user_erp FOREIGN KEY (user_erp_id) REFERENCES user_erp (id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS employee_identifier
(
    id           SERIAL PRIMARY KEY,
    employee_id  INTEGER      NOT NULL,
    type         VARCHAR(50)  NOT NULL,
    identifier   VARCHAR(255) NOT NULL,
    CONSTRAINT fk_employee FOREIGN KEY (employee_id) REFERENCES employee (id) ON DELETE CASCADE,
    UNIQUE (employee_id, type)
);

CREATE TABLE IF NOT EXISTS workspace
(
    id          UUID    NOT NULL PRIMARY KEY,
    user_erp_id INTEGER NOT NULL,
    CONSTRAINT fk_user_erp FOREIGN KEY (user_erp_id) REFERENCES user_erp (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS view
(
    view_id      UUID         NOT NULL PRIMARY KEY,
    label        VARCHAR(50)  NOT NULL,
    entity_key   VARCHAR(255) NOT NULL,
    data         JSONB        NOT NULL,
    workspace_id UUID         NOT NULL,
    CONSTRAINT fk_workspace FOREIGN KEY (workspace_id) REFERENCES workspace (id) ON DELETE CASCADE
);

--
-- Definição de donos e criação de índices
--

ALTER TABLE department OWNER TO postgres;
ALTER TABLE sector OWNER TO postgres;
ALTER TABLE role OWNER TO postgres;
ALTER TABLE user_erp OWNER TO postgres;
ALTER TABLE employee OWNER TO postgres;
ALTER TABLE employee_identifier OWNER TO postgres;
ALTER TABLE workspace OWNER TO postgres;
ALTER TABLE view OWNER TO postgres;

CREATE INDEX IF NOT EXISTS sector_department_id_idx ON sector (department_id);
CREATE INDEX IF NOT EXISTS employee_role_id_idx ON employee (role_id);
CREATE INDEX IF NOT EXISTS employee_sector_id_idx ON employee (sector_id);
CREATE INDEX IF NOT EXISTS employee_user_erp_id_idx ON employee (user_erp_id);
CREATE INDEX IF NOT EXISTS employee_identifier_employee_id_idx ON employee_identifier (employee_id);

-- Fim do script.