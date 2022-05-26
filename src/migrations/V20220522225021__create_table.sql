-- -- Write Migration
-- -- Version: 20220522225021

CREATE TABLE USERS (
    id INTEGER NOT NULL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);



CREATE TABLE USER_COMMENTS (
    id INTEGER NOT NULL PRIMARY KEY,
    user_id INTEGER REFERENCES USERS(id),
    comment_content VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

-- Create new table user description connected to users
CREATE TABLE user_roles (
    id INTEGER NOT NULL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(id),
    role VARCHAR(255) NOT NULL
);


-- Create new table deleted users which contains all deleted users
CREATE TABLE deleted_users (
    id INTEGER NOT NULL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    deleted_at date NOT NULL,
    deleted_by INTEGER NOT NULL
);

CREATE SEQUENCE user_id_seq
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 100
    CACHE 20;

CREATE SEQUENCE comment_id_seq
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 100
    CACHE 20;

CREATE SEQUENCE user_role_id_seq
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 100
    CACHE 20;

CREATE SEQUENCE deleted_user_id_seq
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 100
    CACHE 20;

-- Create procedure to insert new user in oracle sql

create or replace procedure "INSERT_USER"
(
 username IN VARCHAR2,
 password IN VARCHAR2
 )

    is
begin
    insert into users values(user_id_seq.nextval, username, password, CURRENT_DATE, null);
end;
/

create or replace procedure "INSERT_COMMENT"
(
    user_id IN INTEGER,
    comment_content IN VARCHAR2
    )

        is
        begin
            insert into user_comments values(comment_id_seq.nextval, user_id, comment_content, CURRENT_DATE, null);
        end;
/




-- Create procedure to delete user in oracle sql plus
CREATE OR REPLACE PROCEDURE delete_user_by_id(
    deleted_id IN INTEGER
)
IS
BEGIN
    INSERT INTO deleted_users (id, user_id, deleted_at, deleted_by)
    VALUES (deleted_user_id_seq.nextval, deleted_id, CURRENT_DATE, deleted_id);
    DELETE FROM user_roles WHERE user_id = deleted_id;

    DELETE FROM user_comments WHERE user_id = deleted_id;
    DELETE FROM USERS WHERE id = deleted_id;


END;

-- Create procedure to update user id in oracle sql plus
CREATE OR REPLACE PROCEDURE update_user_by_id(
    p_id IN INTEGER,
    new_username IN VARCHAR2,
    new_password IN VARCHAR2
)
IS
BEGIN
    UPDATE USERS SET username = new_username, password = new_password, updated_at = CURRENT_DATE WHERE id = p_id;
END;


-- Create procedure to find user by id in oracle sql plus
create OR REPLACE PROCEDURE get_user_by_id(
    p_id IN NUMBER,
    p_recordset OUT SYS_REFCURSOR
)
    IS
BEGIN
    OPEN p_recordset FOR
        SELECT * FROM users WHERE id = p_id;

    RETURN;

END get_user_by_id;

-- Get all users
CREATE OR REPLACE PROCEDURE get_all_users(
    p_recordset OUT SYS_REFCURSOR
)
    IS
BEGIN
    OPEN p_recordset FOR
        SELECT * FROM users;

    RETURN;

END get_all_users;

-- Insert sample into users
INSERT INTO USERS (id, username, password, created_at, updated_at) VALUES (user_id_seq.nextval, 'admin', '123', SYSDATE, SYSDATE);
INSERT INTO USERS (id, username, password, created_at, updated_at) VALUES (user_id_seq.nextval, 'user', '11', SYSDATE, SYSDATE);

INSERT INTO USER_COMMENTS (id, user_id, comment_content, created_at, updated_at) VALUES (user_id_seq.nextval, 1, 'This is a comment', SYSDATE, SYSDATE);
INSERT INTO USER_COMMENTS (id, user_id, comment_content, created_at, updated_at) VALUES (user_id_seq.nextval, 2, 'This is a comment', SYSDATE, SYSDATE);
INSERT INTO USER_COMMENTS (id, user_id, comment_content, created_at, updated_at) VALUES (user_id_seq.nextval, 2, 'This is a comment', SYSDATE, SYSDATE);


-- Insert sample into user_roles
INSERT INTO user_roles (id, user_id, role) VALUES (user_role_id_seq.nextval, 1, 'admin');
INSERT INTO user_roles (id, user_id, role) VALUES (user_role_id_seq.nextval, 2, 'user');

CREATE MATERIALIZED VIEW user_comments_view
AS
SELECT
    user_comments.id,
    user_comments.user_id,
    user_comments.comment_content,
    user_comments.created_at,
    user_comments.updated_at,
    users.username
FROM
    user_comments
INNER JOIN
    users
ON
    user_comments.user_id = users.id;

-- Create materialized view for all user data
CREATE MATERIALIZED VIEW all_users_comments_and_role
AS
SELECT
    users.username,
    user_roles.role,
    user_comments_view.id,
    user_comments_view.user_id,
    user_comments_view.comment_content,
    user_comments_view.created_at,
    user_comments_view.updated_at
FROM
    users
INNER JOIN
    user_roles
ON
    users.id = user_roles.user_id
INNER JOIN
    user_comments_view
ON
    users.id = user_comments_view.user_id;

-- DROP TRIGGER DELETE_USER_TRIGGER ;
-- -- Create trigger to delete user
--
-- CREATE OR REPLACE TRIGGER delete_user_trigger
-- AFTER DELETE ON users
-- FOR EACH ROW
-- BEGIN
--     INSERT INTO deleted_users (id, user_id, deleted_at, deleted_by)
--     VALUES (deleted_user_id_seq.nextval, OLD.id, CURRENT_DATE, OLD.id);
-- END;