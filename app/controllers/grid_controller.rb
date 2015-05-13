class GridController < ApplicationController
  def show
    @map_points = MapPoint.all.group_by(&:x)
  end

  def move
  end
end
