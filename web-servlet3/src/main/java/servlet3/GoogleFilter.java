package servlet3;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author tianshuo
 */
public class GoogleFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("hello google Servlet3.0");
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}
}
