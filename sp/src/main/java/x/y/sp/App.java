package x.y.sp;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{

	
	public static void main(String[] args) {
		ConstsUtil.read();
		HttpUtil http = new HttpUtil();
		JsonUtil jsonUtil = new JsonUtil();
		
		try {
		String html = http.getRequest("https://www.kidsa-z.com/main/ReadingBookRoom/collectionId/1/level/aa", 5000);
		List<String> artList = new JsonUtil().parsePageList(html);
		for(String artUrl : artList){
			String fileName = artUrl.split("id/")[1] + ".html";
			String artHtml = http.getRequest(artUrl, 5000);
			jsonUtil.parsePage(artHtml,fileName);
			Thread.sleep(NumberUtil.randomNum());
		}

		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ConstsUtil.write();
	}
    
    
}
