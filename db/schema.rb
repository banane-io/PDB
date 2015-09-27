# encoding: UTF-8
# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 20150924220257) do

  # These are extensions that must be enabled in order to support this database
  enable_extension "plpgsql"

  create_table "entities", force: true do |t|
    t.string   "name"
    t.string   "description"
    t.integer  "map_point_id"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  add_index "entities", ["map_point_id", "created_at"], name: "index_entities_on_map_point_id_and_created_at", using: :btree
  add_index "entities", ["map_point_id"], name: "index_entities_on_map_point_id", using: :btree

  create_table "map_points", force: true do |t|
    t.integer  "x"
    t.integer  "y"
    t.string   "zone"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.integer  "terrain_id"
  end

  add_index "map_points", ["terrain_id"], name: "index_map_points_on_terrain_id", using: :btree
  add_index "map_points", ["x", "y"], name: "index_map_points_on_x_and_y", unique: true, using: :btree

  create_table "players", force: true do |t|
    t.string   "username"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.integer  "entity_id"
  end

  add_index "players", ["entity_id"], name: "index_players_on_entity_id", using: :btree

  create_table "terrains", force: true do |t|
    t.text     "name"
    t.text     "colour"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "users", force: true do |t|
    t.string   "email",                  default: "", null: false
    t.string   "encrypted_password",     default: "", null: false
    t.string   "reset_password_token"
    t.datetime "reset_password_sent_at"
    t.datetime "remember_created_at"
    t.integer  "sign_in_count",          default: 0,  null: false
    t.datetime "current_sign_in_at"
    t.datetime "last_sign_in_at"
    t.string   "current_sign_in_ip"
    t.string   "last_sign_in_ip"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string   "provider"
    t.string   "uid"
    t.integer  "player_id"
  end

  add_index "users", ["email"], name: "index_users_on_email", unique: true, using: :btree
  add_index "users", ["player_id"], name: "index_users_on_player_id", using: :btree
  add_index "users", ["reset_password_token"], name: "index_users_on_reset_password_token", unique: true, using: :btree

end
