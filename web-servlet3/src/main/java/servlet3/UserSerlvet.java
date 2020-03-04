package servlet3;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author tianshuo
 */
@WebServlet(urlPatterns = "/user")
public class UserSerlvet extends HttpServlet {
	private static final long serialVersionUID = 9078228534910617681L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletOutputStream outputStream = resp.getOutputStream();
		outputStream.write("aaa".getBytes());
	}
}
