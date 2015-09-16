class CreateTerrains < ActiveRecord::Migration
  def change
    create_table :terrains do |t|
      t.string :name
      t.string :colour

      t.timestamps
    end
  end
end
