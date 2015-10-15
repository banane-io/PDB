require 'test_helper'

class PlayerTest < ActiveSupport::TestCase
  def setup
    @player = Player.new(username: "Jonh", entity_id: 1)
  end

  test "should be valid" do
    assert @player.valid?
  end

  #test "should delete entity if delete the player" do
    #assert @player.destroy
   # assert_not MapPoint.find(1)
  #end

  test "username should be present" do
    @player.username = nil
    assert_not @player.valid?
  end


end
