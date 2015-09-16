class Terrain < ActiveRecord::Base
  validates :name, presence: true
  validates :colour, presence: true
end
