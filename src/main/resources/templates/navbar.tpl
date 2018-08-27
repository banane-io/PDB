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
                if (__user == null) {
                    li {
                        a (href:"/registration") {
                            yield 'Register'
                        }
                    }
                }
                if (__user == null) {
                    li {
                        a(href:"/login") {
                            yield 'Login'
                        }
                    }
                }
                if (__user) {
                    li {
                        a (href:"/logout") {
                            yield 'Sign Out'
                        }
                    }
                }
                if (__user) {
                    li  {
                        a(href:"/player/creation") {
                            yield 'Player creation'
                        }
                    }
                }
            }
        }
    }
}