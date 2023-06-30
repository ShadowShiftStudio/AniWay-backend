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

CREATE TABLE "Badge" (
  "id" BIGSERIAL PRIMARY KEY,
  "name" varchar(20)
);

CREATE TABLE "User" (
  "id" BIGSERIAL PRIMARY KEY,
  "nickname" varchar(20),
  "xp" integer,
  "pass_xp" integer,
  "balance" integer,
  "email" text,
  "password" text
);

CREATE TABLE "Interpreter" (
  "id" BIGSERIAL PRIMARY KEY,
  "nickname" varchar(20),
  "user_id" BIGSERIAL
);

CREATE TABLE "InterpreterManga" (
  "interpreter_id" BIGSERIAL,
  "manga_id" BIGSERIAL
);

CREATE TABLE "UserManga" (
  "user_id" BIGSERIAL,
  "manga_id" BIGSERIAL,
  "status" reading_status,
  "rating" integer
);

CREATE TABLE "Manga" (
  "id" BIGSERIAL PRIMARY KEY,
  "name" varchar(100),
  "year" date,
  "description" text[],
  "status" manga_status,
  "manga_type" manga_type
);

CREATE TABLE "Chapter" (
  "id" BIGSERIAL PRIMARY KEY,
  "manga_id" BIGSERIAL,
  "name" varchar(100),
  "number" integer,
  "likes" integer,
  "views" integer
);

CREATE TABLE "UserChapter" (
  "user_id" BIGSERIAL,
  "chapter_id" BIGSERIAL,
  "is_liked" boolean
);

CREATE TABLE "InterpreterChapter" (
  "interpreter_id" BIGSERIAL,
  "chapter_id" BIGSERIAL
);

CREATE TABLE "Message" (
  "id" BIGSERIAL PRIMARY KEY,
  "message" varchar(150)
);

CREATE TABLE "Comment" (
  "id" BIGSERIAL PRIMARY KEY,
  "user_id" BIGSERIAL,
  "manga_id" BIGSERIAL,
  "message_id" BIGSERIAL
);

CREATE TABLE "Genre" (
  "id" BIGSERIAL PRIMARY KEY,
  "name" varchar(50)
);

CREATE TABLE "MangaGenre" (
  "manga_id" BIGSERIAL,
  "genre_id" BIGSERIAL
);

CREATE TABLE "UserBadge" (
  "user_id" BIGSERIAL,
  "badge_id" BIGSERIAL
);

ALTER TABLE "Chapter" ADD FOREIGN KEY ("manga_id") REFERENCES "Manga" ("id");

ALTER TABLE "InterpreterManga" ADD FOREIGN KEY ("interpreter_id") REFERENCES "Interpreter" ("id");

ALTER TABLE "InterpreterManga" ADD FOREIGN KEY ("manga_id") REFERENCES "Manga" ("id");

ALTER TABLE "UserManga" ADD FOREIGN KEY ("user_id") REFERENCES "User" ("id");

ALTER TABLE "UserManga" ADD FOREIGN KEY ("manga_id") REFERENCES "Manga" ("id");

ALTER TABLE "UserChapter" ADD FOREIGN KEY ("user_id") REFERENCES "User" ("id");

ALTER TABLE "UserChapter" ADD FOREIGN KEY ("chapter_id") REFERENCES "Chapter" ("id");

ALTER TABLE "InterpreterChapter" ADD FOREIGN KEY ("interpreter_id") REFERENCES "Interpreter" ("id");

ALTER TABLE "InterpreterChapter" ADD FOREIGN KEY ("chapter_id") REFERENCES "Chapter" ("id");

ALTER TABLE "Comment" ADD FOREIGN KEY ("user_id") REFERENCES "User" ("id");

ALTER TABLE "Comment" ADD FOREIGN KEY ("manga_id") REFERENCES "Manga" ("id");

ALTER TABLE "Comment" ADD FOREIGN KEY ("message_id") REFERENCES "Message" ("id");

ALTER TABLE "MangaGenre" ADD FOREIGN KEY ("manga_id") REFERENCES "Manga" ("id");

ALTER TABLE "MangaGenre" ADD FOREIGN KEY ("genre_id") REFERENCES "Genre" ("id");

ALTER TABLE "UserBadge" ADD FOREIGN KEY ("user_id") REFERENCES "User" ("id");

ALTER TABLE "UserBadge" ADD FOREIGN KEY ("badge_id") REFERENCES "Badge" ("id");

ALTER TABLE "Interpreter" ADD FOREIGN KEY ("user_id") REFERENCES "User" ("id");