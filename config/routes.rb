Rails.application.routes.draw do
  resources :terrains

  devise_for :users, :controllers => { omniauth_callbacks: 'callbacks' }
  root 'static_pages#home'
  get 'static_pages/home'
  get 'static_pages/help'
  get 'grid/show'
  get 'grid/point/:id', to: 'grid#point'
  get 'grid/move/:map_point_id', to: 'grid#move', as: 'grid_move'
  resources :map_points
  resources :entities
  resources :players
end
