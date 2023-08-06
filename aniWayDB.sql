CREATE TYPE "sex" AS ENUM (
  'male',
  'female'
);

CREATE TYPE "age_rating" AS ENUM (
  'everyone',
  'teenager',
  'adult'
);

CREATE TYPE "role" AS ENUM (
  'USER',
  'MODERATOR',
  'TRANSLATOR'
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

CREATE TABLE "categories" (
  "id" BIGSERIAL PRIMARY KEY,
  "name" varchar(30)
);

CREATE TABLE "title_categories" (
  "id" BIGSERIAL PRIMARY KEY,
  "title_id" BIGSERIAL,
  "category_id" BIGSERIAL
);

CREATE TABLE "users" (
  "id" BIGSERIAL PRIMARY KEY,
  "username" varchar(20) UNIQUE,
  "email" varchar(100),
  "email_verified" boolean,
  "password" varchar(100),
  "sex" sex,
  "role" role,
  "xp" integer,
  "pass_xp" integer,
  "balance" integer,
  "password_reset_token_id" BIGSERIAL,
  "email_verif_token_id" BIGSERIAL,
  "created_at" TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE "password_reset_tokens" (
  "id" BIGSERIAL PRIMARY KEY,
  "token"  varchar(100),
  "expiry_date" TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE "email_verif_tokens" (
  "id" BIGSERIAL PRIMARY KEY,
  "token"  varchar(100),
  "expiry_date" TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE "refresh_tokens" (
  "id" BIGSERIAL PRIMARY KEY,
  "user_id" BIGSERIAL,
  "token" varchar UNIQUE,
  "expiry_date" TIMESTAMP WITHOUT TIME ZONE
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
  "age_rating" age_rating,
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

CREATE TABLE "comments" (
  "id" BIGSERIAL PRIMARY KEY,
  "author_id" BIGSERIAL,
  "text" varchar(350),
  "created_at" TIMESTAMP WITHOUT TIME ZONE,
  "updated_at" TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE "related_titles" (
  "base_title_id" BIGSERIAL,
  "related_title_id" BIGSERIAL
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

ALTER TABLE "teams" ADD FOREIGN KEY ("owner_id") REFERENCES "users" ("id");

ALTER TABLE "refresh_tokens" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "chapters" ADD FOREIGN KEY ("title_id") REFERENCES "titles" ("id");

ALTER TABLE "chapters" ADD FOREIGN KEY ("team_id") REFERENCES "teams" ("id");

ALTER TABLE "comments" ADD FOREIGN KEY ("author_id") REFERENCES "users" ("id");

ALTER TABLE "title_comments" ADD FOREIGN KEY ("comment_id") REFERENCES "comments" ("id");

ALTER TABLE "title_comments" ADD FOREIGN KEY ("title_id") REFERENCES "titles" ("id");

ALTER TABLE "chapter_comments" ADD FOREIGN KEY ("comment_id") REFERENCES "comments" ("id");

ALTER TABLE "chapter_comments" ADD FOREIGN KEY ("chapter_id") REFERENCES "chapters" ("id");

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

ALTER TABLE "titles_categories" ADD FOREIGN KEY ("title_id") REFERENCES "titles" ("id");

ALTER TABLE "titles_categories" ADD FOREIGN KEY ("category_id") REFERENCES "categories" ("id");