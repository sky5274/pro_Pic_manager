package Tool.walker;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.dao.entity.Picture;


public class AutoWalkerPicture implements Runnable{
	private Logger log=Logger.getLogger("logfile");
	private String context;
	private String clazz;
	private String theme;
	private String path;
	private String base;
	private final String tar_rule="page-main";
	private final String tar="cell first-cell";
	private AutoSavePicture savelistener;

	public AutoWalkerPicture(AutoSavePicture saveListener, String clazz, String theme, String path, String base) {
		this.savelistener=saveListener;
		this.clazz=clazz;
		this.theme=theme;
		this.path=path;
		this.base=base;
		context=getContext(path);
	}

	@Override
	public void run() {

		Map<String, String> rule = getRule();
		if(rule!=null){
			String header=rule.get("header");
			String action=rule.get("action");
			if(context==null){
				log.log(Level.WARNING, "info_position:  the walker can not get the html infomation.", context);
			}else{
				log.info("info_position:  now is walkering  the picture; class:"+clazz+"\ntheme:"+theme+"\npath:"+path);
				initPictureWalker();
				delay(200);
			}
			for(int i=2;i<=5;i++){
				String newpath=base+header+i+action;
				setContext(getContext(newpath));
				if(context==null){
					log.log(Level.WARNING, "info_position:  the walker can not get the html infomation.", context);
					continue;
				}else{
					log.info("info_position:  now is walkering  the picture; class:"+clazz+"\ntheme:"+theme+"\npath:"+newpath);
					initPictureWalker();
					System.out.println("the page "+newpath+" has scanned!");
				}
				delay(200);
			}
		}
	}


	public String getContext(String path) {
		return toolWalker.readAddr(path);
	}

	public void setContext(String context) {
		this.context = context;
	}
	
	public void delay(int timer){
		try {
			Thread.sleep(timer);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Map<String, String> getRule(){
		Map<String, String>rule = null;
		if(context==null){
			log.log(Level.WARNING, "info_position:  the walker can not get the html infomation.", context);
		}else{
			rule=toolWalker.getRule(context, tar_rule, ".aspx");
		}
		//		System.out.println("the rule is:"+rule.get("header")+rule.get("action"));
		return rule;
	}

	public void initPictureWalker(){
		List<Picture> list=toolWalker.walkerPicture(context,tar);   //网络爬虫
		log.info("the walker has get the pic :"+list.size());
		for(Picture p:list){
			//				System.out.println(p.toString());
			p.setClazz(clazz);
			p.setTheme(theme);
			savelistener.addTask(p);
		}
//		savelistener.save();
	}

}
