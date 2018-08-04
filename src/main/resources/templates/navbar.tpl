div (class:"container") {
    div(class:"navbar-header") {
        a(class:"navbar-brand", href:"#") {
            yield 'PDB'
        }
        div(id:"navbar", class:"collapse navbar-collapse") {
            ul(class:"nav navbar-nav") {
                li {
                    a (href:"/") {
                        yield 'Home'
                    }
                }
                li {
                   // sec:authorize="!isAuthenticated()"
                    a (href:"/registration") {
                        yield 'Register'
                    }
                }
                li {
                    //sec:authorize="!isAuthenticated()"
                    a(href:"/login") {
                        yield 'Login'
                    }
                }
                li {

                //sec:authorize="isAuthenticated()">
                //<span sec:authentication="name"></span> |
                                    a (href:"/logout") {
                                        yield 'Sign Out'
                                    }
                }
                li  {
                    a(href:"/player/creation") {
                        yield 'Player creation'
                    }
                }
            }
        }
    }
}