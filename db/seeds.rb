i = 0
5.times do |x|
  5.times do |y|
    MapPoint.create!(x: x, y: y, zone: "Zone#{i}")
    i += 1
  end
end
