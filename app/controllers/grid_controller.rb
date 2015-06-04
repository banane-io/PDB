class GridController < ApplicationController
  def show
    @map_points = MapPoint.includes(:entities).all.group_by(&:x)
    @map_point = MapPoint.includes(:entities).first
  end

  def point

  end

  def move
  end
end
