class UsersController < ApplicationController
  authorize_resource :except => [:new, :create]
  # load_and_authorize_resource :only => [:index, :show]

  before_action :set_user, only: [:update, :destroy]

  def new
    @user = User.new  
  end

  # GET /users/1
  # GET /users/1.json
  def show
    @user = User.find_by_id(current_user.id)
  end

  # GET /users/1/edit
  def edit
    @user = User.find_by_id(current_user.id)
  end

  # PATCH/PUT /users/1
  # PATCH/PUT /users/1.json
  def update
    respond_to do |format|
      if @user.update(user_params)
        format.html { redirect_to @user, notice: 'User was successfully updated.' }
        format.json { render :show, status: :ok, location: @user }
      else
        format.html { render :edit }
        format.json { render json: @user.errors, status: :unprocessable_entity }
      end
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_user
      @user = User.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def user_params
      params.require(:user).permit(:role, :email)
    end
end
