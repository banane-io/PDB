$ -> $('.grid_point').on 'click', ->
  $('td').removeClass("clicked_zone")
  id = $(this).val()
  parentTd = $(this).parent()
  parentTd.addClass("clicked_zone")
  $.ajax
    url: "/grid/point/#{id}"
    type: "GET"
    success: (data) ->
      $("#current_map_point").replaceWith data
  return
