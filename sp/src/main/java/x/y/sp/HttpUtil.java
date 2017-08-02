package x.y.sp;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.IOUtils;

public class HttpUtil {
	
	public String getRequest(String url,int timeOut) throws Exception{
		URL u = new URL(url);
		if("https".equalsIgnoreCase(u.getProtocol())){
			SSLClient.ignoreSsl();
		}
		String cookie = "PHPSESSID=j8f60v0tbai45h7chomjnolse4; teacherUsername=tairo; rk-lab-aware-cookie=4879964+1501664901%3B; __utmt=1; __utma=123938188.519210344.1501565901.1501577944.1501664873.3; __utmb=123938188.7.10.1501664873; __utmc=123938188; __utmz=123938188.1501664873.3.2.utmcsr=raz-kids.com|utmccn=(referral)|utmcmd=referral|utmcct=/; __utmt_b=1; __utma=61885683.530806340.1501565901.1501577944.1501664873.3; __utmb=61885683.7.10.1501664873; __utmc=61885683; __utmz=61885683.1501664873.3.2.utmcsr=raz-kids.com|utmccn=(referral)|utmcmd=referral|utmcct=/; BIGipServer~LAZ_Prod~laz_prod_kidsa-z=1375788810.6699.0000";
		URLConnection conn = u.openConnection();
		conn.setConnectTimeout(timeOut);
		conn.setReadTimeout(timeOut);
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
	
}
