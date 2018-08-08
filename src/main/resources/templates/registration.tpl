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
                    div (class: 'form-group') {
                       label (for:'user.username', 'Username')
                       input (name:'user.username', type:'text', value:'',
                               class:'form-control', autofocus:'autofocus',
                               placeholder: 'Username')
                    }

                    div (class: 'form-group') {
                        label (for:'user.password', 'Password')
                        input (name:'user.password', type:'password', class:'form-control', placeholder: 'Password')
                    }

                    div (class: 'form-group') {
                        label (for:'user.passwordConfirm', 'Confirm password')
                        input (name:'user.passwordConfirm', type:'password', class:'form-control', placeholder: 'Confrim password')
                    }

                    div (class: 'form-group') {
                        button(type:'submit', class:'btn btn-success', 'Register')
                    }

                    span {
                        yield 'Already registered?' +  a(href:"/login", 'Login here')
                    }

                }
            }
        }

/*<div class="row">
          <div class="col-md-6 col-md-offset-3">

              <div th:if="${param.success}">
                  <div class="alert alert-info">
                      You've successfully registered to our awesome app!
                  </div>
              </div>

              <h1>Registration</h1>
              <form th:action="@{/registration}" th:object="${user}" method="post">

                  <p class="error-message"
                     th:if="${#fields.hasGlobalErrors()}"
                     th:each="error : ${#fields.errors('global')}"
                     th:text="${error}">Validation error</p>

                  <div class="form-group"
                       th:classappend="${#fields.hasErrors('username')}? 'has-error':''">
                      <label for="username" class="control-label">Username</label>
                      <input id="username"
                             class="form-control"
                             th:field="*{username}"/>
                      <p class="error-message"
                         th:each="error: ${#fields.errors('username')}"
                         th:text="${error}">Validation error</p>
                  </div>
                  <div class="form-group"
                       th:classappend="${#fields.hasErrors('password')}? 'has-error':''">
                      <label for="password" class="control-label">Password</label>
                      <input id="password"
                             class="form-control"
                             type="password"
                             th:field="*{password}"/>
                      <p class="error-message"
                         th:each="error : ${#fields.errors('password')}"
                         th:text="${error}">Validation error</p>
                  </div>
                  <div class="form-group"
                       th:classappend="${#fields.hasErrors('passwordConfirm')}? 'has-error':''">
                      <label for="passwordConfirm" class="control-label">Confirm password</label>
                      <input id="passwordConfirm"
                             class="form-control"
                             type="password"
                             th:field="*{passwordConfirm}"/>
                      <p class="error-message"
                         th:each="error : ${#fields.errors('passwordConfirm')}"
                         th:text="${error}">Validation error</p>
                  </div>
                  <div class="form-group">
                      <button type="submit" class="btn btn-success">Register</button>
                      <span>Already registered? <a href="/" th:href="@{/login}">Login here</a></span>
                  </div>

              </form>
          </div>
      </div>*/