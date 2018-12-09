package core;

import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import bean.Entity;
import bean.Mapping;

import servlet.Servlet;

public class WebApp {
	private static ServletContext context;
	static{
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parse = factory.newSAXParser();
			WebHandler handler = new WebHandler();
			parse.parse(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("WEB_INFO/web.xml"), handler);
			
			context = new ServletContext();
			Map<String, String> mappingMap = context.getMappingMap();
			for(Mapping mapping: handler.getMappingList()){
				List<String> urls = mapping.getUrlPattern();
				for(String url: urls){
					mappingMap.put(url, mapping.getName());
				}
			}
			Map<String, String> servletMap = context.getServletMap();
			for(Entity entity: handler.getEntityList()){
				servletMap.put(entity.getName(), entity.getClz());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Servlet getServlet(String url) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		if( null==url || url.trim().equals("")){
			return null;
		}
		
		String servletName = context.getServletMap().get(context.getMappingMap().get(url));
		if(null == servletName){
			return null;
		}
		return (Servlet) Class.forName(servletName).newInstance();
	}
}
