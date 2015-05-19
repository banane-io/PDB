$ -> $('.grid_point').on 'click', ->
  point = gon.map_points[$(this).val()]
  $('#text_x').val point.x
  $('#text_y').val point.y
  $('#text_zone').val point.zone
  return
