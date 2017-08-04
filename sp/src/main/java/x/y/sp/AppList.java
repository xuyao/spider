package x.y.sp;

import java.util.List;

public class AppList {
	
	public static void main(String[] args) {
		HttpUtil http = new HttpUtil();
		
		try {
			String listUrl = "https://www.kidsa-z.com/main/ReadingBookRoom/collectionId/1/level/aa";
			String html;
			html = http.getRequest(listUrl, 5000);
			List<String> artList = new JsonUtil().parsePageList(html);
			for(String s : artList){
				System.out.println(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
    
}
