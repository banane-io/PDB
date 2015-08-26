class CreatePlayers < ActiveRecord::Migration
  def change
    create_table :players do |t|
      t.string :username

      t.timestamps
    end
    change_table :users do |t|
      t.foreign_key :players
    end

    change_table :entities do |t|
      t.foreign_key :players
    end
  end
end
