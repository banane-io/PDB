class CreateEntities < ActiveRecord::Migration
  def change
    create_table :entities do |t|
      t.string :name
      t.string :description
      t.references :map_point, index: true, foreign_key: true

      t.timestamps
    end
    add_index :entities, [:map_point_id, :created_at]
  end
end
