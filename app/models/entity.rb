class Entity < ActiveRecord::Base
  belongs_to :map_points
  validates :map_point_id, presence: true
end
