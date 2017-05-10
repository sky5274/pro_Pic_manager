package Tool.walker;

import java.util.HashMap;
import java.util.Map;

public class node {
	 private String element;
	 private String[] classes;
	 private String id;
	 private Map<String, String[]> attr;
	 private Map<String, node> childNode;
	 private String innerHTML;
	 private String context;
	 public node(){}
	public node(String element, String[] classes, String id,  String innerHTML) {
		super();
		this.element = element;
		this.classes = classes;
		this.id = id;
		this.innerHTML = innerHTML;
	}
	public String getElement() {
		return element;
	}
	public void setElement(String element) {
		this.element = element;
	}
	public String[] getClasses() {
		return classes;
	}
	public void setClasses(String[] classes) {
		this.classes = classes;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInnerHTML() {
		return innerHTML==null?"":innerHTML;
	}
	public void setInnerHTML(String innerHTML) {
		this.innerHTML = innerHTML;
	}
	public String getContext() {
		return context==null?"":context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public Map<String, node> getChildNode() {
		return childNode==null?new HashMap<String, node>():childNode;
	}
	public void setChildNode(Map<String, node> childNode) {
		this.childNode = childNode;
	}
	public Map<String, String[]> getAttr() {
		return attr==null?new HashMap<String, String[]>():attr;
	}
	public void setAttr(Map<String, String[]> attr2) {
		this.attr = attr2;
	}
	 
}
