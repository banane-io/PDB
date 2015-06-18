class AddPlayerRefToUsers < ActiveRecord::Migration
  def change
    add_reference :users, :player, index: true, foreign_key: true
  end
end
