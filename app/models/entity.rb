class Entity < ActiveRecord::Base
  belongs_to :map_point
  validates :map_point_id, presence: true
end
