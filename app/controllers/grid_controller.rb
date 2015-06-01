class GridController < ApplicationController
  def show
    @map_points = MapPoint.includes(:entities).all.group_by(&:x)
    gon.map_points = Hash[*MapPoint.all.map{ |p| [p.id, p] }.flatten]

  end

  def move
  end
end
