class MapPoint < ActiveRecord::Base
  has_many :entities
  belongs_to :terrain
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
    minMaxX = calculateMinMax(range_map, MapPoint.minimum("x"), MapPoint.maximum("x"), self.x)
    minMaxY = calculateMinMax(range_map, MapPoint.minimum("y"), MapPoint.maximum("y"), self.y)
    MapPoint.includes(:entities).where(:x => minMaxX[0]..minMaxX[1]).where(:y => minMaxY[0]..minMaxY[1]).order(x: :asc, y: :asc).group_by(&:y)
  end

  private
    def calculateMinMax(range_map, minMap, maxMap, position)
      overflow = 0
      underflow = 0
      borderLeft = position - range_map
      borderRight = position + range_map
      if(borderLeft < minMap)
        overflow = borderLeft.abs
      end
      if(borderRight > maxMap)
        underflow = maxMap - borderRight
      end

      min = [minMap, borderLeft].max
      min = min - underflow
      max = [maxMap, borderRight].min
      max = max + overflow
      return [min,max]
    end
end
