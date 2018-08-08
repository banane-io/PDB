layout 'layout.tpl', title: 'Login',
        content: contents {
            div(class: 'row') {
               div (class:'col-md-6 col-md-offset-3') {
                   form (id:'loginForm', action:'/login', method:'post') {
                       if(error) {
                           div(class:'alert alert-danger') {
                               yield 'Invalid usernamre or password'
                           }
                       }

                       if(logout) {
                           div(class:'alert alert-info') {
                               yield 'You have been logged out.'
                           }
                       }

                       div(class:'form-group') {
                           label (for:'username', 'Username')
                           input (name:'username', type:'text', value:'',
                                   class:'form-control', autofocus:'autofocus',
                                   placeholder: 'Username')
                       }

                       div(class:'form-group') {
                           label (for:'password', 'Password')
                           input (name:'password', type:'password', class:'form-control', placeholder: 'Password')
                       }

                       div(class:'form-group') {
                           div (class: 'row') {
                                div(class:'col-sm-6 col-sm-offset-3') {
                                    div (class:'form-control btn btn-info') {
                                       input (type:'submit', value:'Log In')
                                    }
                                }
                           }
                       }
                   }
               }
            }

            p {
                a(href:"/") {
                    yield 'Back to home page'
                }
            }
        }