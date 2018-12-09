package core;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import util.CloseUtil;

public class Request {
	
	private String method;
	private String url;
	private Map<String, List<String>> paraMapValues;
	private String requestInfo;

	private static final String CRLF = "\r\n";
	private InputStream is;
	
	public Request(){
		method = "";
		url = "";
		paraMapValues = new HashMap<String, List<String>>();
		requestInfo = "";
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Request(InputStream is){
		this();
		this.is = is;
		
		try {
			byte[] data = new byte[20480];
			int len = is.read(data);
			requestInfo = new String(data, 0, len);
		} catch (IOException e) {
			e.printStackTrace();
			return ;
		}
		
		parseRequestInfo();
	}
	
	private void parseRequestInfo() {
		if( null == requestInfo || requestInfo.trim().equals("")){
			return;
		}
		
		String firstLine = requestInfo.substring(0, requestInfo.indexOf(CRLF));
		int index = requestInfo.indexOf("/");
		this.method = firstLine.substring(0, index).trim();
		String urlStr = firstLine.substring(index, firstLine.indexOf("HTTP/")).trim();
		
		String paraStr = "";
		if(this.method.equalsIgnoreCase("post")){
			this.url = urlStr;
			paraStr = requestInfo.substring(requestInfo.lastIndexOf(CRLF)).trim();
		}else if(this.method.equalsIgnoreCase("get")){
			if(urlStr.contains("?")){
				String[] urlArr = urlStr.split("\\?");
				this.url = urlArr[0];
				paraStr = urlArr[1];
			}else{
				this.url = urlStr;
			}
		}
		
		if(paraStr.equals(""))
			return;
		else
			parseParams(paraStr);
	}

	private void parseParams(String paraStr) {
		StringTokenizer token = new StringTokenizer(paraStr, "&");
		while(token.hasMoreTokens()){
			String keyValue =token.nextToken();
			String[] keyValues=keyValue.split("=");
			if(keyValues.length==1){
				keyValues =Arrays.copyOf(keyValues, 2);
				keyValues[1] =null;
			}
			
			String key = keyValues[0].trim();
			String value = null==keyValues[1]?null:decode(keyValues[1].trim(),"gbk");
			//转换成Map 分拣
			if(!paraMapValues.containsKey(key)){
				paraMapValues.put(key,new ArrayList<String>());
			}
			
			List<String> values =paraMapValues.get(key);
			values.add(value);			
		}		
	}

	private String decode(String value, String code) {
		try {
			return java.net.URLDecoder.decode(value, code);
		} catch (UnsupportedEncodingException e) {
			//e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据页面的name 获取对应的多个值
	 * @param args
	 */
	public String[] getparaMapValues(String name){
		List<String> values=null;
		if((values=paraMapValues.get(name))==null){
			return null;
		}else{
			return values.toArray(new String[0]);
		}
	}
	/**
	 * 根据页面的name 获取对应的单个值
	 * @param args
	 */
	public String getParameter(String name){
		String[] values =getparaMapValues(name);
		if(null==values){
			return null;
		}
		return values[0];
	}

	@Override
	public String toString() {
		return "Request [method=" + method + ", url=" + url
				+ ", paraMapValues=" + paraMapValues + ", requestInfo="
				+ requestInfo + ", is=" + is + "]";
	}
	
	public void close(){
		CloseUtil.closeIO(is);
	}
}
