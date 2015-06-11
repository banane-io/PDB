class PlayersController < ApplicationController
  before_action :authenticate_user!
  before_action :set_player, only: [:show, :edit, :update, :destroy]
  def index
    @players = Player.all
  end

  def new
    @player = Player.new
  end

  def show
  end

  def create
    @player = Player.new(player_params)
    @player.build_user = current_user
    if @player.save
      #flash[:info] = "Point on the map created"
      redirect_to root_url
    else
      render 'new'
    end
  end

  def destroy
    Player.find(params[:id]).destroy
    #flash[:success] = "Point deleted"
    redirect_to root_url
  end

  def edit
  end

  def update
    if @player.update_attributes(player_params)
      #flash[:success] = "Point updated"
      redirect_to @player
    else
      render 'edit'
    end
  end

  private

    def player_params
      params.require(:player).permit(:username)
    end

    def set_player
      @player = Player.find(params[:id])
    end
end
