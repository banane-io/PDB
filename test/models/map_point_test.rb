require 'test_helper'

class MapPointTest < ActiveSupport::TestCase
  def setup
    @request.env["devise.mapping"] = Devise.mappings[:user]
    get :new
    @map_point = MapPoint.new(x: 5, y: 6)
  end

  test "should be valid" do
    assert @map_point.valid?
  end

  test "x position should be present" do
    @map_point.x = nil
    assert_not @map_point.valid?
  end

  test "y position should be present" do
    @map_point.y = nil
    assert_not @map_point.valid?
  end
end
