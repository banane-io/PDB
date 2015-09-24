i = 0
25.times do |x|
  25.times do |y|
    map_point = MapPoint.create(x: x, y: y, zone: "Zone#{i}")
    map_point.entities.build(name: "Test#{i}", description: "This is a simple description", map_point_id: map_point)
    map_point.save
    i += 1
  end
end
