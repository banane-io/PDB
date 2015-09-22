class MapPoint < ActiveRecord::Base
  has_many :entities
  has_one :terrain
  validates :x, presence: true
  validates :y, presence: true

  def neighbors
    neighbors = Hash.new
    Directions::DIRECTIONS.each{ |direction|
      neighbors[direction] = MapPoint.find_by(x: direction.dx + self.x, y: direction.dy + self.y)
    }
    return neighbors
  end
end
