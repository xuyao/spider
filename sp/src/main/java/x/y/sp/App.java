package x.y.sp;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class App {
	
	public static void main(String[] args) {
		ConstsUtil.read();
		HttpUtil http = new HttpUtil();
		JsonUtil jsonUtil = new JsonUtil();
//		try {
//			List<String> list = FileUtils.readLines(new File("src/main/java/list.txt"), "utf-8");
//			for(String s:list){
//				String html = http.getRequest(s, 5000);
//				List<String> artList = new JsonUtil().parsePageList(html);
//				for(String artUrl : artList){
//					String fileName = artUrl.split("id/")[1] + ".html";
//					String artHtml = http.getRequest(artUrl, 5000);
//					jsonUtil.parsePage(artHtml,fileName);
//					Thread.sleep(NumberUtil.randomNum());
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		///////////////////////////////////////////////////////////////////////////////////////////
		try {
//			String listUrl = "https://www.kidsa-z.com/main/ReadingBookRoom/collectionId/1/level/aa";
//			String html;
//			html = http.getRequest(listUrl, 5000);
//			List<String> artList = new JsonUtil().parsePageList(html);
			List<String> artList = FileUtils.readLines(new File("src/main/java/artList.txt"), "utf-8");
			for(String s : artList){
				System.out.println(s);
				String fileName = s.split("id/")[1] + ".html";
				String artHtml = http.getRequest(s, 5000);
				jsonUtil.parsePage(artHtml,fileName);
				Thread.sleep(NumberUtil.randomNum());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		ConstsUtil.write();
	}
    
}
