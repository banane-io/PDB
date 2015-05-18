Rails.application.routes.draw do
  root 'static_pages#home'
  get 'static_pages/home'
  get 'static_pages/help'
  get 'grid/show'
  get 'grid/move'
  resources :map_points
  resources :entities

end
