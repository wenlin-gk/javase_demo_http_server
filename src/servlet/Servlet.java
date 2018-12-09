package servlet;

import core.Request;
import core.Response;

public abstract class Servlet {
	public void service(Request req, Response resp){
		doGet(req, resp);
		doPost(req, resp);
	}
	
	public abstract void doGet(Request req, Response resp);
	public abstract void doPost(Request req, Response resp);
}
