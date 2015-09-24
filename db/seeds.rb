plain = Terrain.create(name: "plain", colour: "green")
swamp = Terrain.create(name: "swamp", colour: "DarkGreen")
beach = Terrain.create(name: "beach", colour: "yellow")
mountain = Terrain.create(name: "mountain", colour: "gray")

plain.save
swamp.save
beach.save
mountain.save

terrains = Array.new
terrains.push plain
terrains.push swamp
terrains.push beach
terrains.push mountain

i = 0
5.times do |x|
  5.times do |y|
    map_point = MapPoint.create(x: x, y: y, zone: "Zone#{i}", terrain_id: terrains[rand(terrains.length)].id)
    map_point.entities.build(name: "Test#{i}", description: "This is a simple description", map_point_id: map_point)
    map_point.save
    i += 1
  end
end
