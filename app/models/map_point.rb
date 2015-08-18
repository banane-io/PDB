class MapPoint < ActiveRecord::Base
  has_many :entities
  validates :x, presence: true
  validates :y, presence: true

  def neighbors
    neighbors = {}
    Directions::DIRECTIONS.each{ |direction|
      neighbors[direction] = MapPoint.find_by(x: direction.dx + self.x, y: direction.dy + self.y)
    }
  end
end
