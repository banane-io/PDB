class TerrainsController < ApplicationController
  before_action :authenticate_user!, only: [:edit, :update, :destroy]
  before_action :set_terrain, only: [:show, :edit, :update, :destroy]

  respond_to :html

  def index
    @terrains = Terrain.all
    respond_with(@terrains)
  end

  def show
    respond_with(@terrain)
  end

  def new
    @terrain = Terrain.new
    respond_with(@terrain)
  end

  def edit
  end

  def create
    @terrain = Terrain.new(terrain_params)
    @terrain.save
    respond_with(@terrain)
  end

  def update
    @terrain.update(terrain_params)
    respond_with(@terrain)
  end

  def destroy
    @terrain.destroy
    respond_with(@terrain)
  end

  private
    def set_terrain
      @terrain = Terrain.find(params[:id])
    end

    def terrain_params
      params.require(:terrain).permit(:name, :colour)
    end
end
