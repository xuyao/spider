package x.y.sp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsonUtil {

	public List<String> parsePageList(String html){
		List<String> list = new ArrayList<String>();
		Document doc = Jsoup.parse(html);
		Elements elements = doc.getElementsByClass("activity");
		Iterator it = elements.listIterator();
		while(it.hasNext()){
			Element e = (Element)it.next();
			Elements alist = e.getElementsByTag("a");
			Iterator ita = alist.listIterator();
			while(ita.hasNext()){
				Element ae = (Element)ita.next();
				list.add(ConstsUtil.getValue("website")+ae.attr("href"));
			}
		}
		return list;
	}
	
	public void parsePage(String html, String fileName){
		Document doc = Jsoup.parse(html);
		Elements elements = doc.getElementsByTag("script");
		Iterator it = elements.listIterator();
		HttpUtil http = new HttpUtil();
		String website = ConstsUtil.getValue("website");
		String filePath = ConstsUtil.getValue("filepath");
		String contentweb = ConstsUtil.getValue("contentweb");
		
//		while(it.hasNext()){
//			Element e = (Element)it.next();
		
		/**
			//先下载js
			String jsUrl = e.attr("src");
			try {
				if(!StringUtils.isEmpty(jsUrl))
					http.download(website+jsUrl, filePath+jsUrl);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		*/
//			
//			//开始解析json
//			String js = e.data();
//			if(js.contains("var studentBook")){
//				//System.out.println(StringUtils.trim(js));
//				String[] st = js.split(", ");
//				JSONArray jsonArray = new JSONArray(st[2]);
//				Iterator itJsArr = jsonArray.iterator();
//				
//				while(itJsArr.hasNext()){
//					JSONObject jsonObj = (JSONObject)itJsArr.next();
//					//下载image图片
//					String jpgUrl = jsonObj.get("page_image").toString();
//					try {
//						http.download(jpgUrl, filePath+jpgUrl.replaceAll(contentweb, ""));
//						Thread.sleep(NumberUtil.randomNum());
//					} catch (Exception e1) {
//						e1.printStackTrace();
//					}
//					
//					//下载mp3
//					if(!jsonObj.has("sections"))
//						continue;
//					
//					JSONArray jsonArrSec = (JSONArray)jsonObj.get("sections");
//					if(jsonArrSec!=null && jsonArrSec.length()!=0){//如果sections里面有值
//						Iterator itSections = jsonArrSec.iterator();
//						
//						while(itSections.hasNext()){
//							JSONObject jsonSections = (JSONObject)itSections.next();
//
//							String sectionAudioUrl = jsonSections.get("section_audio").toString();
//							if(StringUtils.isEmpty(sectionAudioUrl))
//								continue;
//							System.out.println(sectionAudioUrl);
//							try {
//								http.download(sectionAudioUrl, filePath+sectionAudioUrl.replaceAll(contentweb, ""));
//								Thread.sleep(NumberUtil.randomNum());
//							} catch (Exception e1) {
//								e1.printStackTrace();
//							}
//							
//							JSONArray jsonArrPhrases = (JSONArray)jsonSections.get("phrases");
//							Iterator itPhrases = jsonArrPhrases.iterator();
//							
//							while(itPhrases.hasNext()){
//								JSONObject jsonPhrases = (JSONObject)itPhrases.next();
//								
//								JSONArray jsonArrWords = (JSONArray)jsonPhrases.get("words");
//								Iterator itWords = jsonArrWords.iterator();
//								
//								while(itWords.hasNext()){
//									JSONObject jsonAudioUrl = (JSONObject)itWords.next();
//									String audioUrl = jsonAudioUrl.get("audio_url").toString();
//									if(StringUtils.isEmpty(audioUrl))
//										continue;
//									System.err.println(audioUrl);
//									try {
//										if(ConstsUtil.mp3Map.get(audioUrl)!=null)//判断是否以前下过否则就不用再下了。
//											continue;
//										http.download(audioUrl, filePath+audioUrl.replaceAll(contentweb, ""));
//										ConstsUtil.mp3Map.put(audioUrl, audioUrl);
//										Thread.sleep(NumberUtil.randomNum());
//									} catch (Exception e1) {
//										e1.printStackTrace();
//									}
//								}
//								
//							}
//								
//						}
//					}
//					
//				}
//				
//				
//			}
			
//		}
		
		//下载css
		/**
		Elements csselements = doc.getElementsByTag("link");
		Iterator itCss = csselements.iterator();
		while(itCss.hasNext()){
			Element csse = (Element)itCss.next();
			String cssUrl = csse.attr("href");
			if(!StringUtils.isEmpty(cssUrl))
				try {
					http.download(website+cssUrl, filePath+cssUrl);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}*/
		
		//下载html
		String contentrelpace = ConstsUtil.getValue("contentrelpace");
		html = html.replaceAll("/shared/", "shared/");
		html = html.replaceAll(contentrelpace, "");
		try {
			FileUtils.writeStringToFile(new File(filePath+fileName), html, "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
