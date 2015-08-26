class Entity < ActiveRecord::Base
  belongs_to :map_point
  has_one :player
  validates :map_point_id, presence: true

  def move(new_position)
    self.map_point = new_position
    self.save
  end
end
