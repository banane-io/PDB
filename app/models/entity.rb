class Entity < ActiveRecord::Base
  belongs_to :map_point
  has_one :player
  validates :map_point_id, presence: true
end
