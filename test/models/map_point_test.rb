require 'test_helper'

class MapPointTest < ActiveSupport::TestCase
  def setup
    @map_point = MapPoint.new(x:115, y: 116)
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

  test "complete grid of neighbors" do
    neighbors = map_points(:point_28).neighbors
    assert_equal 8, neighbors.length
    Directions::DIRECTIONS.each{ |direction|
      assert_not neighbors[direction].nil?
    }
  end

  test "imcomplete grid of neighbors" do
    neighbors = map_points(:point_1).neighbors
    assert_equal 8, neighbors.length
    assert neighbors[Directions::NORTHWEST].nil?
    assert neighbors[Directions::NORTH].nil?
    assert neighbors[Directions::NORTHEAST].nil?
    assert_not neighbors[Directions::EAST].nil?
    assert neighbors[Directions::WEST].nil?
    assert neighbors[Directions::SOUTHWEST].nil?
    assert_not neighbors[Directions::SOUTH].nil?
    assert_not neighbors[Directions::SOUTHEAST].nil?
  end

  test "none neighbors" do
    neighbors = @map_point.neighbors
    assert_equal 8, neighbors.length
    assert neighbors[Directions::NORTHWEST].nil?
    assert neighbors[Directions::NORTH].nil?
    assert neighbors[Directions::NORTHEAST].nil?
    assert neighbors[Directions::EAST].nil?
    assert neighbors[Directions::WEST].nil?
    assert neighbors[Directions::SOUTHWEST].nil?
    assert neighbors[Directions::SOUTH].nil?
    assert neighbors[Directions::SOUTHEAST].nil?
  end

  test "range of map" do
    grid = map_points(:point_261).load_grid(2)
    assert_equal 3, grid.length
    grid.each do |key, array|
      assert_equal 3, array.length
    end
  end

  test "top-left corner of a map" do
    grid = map_points(:point_1).load_grid(2)
    assert_equal 3, grid.length
    grid.each do |key, array|
      assert_equal 3, array.length
    end
  end

  test "top-right corner of a map" do
    grid = map_points(:point_601).load_grid(2)
    assert_equal 3, grid.length
    grid.each do |key, array|
      assert_equal 3, array.length
    end
  end

  test "bottom-left corner of a map" do
    grid = map_points(:point_25).load_grid(2)
    assert_equal 3, grid.length
    grid.each do |key, array|
      assert_equal 3, array.length
    end
  end

  test "bottom-right corner of a map" do
    grid = map_points(:point_625).load_grid(2)
    assert_equal 3, grid.length
    grid.each do |key, array|
      assert_equal 3, array.length
    end
  end

end
