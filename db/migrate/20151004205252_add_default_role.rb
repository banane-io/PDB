class AddDefaultRole < ActiveRecord::Migration
  def change
    change_column :users, :role, :string, :default => "nonuser"
  end
end
