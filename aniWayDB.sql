CREATE TYPE "sex" AS ENUM (
  'male',
  'female'
);

CREATE TYPE "title_status" AS ENUM (
  'ongoing',
  'finished',
  'freezed',
  'announced'
);

CREATE TYPE "title_type" AS ENUM (
  'manhwa',
  'manhua',
  'manga',
  'cartoon'
);

CREATE TYPE "reading_status" AS ENUM (
  'in_progress',
  'planned',
  'completed',
  'postponed'
);

CREATE TABLE "users" (
  "id" BIGSERIAL PRIMARY KEY,
  "username" varchar(20) UNIQUE,
  "email" varchar(100),
  "password" varchar(100),
  "sex" sex,
  "xp" integer,
  "pass_xp" integer,
  "balance" integer,
  "is_hentai_hidden" boolean,
  "is_yuri_hidden" boolean,
  "is_yaoi_hidden" boolean,
  "created_at" TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE "teams" (
  "id" BIGSERIAL PRIMARY KEY,
  "owner_id" BIGSERIAL,
  "name" varchar(20),
  "description" varchar(150),
  "created_at" TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE "titles" (
  "id" BIGSERIAL PRIMARY KEY,
  "name" varchar(100),
  "original_name" varchar(100),
  "year" integer,
  "description" text[],
  "status" title_status,
  "type" title_type,
  "views" integer
);

CREATE TABLE "chapters" (
  "id" BIGSERIAL PRIMARY KEY,
  "title_id" BIGSERIAL,
  "team_id" BIGSERIAL,
  "name" varchar(300),
  "number" integer,
  "volume" integer,
  "views" integer,
  "created_at" TIMESTAMP WITHOUT TIME ZONE,
  "updated_at" TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE "genres" (
  "id" BIGSERIAL PRIMARY KEY,
  "name" varchar(20) UNIQUE
);

CREATE TABLE "badges" (
  "id" BIGSERIAL PRIMARY KEY,
  "name" varchar(20) UNIQUE,
  "created_at" TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE "title_comments" (
  "id" BIGSERIAL PRIMARY KEY,
  "title_id" BIGSERIAL,
  "comment_id" BIGSERIAL
);

CREATE TABLE "chapter_comments" (
  "id" BIGSERIAL PRIMARY KEY,
  "chapter_id" BIGSERIAL,
  "comment_id" BIGSERIAL
);

CREATE TABLE "related_titles" (
  "base_title_id" BIGSERIAL,
  "related_title_id" BIGSERIAL
);

CREATE TABLE "comment" (
  "id" BIGSERIAL PRIMARY KEY,
  "author_id" BIGSERIAL,
  "text" varchar(350),
  "created_at" TIMESTAMP WITHOUT TIME ZONE,
  "updated_at" TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE "user_info_of_chapters" (
  "chapter_id" BIGSERIAL,
  "user_id" BIGSERIAL,
  "is_liked" boolean,
  "is_readed" boolean
);

CREATE TABLE "user_info_of_titles" (
  "title_id" BIGSERIAL,
  "user_id" BIGSERIAL,
  "reading_status" reading_status,
  "bookmarked" boolean,
  "rating" integer
);

CREATE TABLE "team_users" (
  "user_id" BIGSERIAL,
  "team_id" BIGSERIAL,
  "status" varchar(20)
);

CREATE TABLE "users_badges" (
  "user_id" BIGSERIAL,
  "badge_id" BIGSERIAL
);

CREATE TABLE "titles_genres" (
  "title_id" BIGSERIAL,
  "genre_id" BIGSERIAL
);

COMMENT ON COLUMN "user_info_of_titles"."rating" IS 'min 1 max 5';

ALTER TABLE "users" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "teams" ADD FOREIGN KEY ("owner_id") REFERENCES "users" ("id");

ALTER TABLE "chapters" ADD FOREIGN KEY ("title_id") REFERENCES "titles" ("id");

ALTER TABLE "chapters" ADD FOREIGN KEY ("team_id") REFERENCES "teams" ("id");

ALTER TABLE "title_comments" ADD FOREIGN KEY ("title_id") REFERENCES "titles" ("id");

ALTER TABLE "title_comments" ADD FOREIGN KEY ("author_id") REFERENCES "users" ("id");

ALTER TABLE "title_comments" ADD FOREIGN KEY ("text") REFERENCES "comment_texts" ("id");

ALTER TABLE "chapter_comments" ADD FOREIGN KEY ("chapter_id") REFERENCES "chapters" ("id");

ALTER TABLE "chapter_comments" ADD FOREIGN KEY ("author_id") REFERENCES "users" ("id");

ALTER TABLE "chapter_comments" ADD FOREIGN KEY ("text") REFERENCES "comment_texts" ("id");

ALTER TABLE "related_titles" ADD FOREIGN KEY ("base_title_id") REFERENCES "titles" ("id");

ALTER TABLE "related_titles" ADD FOREIGN KEY ("related_title_id") REFERENCES "titles" ("id");

ALTER TABLE "user_info_of_chapters" ADD FOREIGN KEY ("chapter_id") REFERENCES "chapters" ("id");

ALTER TABLE "user_info_of_chapters" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "user_info_of_titles" ADD FOREIGN KEY ("title_id") REFERENCES "titles" ("id");

ALTER TABLE "user_info_of_titles" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "team_users" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "team_users" ADD FOREIGN KEY ("team_id") REFERENCES "teams" ("id");

ALTER TABLE "users_badges" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "users_badges" ADD FOREIGN KEY ("badge_id") REFERENCES "badges" ("id");

ALTER TABLE "titles_genres" ADD FOREIGN KEY ("title_id") REFERENCES "titles" ("id");

ALTER TABLE "titles_genres" ADD FOREIGN KEY ("genre_id") REFERENCES "genres" ("id");
