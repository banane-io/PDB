i = 0
5.times do |x|
  5.times do |y|
    map_point = MapPoint.create(x: x, y: y, zone: "Zone#{i}")
    map_point.entities.build(name: "Test#{i}", description: "This is a simple description", map_point_id: map_point)
    map_point.save
    i += 1
  end
end
