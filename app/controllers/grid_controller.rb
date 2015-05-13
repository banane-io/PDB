class GridController < ApplicationController
  def show
    @map_points_temp = MapPoint.all

    @map_points = @map_points_temp.map {|map_point| [map_point.x, map_point]}
  end

  def move
  end
end
