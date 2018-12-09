package core;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import bean.Entity;
import bean.Mapping;

public class WebHandler extends DefaultHandler{
	private List<Entity> entityList;
	private List<Mapping> mappingList;
	private Entity entityTemp;
	private Mapping mappingTemp;
	private String tagName;
	private boolean isMapping;
	
	@Override
	public void startDocument() throws SAXException {
		entityList = new ArrayList<Entity>();
		mappingList = new ArrayList<Mapping>();
	}
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if(null != qName){
			tagName = qName;
			if(qName.equals("servlet")){
				isMapping = false;
				entityTemp = new Entity();
			}else if(qName.equals("servlet-mapping")){
				isMapping = true;
				mappingTemp = new Mapping();
			}
		}
	}
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if(null != tagName){
			String str = new String(ch, start, length);
			if(!isMapping){
				if(tagName.equals("servlet-name")){
					entityTemp.setName(str);
				}else if(tagName.equals("servlet-class")){
					entityTemp.setClz(str);
				}
			}else{
				if(tagName.equals("servlet-name")){
					mappingTemp.setName(str);
				}else if(tagName.equals("url-pattern")){
					mappingTemp.getUrlPattern().add(str);
				}
			}
		}
	}
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		tagName = null;
		if(null != qName){
			if(qName.equals("servlet")){
				this.entityList.add(entityTemp);
			}else if(qName.equals("servlet-mapping")){
				this.mappingList.add(mappingTemp);
			}
		}
	}
	public List<Entity> getEntityList() {
		return entityList;
	}
	public void setEntityList(List<Entity> entityList) {
		this.entityList = entityList;
	}
	public List<Mapping> getMappingList() {
		return mappingList;
	}
	public void setMappingList(List<Mapping> mappingList) {
		this.mappingList = mappingList;
	}
	
}
