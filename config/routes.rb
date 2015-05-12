Rails.application.routes.draw do
  get 'grid/show'

  get 'grid/move'
  root 'map_points#index'
  resources :map_points
end
