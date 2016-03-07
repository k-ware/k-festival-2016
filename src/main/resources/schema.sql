
CREATE TABLE TB_MEMBER (
  EMAIL VARCHAR2(200) NOT NULL PRIMARY KEY,
  NAME VARCHAR2(100) NOT NULL,
  M_GROUP CHAR(1)
);

CREATE TABLE TB_RESULT (
  ID BIGINT NOT NULL IDENTITY PRIMARY KEY,
  EMAIL VARCHAR2(200) NOT NULL,
  QUESTION_ID INT NOT NULL,
  SOURCE CLOB NOT NULL,
  NORMAL_OUTPUT CLOB,
  HAS_ERROR BIT DEFAULT FALSE,
  ERROR_COMMAND VARCHAR2(50),
  ERROR_OUTPUT CLOB,
  TIME_MILLS BIGINT DEFAULT 0,
  RIGHT_ANSWER BIT DEFAULT FALSE,
  REG_DT TIMESTAMP DEFAULT NOW()
);