package x.y.sp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class HttpUtil {
	
	public String getRequest(String url,int timeOut) throws Exception{
		URL u = new URL(url);
		if("https".equalsIgnoreCase(u.getProtocol())){
			SSLClient.ignoreSsl();
		}
		String cookie = FileUtils.readLines(new File("src/main/java/cookie.txt"),"utf-8").get(0);
		URLConnection conn = u.openConnection();
		conn.setConnectTimeout(59000);
		conn.setReadTimeout(59000);
        conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");  
        conn.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch, br");
        conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.6,en;q=0.4"); 
        conn.setRequestProperty("Cache-Control", "max-age=0"); 
		conn.setRequestProperty("Cookie", cookie);
		conn.setRequestProperty("Connection", "keep-alive");
		conn.setRequestProperty("Host", "www.kidsa-z.com");
		conn.setRequestProperty("Referer", "https://www.kidsa-z.com/main/ReadingBookRoom");
		conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
		
		InputStream ism = null;
		ism = conn.getInputStream();
		ism = new GZIPInputStream(conn.getInputStream());
		return IOUtils.toString(ism);
	}

	public String postRequest(String urlAddress,String args,int timeOut) throws Exception{
		URL url = new URL(urlAddress);
		if("https".equalsIgnoreCase(url.getProtocol())){
			SSLClient.ignoreSsl();
		}
		URLConnection u = url.openConnection();
		u.setDoInput(true);
		u.setDoOutput(true);
		u.setConnectTimeout(timeOut);
		u.setReadTimeout(timeOut);
		OutputStreamWriter osw = new OutputStreamWriter(u.getOutputStream(), "UTF-8");
		osw.write(args);
		osw.flush();
		osw.close();
		u.getOutputStream();
		return IOUtils.toString(u.getInputStream());
	}
	
	public void download (String url, String filePath) throws Exception{
		URL u = new URL(url);
		if("https".equalsIgnoreCase(u.getProtocol())){
			SSLClient.ignoreSsl();
		}
		String cookie = "PHPSESSID=lf95c5bh889u41mabqo1nt95q3; __utmt=1; __utmt_b=1; teacherUsername=tairo; rk-lab-aware-cookie=4879964+1501743843%3B; BIGipServer~LAZ_Prod~laz_prod_kidsa-z=1694555914.6699.0000; __utma=123938188.519210344.1501565901.1501727362.1501743832.5; __utmb=123938188.6.10.1501743832; __utmc=123938188; __utmz=123938188.1501727362.4.3.utmcsr=raz-kids.com|utmccn=(referral)|utmcmd=referral|utmcct=/; __utma=61885683.530806340.1501565901.1501727362.1501743832.5; __utmb=61885683.6.10.1501743832; __utmc=61885683; __utmz=61885683.1501727362.4.3.utmcsr=raz-kids.com|utmccn=(referral)|utmcmd=referral|utmcct=/";
		URLConnection conn = u.openConnection();
		conn.setConnectTimeout(59000);
		conn.setReadTimeout(59000);
        conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");  
        conn.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch, br");
        conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.6,en;q=0.4"); 
        conn.setRequestProperty("Cache-Control", "max-age=0"); 
		conn.setRequestProperty("Cookie", cookie);
		conn.setRequestProperty("Connection", "keep-alive");
		conn.setRequestProperty("Host", "www.kidsa-z.com");
		conn.setRequestProperty("Referer", "https://www.kidsa-z.com/main/ReadingBookRoom");
		conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
		
		InputStream ism = null;
		ism = conn.getInputStream();
//		ism = new GZIPInputStream(conn.getInputStream());
		writeToLocal(filePath, ism);
	}
	
	private static void writeToLocal(String destination, InputStream input)  
	        throws IOException {  
	    int index;  
	    byte[] bytes = new byte[1024];
	    File f = new File(destination);
		try {
			if(!f.getParentFile().exists()){
				f.getParentFile().mkdirs();//创建父目录
			}
			if (!f.exists()) {
				f.createNewFile();//创建文件
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	    FileOutputStream downloadFile = new FileOutputStream(destination);
	    while ((index = input.read(bytes)) != -1) {  
	        downloadFile.write(bytes, 0, index);  
	        downloadFile.flush();  
	    }  
	    downloadFile.close();  
	    input.close();  
	}  
	
}
