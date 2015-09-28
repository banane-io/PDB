class ApplicationController < ActionController::Base
  # Prevent CSRF attacks by raising an exception.
  # For APIs, you may want to use :null_session instead.
  protect_from_forgery with: :exception

  def after_sign_in_path_for(resource_or_scope)
    if current_user.player.nil?
       new_player_path
    else
       root_path
    end
  end

  def check_if_admin
    if not current_user.nil?
      if current_user.role.downcase != "admin".downcase
        redirect_to :unauthorized_redirect_path
      end
    else
      redirect_to :login_required_path
    end
  end

  def check_if_login
    if current_user.nil?
      redirect_to :login_required_path
    end
  end

  include ApplicationHelper
end
