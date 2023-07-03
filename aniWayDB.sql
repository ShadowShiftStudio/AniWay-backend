CREATE TYPE "reading_status" AS ENUM (
  'in_progress',
  'planned',
  'completed',
  'postponed'
);

CREATE TYPE "manga_status" AS ENUM (
  'ongoing',
  'finished',
  'freezed',
  'announced'
);

CREATE TYPE "manga_type" AS ENUM (
  'manhwa',
  'manhua',
  'manga',
  'cartoon'
);

CREATE TABLE "badge" (
  "id" BIGSERIAL PRIMARY KEY,
  "name" varchar(20)
);

CREATE TABLE "user" (
  "id" BIGSERIAL PRIMARY KEY,
  "nickname" varchar(20),
  "xp" integer,
  "pass_xp" integer,
  "balance" integer,
  "email" text,
  "password" text
);

CREATE TABLE "translator" (
  "id" BIGSERIAL PRIMARY KEY,
  "nickname" varchar(20),
  "user_id" BIGSERIAL
);

CREATE TABLE "translator_manga" (
  "translator_id" BIGSERIAL,
  "manga_id" BIGSERIAL
);

CREATE TABLE "user_manga" (
  "user_id" BIGSERIAL,
  "manga_id" BIGSERIAL,
  "status" reading_status,
  "rating" integer
);

CREATE TABLE "manga" (
  "id" BIGSERIAL PRIMARY KEY,
  "name" varchar(100),
  "year" date,
  "description" text[],
  "status" manga_status,
  "manga_type" manga_type
);

CREATE TABLE "chapter" (
  "id" BIGSERIAL PRIMARY KEY,
  "manga_id" BIGSERIAL,
  "name" varchar(100),
  "number" integer,
);

CREATE TABLE "user_chapter" (
  "user_id" BIGSERIAL,
  "chapter_id" BIGSERIAL,
  "is_liked" boolean
);

CREATE TABLE "translator_chapter" (
  "translator_id" BIGSERIAL,
  "chapter_id" BIGSERIAL
);

CREATE TABLE "message" (
  "id" BIGSERIAL PRIMARY KEY,
  "message" varchar(350)
);

CREATE TABLE "comment" (
  "id" BIGSERIAL PRIMARY KEY,
  "user_id" BIGSERIAL,
  "manga_id" BIGSERIAL,
  "message_id" BIGSERIAL
);

CREATE TABLE "genre" (
  "id" BIGSERIAL PRIMARY KEY,
  "name" varchar(50)
);

CREATE TABLE "manga_genre" (
  "manga_id" BIGSERIAL,
  "genre_id" BIGSERIAL
);

CREATE TABLE "user_badge" (
  "user_id" BIGSERIAL,
  "badge_id" BIGSERIAL
);

ALTER TABLE "chapter" ADD FOREIGN KEY ("manga_id") REFERENCES "manga" ("id");

ALTER TABLE "translator_manga" ADD FOREIGN KEY ("translator_id") REFERENCES "translator" ("id");

ALTER TABLE "translator_chapter" ADD FOREIGN KEY ("translator_id") REFERENCES "translator" ("id");

ALTER TABLE "translator_manga" ADD FOREIGN KEY ("manga_id") REFERENCES "manga" ("id");

ALTER TABLE "user_manga" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("id");

ALTER TABLE "user_manga" ADD FOREIGN KEY ("manga_id") REFERENCES "manga" ("id");

ALTER TABLE "user_chapter" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("id");

ALTER TABLE "user_chapter" ADD FOREIGN KEY ("chapter_id") REFERENCES "chapter" ("id");

ALTER TABLE "translator_chapter" ADD FOREIGN KEY ("chapter_id") REFERENCES "chapter" ("id");

ALTER TABLE "comment" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("id");

ALTER TABLE "comment" ADD FOREIGN KEY ("manga_id") REFERENCES "manga" ("id");

ALTER TABLE "comment" ADD FOREIGN KEY ("message_id") REFERENCES "message" ("id");

ALTER TABLE "manga_genre" ADD FOREIGN KEY ("manga_id") REFERENCES "manga" ("id");

ALTER TABLE "manga_genre" ADD FOREIGN KEY ("genre_id") REFERENCES "genre" ("id");

ALTER TABLE "user_badge" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("id");

ALTER TABLE "user_badge" ADD FOREIGN KEY ("badge_id") REFERENCES "badge" ("id");

ALTER TABLE "translator" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("id");
