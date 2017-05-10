package Tool.walker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SelectNodeInfo {
	public static List<node> findByElement(node n,String ele){
		node tar=n;
		List<node> list=new ArrayList<node>();
		if(tar!=null){
			if(tar.getChildNode()==null){
				return null;
			}else{
				Map<String, node> map=n.getChildNode();
				Iterator<String> it=map.keySet().iterator();
				while(it.hasNext()){
					String key=it.next();
					if(map.get(key).getElement().contains(ele)){
						list.add(map.get(key));
//						System.out.println(key+":"+map.get(key).getInnerHTML());
					}
					appendList(list, findByElement(map.get(key), ele));
				}
				return list;
			}
			
		}else{
			return null;
		}
		
	}
	
	public static List<node> findByClass(node n,String cls){
		node tar=n;
		List<node> list=new ArrayList<node>();
		if(tar!=null){
			if(tar.getClasses()==null){
				return null;
			}else{
				if(tar.getChildNode()==null){
					return null;
				}else{
					Map<String, node> map=n.getChildNode();
					Iterator<String> it=map.keySet().iterator();
					while(it.hasNext()){
						String key=it.next();
						String[] clss={};
						if(map.get(key).getClasses()!=null){
							clss=map.get(key).getClasses();
						}
						for(String c:clss){
							if(c==cls){
								list.add(tar);
							}
						}
						list.addAll(findByElement(map.get(key), cls));
					}
					return list;
				}
			}
		}else{
			return null;
		}
	}
	public static List<String> getAttr(node n,String attr){
		node tar=n;
		List<String> list=new ArrayList<>();
		if(tar==null){
			return null;
		}else{
			Map<String, String[]> map = tar.getAttr();
			Iterator<String> it=map.keySet().iterator();
			while(it.hasNext()){
				String key=it.next();
				if(key==attr){
					for(String s:map.get(key)){
						list.add(s);
					}
					break;
				}
			}
			return list;
		}
	}
	
	private static <T> List<T> appendList(List<T> tar,List<T> ele){
		for(T e:ele){
			tar.add(e);
		}
		return tar;
	}
}
