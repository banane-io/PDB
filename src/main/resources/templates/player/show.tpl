layout 'layout.tpl', true, title: 'Show Player',
        content: contents {
            div( class: "row") {
                div ( classs: "col-md-6 col-md-offset-3") {
                    h1 ('Your character')
                    div {
                        yield Name: + player.name
                    }
                }
            }
                    <div class="col-md-6 col-md-offset-3">
                        <h1>Your character</h1>
                        <div>
                            Name : <span th:text="${name}"></span>
                        </div>
                    </div>
                </div>
        }