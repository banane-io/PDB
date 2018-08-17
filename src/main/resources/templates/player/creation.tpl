layout 'layout.tpl', true, title: 'Player Creation',
        content: contents {
            div(class:"row") {
                div(class:"col-md-6 col-md-offset-3") {
                    h1('Player Creation')
                    form (id:'playerForm', action:'/player/creation', method:'post') {
                        if (formErrors) {
                            div(class:'alert alert-error') {
                                formErrors.each { error ->
                                  p error.defaultMessage
                                }
                            }
                        }
                        input (name:'_csrf', value: _csrf.token, type: 'hidden')
                        div(class:fieldErrors?.username ? 'form-group has-error' : 'form-group') {
                           label (for:'username', 'Username')
                           input (name:'username', type:'text', value:'',
                                   class:'form-control', autofocus:'autofocus',
                                   placeholder: 'Username')
                            if(fieldErrors?.username) {
                                p(class: 'error-message') {
                                    yield fieldErrors.username
                                }
                            }
                       }

                        div(class:'form-group') {
                            input (type:'submit', value:'Create', class:'btn btn-success')
                        }
                    }
                }
            }
        }