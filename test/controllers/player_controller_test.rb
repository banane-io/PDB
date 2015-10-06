require 'test_helper'

class PlayerControllerTest < ActionController::TestCase
  setup do
    sign_in users(:admin)
    @player = players(:one)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

end
