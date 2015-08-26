class AddUniqueIndexToMapPointsXY < ActiveRecord::Migration
  def change
    add_index :map_points, [:x,:y], unique: true
  end
end
