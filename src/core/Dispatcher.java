package core;

import java.io.IOException;
import java.net.Socket;

import servlet.Servlet;
import util.CloseUtil;

public class Dispatcher implements Runnable{
	private Socket client;
	private Request req;
	private Response resp;
	private int code = 200;
	
	

	public Dispatcher(Socket client) {
		this.client = client;
		try {
			req = new Request(client.getInputStream());
			resp = new Response(client.getOutputStream());
		} catch (IOException e) {
			code = 500; 
			return;
		}
	}

	@Override
	public void run() {
		try {
			Servlet serv = WebApp.getServlet(req.getUrl());
			if(null==serv){
				this.code = 404;
			}else{
				serv.service(req, resp);
			}
			resp.pushToClient(code);
		} catch (Exception e) {
			try {
				resp.pushToClient(500);
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
		req.close();
		resp.close();
		CloseUtil.closeSocket(client);
	}
	
}
