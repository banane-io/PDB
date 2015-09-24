class MapPoint < ActiveRecord::Base
  has_many :entities
  validates :x, presence: true
  validates :y, presence: true

  def neighbors
    neighbors = Hash.new
    Directions::DIRECTIONS.each{ |direction|
      neighbors[direction] = MapPoint.find_by(x: direction.dx + self.x, y: direction.dy + self.y)
    }
    return neighbors
  end

  def load_grid(range)
    range_map = range / 2
    min_x = [0, self.x - range_map].max
    max_x = [25, self.x + range_map].min
    min_y = [0, self.y - range_map].max
    max_y = [25, self.y + range_map].min
    MapPoint.includes(:entities).where(:x => min_x..max_x).where(:y => min_y..max_y).order(x: :asc, y: :asc).group_by(&:y)
  end
end
