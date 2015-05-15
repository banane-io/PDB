# Place all the behaviors and hooks related to the matching controller here.
# All this logic will automatically be available in application.js.
# You can use CoffeeScript in this file: http://coffeescript.org/
console.log gon.map_points
$ -> $('.test').on 'click', ->
  point = gon.map_points[$(this).val()]
  $('#text_x').val point.x
  $('#text_y').val point.y
  $('#text_zone').val point.zone
  return
