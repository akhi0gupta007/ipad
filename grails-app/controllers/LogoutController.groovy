import javax.servlet.http.Cookie;

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.springframework.web.servlet.support.RequestContextUtils;

class LogoutController {

	def springSecurityService
	/**
	 * Index action. Redirects to the Spring security logout uri.
	 */
	def index = {
		// TODO put any pre-logout code here
		redirect uri: SpringSecurityUtils.securityConfig.logout.filterProcessesUrl // '/j_spring_security_logout'
	}
}