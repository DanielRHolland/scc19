CREATE TABLE shares (
   company_symbol CHAR(3) NOT NULL,
   company_name VARCHAR,
   shares_available INT,
   last_update DATETIME,
   currency CHAR(3),
   value FLOAT,
   PRIMARY KEY (company_symbol)  
);