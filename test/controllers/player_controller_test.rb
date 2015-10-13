require 'test_helper'

class PlayersControllerTest < ActionController::TestCase
  setup do
    #@player = players(:one)
    sign_in users(:admin)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

end
