class Player < ActiveRecord::Base
  has_one :user
  belongs_to :entity, :dependent => :delete
  validates :username, presence: true

end
