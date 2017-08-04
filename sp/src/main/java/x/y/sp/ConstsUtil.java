package x.y.sp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConstsUtil {
	public static Map<String,String> mp3Map = new HashMap<String,String>();
	
	public static final Properties prop = new Properties();
	static{
		InputStream is = ConstsUtil.class.getClassLoader().getResourceAsStream("config.properties");
		try {
			prop.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getValue(String key){
		return prop.getProperty(key);
	}
	
	public static void write(){
		FileOutputStream outstream;
		try {
			outstream = new FileOutputStream("library.dat");
	        ObjectOutputStream out = new ObjectOutputStream(outstream);
	        out.writeObject(mp3Map); //将这个对象写入流
	        out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void read(){
        ObjectInputStream in;
        
		try {
			File file = new File("library.dat");
			if(!file.exists()){
				
			}else{
				in = new ObjectInputStream(new FileInputStream("library.dat"));
		        mp3Map = (Map<String,String>)in.readObject();
		        in.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
