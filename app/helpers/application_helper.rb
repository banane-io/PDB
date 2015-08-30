module ApplicationHelper
  def current_player
    user = current_user
    user.player
  end
end
