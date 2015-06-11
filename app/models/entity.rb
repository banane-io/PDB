class Entity < ActiveRecord::Base
  belongs_to :map_point
  belongs_to :player
  validates :map_point_id, presence: true
end
