package Tool.walker;

import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AutogetMenu implements Runnable{
	private Map<String, Map<String, String>> menu;
	private String base;
	private AutoSavePicture saveListener;
	private Logger log=Logger.getLogger("logfile");

	public AutogetMenu(Map<String, Map<String, String>> menumap, String basePath) {
		this.menu=menumap;
		this.base=basePath;
	}

	@Override
	public void run() {
		System.out.println("info: init walker the menu");
		if(menu==null|| base==null){
			try {
				System.out.println("info: init is failed ,can not get menu");
				throw new Exception("can not find menu or basePath");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			System.out.println("info: init to open the menu.");
			saveListener=new AutoSavePicture();
			
			Iterator<String> it = menu.keySet().iterator();
			while(it.hasNext()){
				log.log(Level.INFO, "info_position:  the walker is open the menu.", menu);
				String clazz=it.next();
				Map<String, String> itemMap = menu.get(clazz);
				Iterator<String> itemIt = itemMap.keySet().iterator();
				while(itemIt.hasNext()){
					String theme=itemIt.next();
					log.log(Level.INFO, "info_position:  the walker is open the menuItem.", itemMap.get(theme));
					String path=base+itemMap.get(theme);
					
//					System.out.println("class:"+clazz+"\ntheme:"+theme+"\npath:"+path);
					AutoWalkerPicture pictureWalker=new AutoWalkerPicture(saveListener,clazz,theme,path,base);
					Thread thread=new Thread(pictureWalker);
					thread.setPriority(Thread.MIN_PRIORITY);
					thread.start();
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
//				log.log(Level.INFO, "info_position:  the walker is changing the menuItem.", menu);
			}

		}
		saveListener.openSave();
		Thread saveThread=new Thread(saveListener);
		
		saveThread.setPriority(Thread.MAX_PRIORITY);
		saveThread.start();
		
		System.out.println("info: walker is close");
	}

}
