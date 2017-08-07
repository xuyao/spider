package x.y.sp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

public class MapMerge {
	public static void main(){
		MapMerge mm = new MapMerge();
	    Map<String,String> mp3Map1 = mm.read("");
	    Map<String,String> mp3Map2 = mm.read("");
	    mp3Map1.putAll(mp3Map2);
	    
	    write(mp3Map1, "");
	}
	
	public static void write(Map<String,String> mp3Map, String path){
		FileOutputStream outstream;
		try {
			outstream = new FileOutputStream(path);
	        ObjectOutputStream out = new ObjectOutputStream(outstream);
	        out.writeObject(mp3Map); //将这个对象写入流
	        out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Map<String,String> read(String path){
        ObjectInputStream in;
        Map<String,String> mp3Map = null;
		try {
			File file = new File(path);
			if(!file.exists()){
				
			}else{
				in = new ObjectInputStream(new FileInputStream(path));
		        mp3Map = (Map<String,String>)in.readObject();
		        in.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			return mp3Map;
		}
	}
}
