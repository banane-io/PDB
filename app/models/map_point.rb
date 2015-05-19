class MapPoint < ActiveRecord::Base
  has_many :entities
  validates :x, presence: true
  validates :y, presence: true
end
