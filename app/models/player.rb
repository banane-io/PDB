class Player < ActiveRecord::Base
  has_one :user
  has_one :entity
end
