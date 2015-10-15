require 'test_helper'

class GridControllerTest < ActionController::TestCase
  setup do
    sign_in users(:admin)
  end

  test "should get show" do
    get :show
    assert_response :success
  end
end
