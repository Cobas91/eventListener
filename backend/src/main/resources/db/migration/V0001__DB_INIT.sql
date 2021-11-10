
CREATE TABLE IF NOT EXISTS "app_userdto" (
    "id" SERIAL PRIMARY KEY,
    "username" VARCHAR(255) NOT NULL,
    "password" VARCHAR(255) NULL DEFAULT NULL
    );

CREATE TABLE IF NOT EXISTS "event" (
    "id" SERIAL PRIMARY KEY,
    "description" VARCHAR(255) NULL DEFAULT NULL,
    "name" VARCHAR(255) NULL DEFAULT NULL
    );


CREATE TABLE IF NOT EXISTS "event_actions" (
    "event_id" SERIAL PRIMARY KEY,
    "actions" INTEGER NULL DEFAULT NULL,
    CONSTRAINT "fk1q28ul9fhf3j52ebs1uwqf1te" FOREIGN KEY ("event_id") REFERENCES "public"."event" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
    );

CREATE TABLE IF NOT EXISTS "notification_user" (
    "id" SERIAL PRIMARY KEY,
    "email" VARCHAR(255) NULL DEFAULT NULL,
    "name" VARCHAR(255) NULL DEFAULT NULL
    );

CREATE TABLE IF NOT EXISTS "event_notification_user" (
    "event_id" SERIAL PRIMARY KEY,
    "notification_user_id" INTEGER NOT NULL,
    CONSTRAINT "fkhibp3hk5g7pk2krp47721u8y" FOREIGN KEY ("event_id") REFERENCES "public"."event" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT "fkjuklqmawfcf1ein89djo7945t" FOREIGN KEY ("notification_user_id") REFERENCES "public"."notification_user" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
    );



