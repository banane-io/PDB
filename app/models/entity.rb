class Entity < ActiveRecord::Base
  belongs_to :map_point
  has_one :player
  validates :map_point_id, presence: true
  validates :name, presence: true

  def move(new_position)
    if(!new_position.nil?)
      self.map_point = new_position
      self.save
    end
  end
end
