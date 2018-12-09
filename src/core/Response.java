package core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;

import util.CloseUtil;

public class Response {

	private static final String CRLF = "\r\n";
	private static final String BLANK = " ";
	
	private int len = 0;
	private StringBuilder headInfo;
	private StringBuilder content;
	private BufferedWriter bw;
	
	public Response(){
		headInfo = new StringBuilder();
		content = new StringBuilder();
		len = 0;
	}
	
	public Response(OutputStream os){
		this();
		bw = new BufferedWriter(new OutputStreamWriter(os));
	}
	
	private void createHeadInfo(int code){
		String codeDesc = null;
		switch(code){
		case 200:
			codeDesc = "OK";
		case 404:
			codeDesc = "NOT FOUND";
		case 500:
			codeDesc = "SERVER ERROR";
		}
		headInfo.append("HTTP/1.1").append(BLANK).append(code).append(BLANK).append(codeDesc).append(CRLF);
		headInfo.append("Server:wenlin Server/0.0.1").append(CRLF);
		headInfo.append("Data:").append(new Date()).append(CRLF);
		headInfo.append("content-type:text/html;charset=GBK").append(CRLF);
		
		headInfo.append("Content-Length:").append(len).append(CRLF);
		
		headInfo.append(CRLF);
	}
	
	public Response insertContext(String info){
		content.append(info);
		len += info.getBytes().length;
		return this;
	}

	public Response insertContextLn(String info){
		content.append(info).append(CRLF);
		len += info.getBytes().length;
		return this;
	}
	
	public void pushToClient(int code) throws IOException{
		if(null == headInfo){
			code = 500;
		}
		createHeadInfo(code);
		bw.append(headInfo.toString());
		bw.append(content.toString());
		bw.flush();
		
		System.out.println(headInfo.toString()+content.toString());
	}
	
	public void close(){
		CloseUtil.closeIO(bw);
	}
	
}
