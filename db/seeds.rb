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
