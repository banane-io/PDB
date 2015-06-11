Rails.application.routes.draw do
  get 'player/new'

  devise_for :users, :controllers => { :omniauth_callbacks => "callbacks"}
  root 'static_pages#home'
  get 'static_pages/home'
  get 'static_pages/help'
  get 'grid/show'
  get 'grid/point/:id', to: 'grid#point'
  get 'grid/move'
  resources :map_points
  resources :entities

end
