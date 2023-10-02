CREATE TABLE IF NOT EXISTS tasklist(
   AI  VARCHAR(8) PRIMARY KEY,
   keyword VARCHAR(256),
   pagenumber VARCHAR(10),
   Createddate VARCHAR(255),
   Updatedate
   done BOOLEAN
);