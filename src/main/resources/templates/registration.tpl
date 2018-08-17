layout 'layout.tpl', title: 'Registration',
        content: contents {
            div (class: 'col-md-6 col-md-offset-3') {
                if(success) {
                    div (class: 'alert alert-info', "You've successfully registered to our awesome app!")
                }

                h1 ('Regristration')
                form (id:'registration', action:'/registration', method:'post') {
                    if (formErrors) {
                        formErrors.each { error ->
                          p error.defaultMessage
                        }
                    }
                    input (name:'_csrf', value: _csrf.token, type: 'hidden')
                    div (class: 'form-group') {
                       label (for:'username', 'Username')
                       input (name:'username', type:'text', value:'',
                               class:'form-control', autofocus:'autofocus',
                               placeholder: 'Username')
                    }

                    div (class: 'form-group') {
                        label (for:'password', 'Password')
                        input (name:'password', type:'password', class:'form-control', placeholder: 'Password')
                    }

                    div (class: 'form-group') {
                        label (for:'passwordConfirm', 'Confirm password')
                        input (name:'passwordConfirm', type:'password', class:'form-control', placeholder: 'Confrim password')
                    }

                    div (class: 'form-group') {
                        button(type:'submit', class:'btn btn-success', 'Register')
                    }

                    span ("Already registered? ${$a(href:'/login', "Login here")}")

                }
            }
        }