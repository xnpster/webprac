CREATE TABLE "Clients" (
  "id" serial,
  "name" varchar(100),
  "address" text,
  "email" varchar(100),
  "phone" varchar(15),
  PRIMARY KEY ("id")
);

CREATE TABLE "Branches" (
  "id" serial,
  "name" varchar(100),
  "address" text,
  "city" varchar(20),
  PRIMARY KEY ("id")
);

CREATE TABLE "Account_types" (
  "id" serial,
  "name" varchar(100),
  "credit_limit" int,
  "allow_refill" bool,
  "allow_write-off" bool,
  PRIMARY KEY ("id")
);

CREATE TABLE "Accounts" (
  "id" serial,
  "branch_id" int,
  "client_id" int,
  "type_id" int,
  "balance" int,
  "open_date" date,
  "close_date" date,
  PRIMARY KEY ("id"),
  CONSTRAINT "FK_Accounts.client_id"
    FOREIGN KEY ("client_id")
      REFERENCES "Clients"("id"),
  CONSTRAINT "FK_Accounts.branch_id"
    FOREIGN KEY ("branch_id")
      REFERENCES "Branches"("id"),
  CONSTRAINT "FK_Accounts.type_id"
    FOREIGN KEY ("type_id")
      REFERENCES "Account_types"("id")
);

CREATE TABLE "History" (
  "account_id" int,
  "sum" int,
  "date" timestamp,
  CONSTRAINT "FK_History.account_id"
    FOREIGN KEY ("account_id")
      REFERENCES "Accounts"("id")
);