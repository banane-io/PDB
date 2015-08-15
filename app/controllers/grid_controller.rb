class GridController < ApplicationController
  before_action :set_map_point, only: [:point]
  before_action :set_map_point_to_move_to, only: [:move]
  def show
    @map_points = MapPoint.includes(:entities).all.group_by(&:x)

    me = current_user
    @player = me.player
    @map_point = me.player.entity.map_point
  end

  def point
    render partial: 'map_points/map_point'
  end

  def move
    me = current_user
    @player = me.player
    me.player.entity.move(MapPoint.find(params[:map_point_id]))
    redirect_to grid_show_path
  end

  private

    def set_map_point
      @map_point = MapPoint.includes(:entities).find(params[:id])
    end

    def set_map_point_to_move_to
      @map_point = MapPoint.find(params[:map_point_id])
    end


end
