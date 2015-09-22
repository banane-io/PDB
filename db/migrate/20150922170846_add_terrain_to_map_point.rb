class AddTerrainToMapPoint < ActiveRecord::Migration
  def change
    add_column :map_points, :terrain_id, :integer
  end
end
