class Directions
  attr_accessor(:dx, :dy)
  def initialize(dx, dy)
    @dx = dx
    @dy = dy
  end

  NORTHWEST = new(-1, -1)
  NORTH = new(0, -1)
  NORTHEAST = new(1, -1)
  EAST = new(1, 0)
  SOUTHEAST = new(1, 1)
  SOUTH = new(0, 1)
  SOUTHWEST = new(-1, 1)
  WEST = new(-1, 0)

  DIRECTIONS = [ NORTHWEST, NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST,
      WEST ]
  class << self
    private :new
  end
end
