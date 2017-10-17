plain = Terrain.create(name: "Plain", colour: "green")
swamp = Terrain.create(name: "Swamp", colour: "DarkGreen")
beach = Terrain.create(name: "Beach", colour: "yellow")
mountain = Terrain.create(name: "Mountain", colour: "gray")
water =  Terrain.create(name: "Water", colour: "blue")
bridge =  Terrain.create(name: "Bridge", colour: "SaddleBrown")

plain.save
swamp.save
beach.save
mountain.save
water.save
bridge.save

map = ActiveSupport::JSON.decode(File.read('db/map.json'))

map.each do |point|
  MapPoint.create!(point)
end


User.create!(email: "test_normal@test.com", password: "test_normal", role: "normal")
User.create!(email: "test_nonuser@test.com", password: "test_nonuser", role: "nonuser")
User.create!(email: "test_admin@test.com", password: "test_admin", role: "admin")