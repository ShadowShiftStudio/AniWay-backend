CREATE TYPE "sex" AS ENUM (
  'MALE',
  'FEMALE'
);

CREATE TYPE "age_rating" AS ENUM (
  'EVERYONE',
  'TEENAGER',
  'ADULT'
);

CREATE TYPE "role" AS ENUM (
  'USER',
  'MODERATOR',
  'TRANSLATOR',
  'ADMIN'
);

CREATE TYPE "achievement_type" AS ENUM (
  'LIKES',
  'COMMENTS',
  'CHAPTERS',
  'LVL'
);

CREATE TYPE "title_status" AS ENUM (
  'ONGOING',
  'FINISHED',
  'FREEZED',
  'ANNOUNCED'
);

CREATE TYPE "title_type" AS ENUM (
    'MANHWA'
    'MANHUA',
    'MANGA',
    'CARTOON'
);

CREATE TYPE "reading_status" AS ENUM (
  'IN_PROGRESS',
  'PLANNED',
  'COMPLETED',
  'POSTPONED',
  'FAVOURITE'
);

CREATE TABLE "categories" (
  "id" BIGSERIAL PRIMARY KEY,
  "name" varchar(30)
);

CREATE TABLE "title_categories" (
  "title_id" BIGINT,
  "category_id" BIGINT 
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
  "created_at" TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE "password_reset_tokens" (
  "id" BIGSERIAL PRIMARY KEY,
  "user_id" BIGINT,
  "token"  varchar(100),
  "expiry_date" TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE "email_verif_tokens" (
  "id" BIGSERIAL PRIMARY KEY,
  "user_id" BIGINT,
  "token"  varchar(100),
  "expiry_date" TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE "refresh_tokens" (
  "id" BIGSERIAL PRIMARY KEY,
  "user_id" BIGINT,
  "token" varchar UNIQUE,
  "expiry_date" TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE "teams" (
  "id" BIGSERIAL PRIMARY KEY,
  "owner_id" BIGINT,
  "name" varchar(20),
  "description" varchar(150),
  "avatar_url" varchar(150),
  "created_at" TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE "team_chapters" (
  "id" BIGSERIAL PRIMARY KEY,
  "team_id" BIGINT,
  "chapter_id" BIGINT 
);

CREATE TABLE "titles" (
  "id" BIGSERIAL PRIMARY KEY,
  "name" varchar(100),
  "original_name" varchar(100),
  "age_rating" age_rating,
  "year" integer,
  "background_image_url" varchar(300),
  "description" text[],
  "status" title_status,
  "type" title_type,
  "views" integer
);

CREATE TABLE "chapter_images" (
  "id" BIGSERIAL PRIMARY KEY,
  "chapter_id" BIGINT,
  "image_index" integer,
  "url" varchar(100)
);

CREATE TABLE "chapters" (
  "id" BIGSERIAL PRIMARY KEY,
  "title_id" BIGINT,
  "team_id" BIGINT,
  "name" varchar(300),
  "number" integer,
  "volume" integer,
  "number_of_pages" integer,
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

CREATE TABLE "achievements" (
  "id" BIGSERIAL PRIMARY KEY,
  "header" varchar(30),
  "avatar_url" varchar,
  "text" varchar(50),
  "type" achievement_type,
  "condition" integer
);

CREATE TABLE "user_achievements" (
  "user_id" BIGSERIAL,
  "achievement_id" BIGINT,
  "received" boolean
);

CREATE TABLE "comments" (
  "id" BIGSERIAL PRIMARY KEY,
  "author_id" BIGINT,
  "title_id" BIGINT NULL,
  "chapter_id" BIGINT NULL,
  "text" varchar(350),
  "created_at" TIMESTAMP WITHOUT TIME ZONE,
  "updated_at" TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE "related_titles" (
  "base_title_id" BIGINT,
  "related_title_id" BIGINT 
);

CREATE TABLE "user_info_of_chapters" (
  "chapter_id" BIGINT,
  "user_id" BIGINT,
  "is_liked" boolean,
  "is_read" boolean
);

CREATE TABLE "user_info_of_titles" (
  "title_id" BIGINT,
  "user_id" BIGINT,
  "reading_status" reading_status,
  "rating" integer
);

CREATE TABLE "team_users" (
  "user_id" BIGINT,
  "team_id" BIGINT,
  "status" varchar(20)
);

CREATE TABLE "users_badges" (
  "user_id" BIGINT,
  "badge_id" BIGINT 
);

CREATE TABLE "titles_genres" (
  "title_id" BIGINT,
  "genre_id" BIGINT 
);

COMMENT ON COLUMN "user_info_of_titles"."rating" IS 'min 1 max 5';

ALTER TABLE "teams" ADD FOREIGN KEY ("owner_id") REFERENCES "users" ("id");

ALTER TABLE "refresh_tokens" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "chapters" ADD FOREIGN KEY ("title_id") REFERENCES "titles" ("id");

ALTER TABLE "chapters" ADD FOREIGN KEY ("team_id") REFERENCES "teams" ("id");

ALTER TABLE "comments" ADD FOREIGN KEY ("author_id") REFERENCES "users" ("id");

ALTER TABLE "comments" ADD FOREIGN KEY ("title_id") REFERENCES "titles" ("id");

ALTER TABLE "comments" ADD FOREIGN KEY ("chapter_id") REFERENCES "chapters" ("id");

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

ALTER TABLE "title_categories" ADD FOREIGN KEY ("title_id") REFERENCES "titles" ("id");

ALTER TABLE "title_categories" ADD FOREIGN KEY ("category_id") REFERENCES "categories" ("id");

ALTER TABLE "chapter_images" ADD FOREIGN KEY ("chapter_id") REFERENCES "chapters" ("id");

ALTER TABLE "user_achievements" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "user_achievements" ADD FOREIGN KEY ("achievement_id") REFERENCES "achievements" ("id");