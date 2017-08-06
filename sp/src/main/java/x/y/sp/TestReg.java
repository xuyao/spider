package x.y.sp;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class TestReg {

	public static void main(String[] args) throws Exception{
	        String path = "D:\\eglish\\D\\"; // 路径
	        File f = new File(path);
	        if (!f.exists()) {
	            System.out.println(path + " not exists");
	            return;
	        }

	        File fa[] = f.listFiles();
	        for (int i = 0; i < fa.length; i++) {
	            File fs = fa[i];
	            if (!fs.isDirectory() && fs.getName().contains(".html")) {
	            	System.out.println(fs.getName());
	            	
	    			String s = FileUtils.readFileToString(new File(path+fs.getName()),"utf-8");
	    			s = s.replaceAll("\"animation_content\"\\:\\{[\\s\\S]*?\\}", "\"animation_content\":false");
	    			FileUtils.writeStringToFile(new File(path+fs.getName()), s, "utf-8");
	            }
	        }
	        
		}		
}
