package servlet;

import core.Request;
import core.Response;

public class RegisterServlet extends Servlet{

	@Override
	public void doGet(Request req, Response resp) {
		
	}

	@Override
	public void doPost(Request req, Response resp) {
		resp.insertContextLn("<html><head><title>http response");
		resp.insertContextLn("</title></head></html><body>" +
				"hello: " + req.getParameter("uname") +
				"</body>");		
	}

}
