class AddEntityRefToPlayers < ActiveRecord::Migration
  def change
    add_reference :players, :entity, index: true, foreign_key: true
  end
end
