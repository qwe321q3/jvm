package servlet3;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author tianshuo
 */
@WebFilter(urlPatterns = "/*")
public class SecurityFilter  implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("hello Servlet3.0");
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}
}
