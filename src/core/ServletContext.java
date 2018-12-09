package core;

import java.util.HashMap;
import java.util.Map;

public class ServletContext {
	private Map<String, String> servletMap;
	private Map<String, String> mappingMap;
	public ServletContext() {
		super();
		this.servletMap = new HashMap<String, String>();
		this.mappingMap = new HashMap<String, String>();
	}
	public Map<String, String> getServletMap() {
		return servletMap;
	}
	public void setServletMap(Map<String, String> servletMap) {
		this.servletMap = servletMap;
	}
	public Map<String, String> getMappingMap() {
		return mappingMap;
	}
	public void setMappingMap(Map<String, String> mappingMap) {
		this.mappingMap = mappingMap;
	}
}
