$ -> $('.grid_point').on 'click', ->
  id = $(this).val()
  $.ajax
    url: "/grid/point/#{id}"
    type: "GET"
    success: (data) ->
      $("#current_map_point").replaceWith data
  return
