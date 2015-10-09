require 'test_helper'

class PlayerControllerTest < ActionController::TestCase
  setup do
    @player = players(:one)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

end
