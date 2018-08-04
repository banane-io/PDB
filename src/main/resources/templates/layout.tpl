html {
    head {
        title(title)
        link(rel: 'stylesheet', href: '/webjars/bootstrap/3.3.7/css/bootstrap.min.css')
    }
    body {
        div(class: 'container') {
            include template: 'navbar.tpl'
            h1(title)
            div { content() }
        }
        script(type:"text/javascript", src:"/webjars/bootstrap/3.3.7/js/bootstrap.min.js")
    }
}