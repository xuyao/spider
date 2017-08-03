package x.y.sp;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{

	
	public static void main(String[] args) {
			HttpUtil http = new HttpUtil();
			try {
				String html = http.getRequest("https://www.kidsa-z.com/main/ReadingBookRoom/collectionId/1/level/aa", 3000);
				List<String> artList = new JsonUtil().parsePageList(html);
				for(String artUrl : artList){
					
					
				}
//				http.download("https://content.kidsa-z.com//audio//1241//raz_the100thdayproject_li24_title_text.mp3", "f://1.mp3");
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
    
    
}
