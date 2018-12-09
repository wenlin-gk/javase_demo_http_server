package servlet;

import core.Request;
import core.Response;

public class LoginServlet extends Servlet{

	public boolean login(String name,String pwd){
		if(null==name || null==pwd){
			return false;
		}
		return name.equals("wenlin") && pwd.equals("qwe123");
	}
	
	@Override
	public void doGet(Request req, Response resp) {
		String name = req.getParameter("uname");
		String pwd = req.getParameter("pwd");
		if(login(name, pwd)){
			resp.insertContextLn("login successed.");
		}else{
			resp.insertContextLn("login failed.");
		}
	}

	@Override
	public void doPost(Request req, Response resp) {
		
	}

}
