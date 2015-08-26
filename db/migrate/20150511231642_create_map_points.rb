class CreateMapPoints < ActiveRecord::Migration
  def change
    create_table :map_points do |t|
      t.integer :x
      t.integer :y
      t.string :zone

      t.timestamps
    end
  end
end
