package util;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CloseUtil {
	public static <T extends Closeable> void closeIO(T... ios) {
		for (Closeable temp : ios) {
			try {
				if (null != temp) {
					temp.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void closeSocket(Socket socket) {
		try {
			if (null != socket) {
				socket.close();
			}
		} catch (Exception e) {
		}
	}

	public static  void closeSocket(ServerSocket socket){
		try {
			if (null != socket) {
				socket.close();
			}
		} catch (Exception e) {
		}
	}
}
