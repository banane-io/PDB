json.array!(@terrains) do |terrain|
  json.extract! terrain, :id, :name, :colour
  json.url terrain_url(terrain, format: :json)
end
