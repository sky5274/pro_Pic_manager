package Tool.walker;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


public class WalkerPicture {
	

	public static void main(String[] args){
		 Logger log=Logger.getLogger("logfile");
		String path="http://www.tooopen.com/img/87.aspx";
		String basePath="http://www.tooopen.com";
		String context=toolWalker.readAddr(path);
		System.out.println(context);
		List<node> n=toolWalker.analysByClass(context, "cell type-list first-cell",0);
		Map<String, Map<String, String>> menumap=toolWalker.getMenu(n.get(0));
		log.info("now start to get the web is infomation  !!!");
		AutogetMenu menuTHread=new AutogetMenu(menumap,basePath);
		Thread walkerMune=new Thread(menuTHread);
		walkerMune.start();
		
		System.out.println("info:  the program is end");
		
		
//		AutoSavePicture saveListener=new AutoSavePicture();
//		String clazz="职场人物";
//		String theme=clazz;
//		String path="http://www.tooopen.com/img/239.aspx";
//		saveListener.openSave();
//		new Thread(saveListener).start();
//		AutoWalkerPicture pictureWalker=new AutoWalkerPicture(saveListener,clazz,theme,path);
//		Thread thread=new Thread(pictureWalker);
//		thread.start();
		
	}
	
}
