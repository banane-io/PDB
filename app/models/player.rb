class Player < ActiveRecord::Base
  has_one :user
  belongs_to :entity
end
