package core;

import java.io.IOException;
import java.net.ServerSocket;

import util.CloseUtil;

public class Server {

	private ServerSocket server;
	public static final String CRLF = "\r\n";
	public static final String BLANK = " ";
	
	private boolean isShutDown = false;
	
	public void start(){
		try {
			server = new ServerSocket(8888);
			this.receive();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void receive(){
		try {
			while(!isShutDown){
				new Thread(new Dispatcher(server.accept())).start();
			}
		} catch (IOException e) {
			stop();
		}
	}
	
	public void stop() {
		isShutDown = true;
		CloseUtil.closeSocket(server);
	}

	public static void main(String[] args) {
		Server server = new Server();
		server.start();
	}
}
