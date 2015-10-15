require 'test_helper'

class StaticPagesControllerTest < ActionController::TestCase
  test "should get home" do
    get :home
    assert_response :success
  end

  test "should get help" do
    get :help
    assert_response :success
  end

  test "should get unauthorized" do
    get :unauthorized
    assert_response :success
  end

  test "should get login required" do
    get :login_required
    assert_response :success
  end

end
