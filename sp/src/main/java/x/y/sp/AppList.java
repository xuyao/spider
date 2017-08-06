package x.y.sp;

import java.util.List;

public class AppList {
	
	public static void main(String[] args) {
		HttpUtil http = new HttpUtil();
		
		try {
			String listUrl = "https://www.kidsa-z.com/main/ReadingBookRoom/collectionId/1/level/F";
			String html;
			html = http.getRequest(listUrl, 5000);
			List<String> artList = new JsonUtil().parsePageList(html);
			for(String s : artList){
				System.out.println(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
//        try {
//        	DownUtil downUtil = new DownUtil("https://ss0.baidu.com/"
//                    + "6ONWsjip0QIZ8tyhnq/it/u=1927822194,1885130936&fm=80&w=179&h=119&img.JPEG","11.JPEG",4);
//			downUtil.downLoad();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
	}
    
}
