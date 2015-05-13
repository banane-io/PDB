class CreateEntities < ActiveRecord::Migration
  def change
    create_table :entities do |t|
      t.string :name
      t.string :description

      t.timestamps
    end
    add_index :entities, [:map_point_id, :created_at]
  end
end
