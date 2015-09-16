require 'test_helper'

class TerrainTest < ActiveSupport::TestCase
  def setup
    @terrain = Terrain.new(name: "Test", colour: "Green")
  end

  test "should be valid" do
    assert @terrain.valid?
  end

  test "name should be present" do
    @terrain.name = ""
    assert_not @terrain.valid?
  end

  test "colour should be present" do
    @terrain.colour = ""
    assert_not @terrain.valid?
  end
end
