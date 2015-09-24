class AddTerrainRefToMapPoint < ActiveRecord::Migration
  def change
    add_reference :map_points, :terrain, index: true
  end
end
