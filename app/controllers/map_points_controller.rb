class MapPointsController < ApplicationController
  before_action :set_map_point, only: [:show, :edit, :update, :destroy]
  def index
    @map_points = MapPoint.all
  end

  def new
    @map_point = MapPoint.new
  end

  def show
  end

  def create
    @map_point = MapPoint.new(map_point_params)
    if @map_point.save
      #flash[:info] = "Point on the map created"
      redirect_to root_url
    else
      render 'new'
    end
  end

  def destroy
    MapPoint.find(params[:id]).destroy
    #flash[:success] = "Point deleted"
    redirect_to root_url
  end

  def edit
  end

  def update
    if @map_point.update_attributes(map_point_params)
      #flash[:success] = "Point updated"
      redirect_to @map_point
    else
      render 'edit'
    end
  end

  private

    def map_point_params
      params.require(:map_point).permit(:x, :y, :zone)
    end

    def set_map_point
      @map_point = MapPoint.find(params[:id])
    end
end
