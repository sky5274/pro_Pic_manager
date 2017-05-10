package Tool.walker;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dao.entity.Picture;
import com.dao.impl.PictureMapper;


public class AutoSavePicture implements Runnable{
	private Logger log=Logger.getLogger("logfile");
	private ApplicationContext con;
	private static List<Picture> list;
	private  PictureMapper ser;
	private boolean isSaveState;
	private boolean isSaveOpen;
	public AutoSavePicture(){
		super();
		list=new ArrayList<Picture>();
		System.out.println("picture save listener is inited");
		isSaveState=true;
		isSaveOpen=false;
		con=new ClassPathXmlApplicationContext("classpath:config/ApplicaiotnContext.xml");
		ser=(PictureMapper) con.getBean(PictureMapper.class);
	}

	@Override
	public void run() {
		while(isSaveState){
			if(list.size()>0){
				if(isSaveOpen){
					log.info("the saver has get resource, the size:"+list.size());
					synchronized (this) {
						//log.info(list.get(0).toString());
						try {
							ser.insertSelective(list.get(0));
						} catch (Exception e) {
							System.out.println("未能存入图片信息");
							e.printStackTrace();
						}
						
						list.remove(0);
					}
				}else{
					try {
						System.out.print(3);
						Thread.sleep(50);
						if(list.size()>0){
							openSave();
							continue;
						}else{
							pushSave();
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}else{
				log.info("now is sleep, the  resource size:"+list.size());
				try {
					Thread.sleep(500);
					if(list.size()==0){
						closeSave();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public void addTask(Picture p) {
		synchronized (this) {
			list.add(p);
			log.log(Level.INFO, "listener get a picture");
		}
//		openSave();
	}

	public void closeSave(){
		isSaveState=false;
		System.out.println("all pic is save");
	}
	public void pushSave() {
		isSaveOpen=false;
		System.out.println("save thread is pushed");
	}

	public void openSave() {
		isSaveOpen=true;
		System.out.println("save thread is opened");
	}

	public void save() {
		for(Picture p:list){
			ser.insertSelective(p);
			list.remove(p);
		}
		
	}

}
