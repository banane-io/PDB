class CreateTerrains < ActiveRecord::Migration
  def change
    create_table :terrains do |t|
      t.text :name
      t.text :colour

      t.timestamps
    end
  end
end
