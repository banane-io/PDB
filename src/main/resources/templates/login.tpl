layout 'layout.tpl', true, title: 'Login',
        content: contents {
            div(class: 'row') {
               div (class:'col-md-6 col-md-offset-3') {
                   form (id:'loginForm', action:'/login', method:'post') {
                       if(error) {
                           div(class:'alert alert-danger') {
                               yield 'Invalid usernamre or password'
                           }
                       }

                       if(message) {
                           div(class:'alert alert-info') {
                               yield message
                           }
                       }
                       input (name:'_csrf', value: _csrf.token, type: 'hidden')

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
                                    div {
                                       input (type:'submit', value:'Log In', class:'form-control btn btn-info')
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