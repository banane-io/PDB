package banane.io.pdb.security

import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomAccessDeniedHandler : AccessDeniedHandler {
    @Throws(IOException::class, ServletException::class)
    override fun handle(request: HttpServletRequest, response: HttpServletResponse, ex: AccessDeniedException) {
        response.outputStream.print("Error Message Goes Here")
        response.status = 403
    }
}