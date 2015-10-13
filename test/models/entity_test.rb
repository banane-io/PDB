require 'test_helper'

class EntityTest < ActiveSupport::TestCase
  def setup
    @entity = Entity.new(name: "Joe", description: "This is an entity", map_point_id: 1)
  end

  test "should be valid" do
    assert @entity.valid?
  end

  test "name should be present" do
    @entity.name = nil
    assert_not @entity.valid?
  end

  test "description can be null" do
    @entity.description = nil
    assert @entity.valid?
  end

  test "entity could not belong to anybody" do
    @entity.player = nil
    assert @entity.valid?
  end

  test "entity can move" do
    assert @entity.move(MapPoint.find(2))
    assert_equal 2, @entity.map_point_id
  end

  test "can't move to nil" do
    assert_not @entity.move(nil)
    assert_equal 1, @entity.map_point_id
  end
end
