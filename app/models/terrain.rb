class Terrain < ActiveRecord::Base
  belongs_to :map_point
  validates :name, presence: true
  validates :colour, presence: true
end
