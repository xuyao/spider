package x.y.sp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
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
			
			Element listen = (Element)alist.get(1);//取三个a标签的第二个只是听力的
			Element read = null;
			if(alist.size()<3){
				list.add(ConstsUtil.getValue("website2")+listen.attr("href")+";"
						+listen.attr("title"));
			}
			else{
				read = (Element)alist.get(2);//取三个a标签的第三个是阅读的
				list.add(ConstsUtil.getValue("website2")+listen.attr("href")+";"
				+listen.attr("title")+";"+ConstsUtil.getValue("website2")+read.attr("href")
				+";"+read.attr("title"));
			}
			
//			Iterator ita = alist.listIterator();
//			while(ita.hasNext()){
//				Element ae = (Element)ita.next();
//				list.add(ConstsUtil.getValue("website2")+ae.attr("href"));
//			}
		}
		return list;
	}
	
	public void parseListenPage(String html, String fileName){
		Document doc = Jsoup.parse(html);
		Elements elements = doc.getElementsByTag("script");
		Iterator it = elements.listIterator();
		HttpUtil http = new HttpUtil();
		String website = ConstsUtil.getValue("website");
		String filePath = ConstsUtil.getValue("filepath");
		String contentweb = ConstsUtil.getValue("contentweb");
		
		while(it.hasNext()){
			Element e = (Element)it.next();
			
			//开始解析json
			String js = e.data();
			if(js.contains("var studentBook")){
				js = StringUtils.trim(js);
				String[] st = js.split(", ");
				JSONArray jsonArray = new JSONArray(st[2]);//book对象第3个参数
				Iterator itJsArr = jsonArray.iterator();
				
				while(itJsArr.hasNext()){
					JSONObject jsonObj = (JSONObject)itJsArr.next();
					//下载image图片
					String jpgUrl = jsonObj.get("page_image").toString();
//					System.out.println(jpgUrl);
//					try {
//						http.download(jpgUrl, filePath+jpgUrl.replaceAll(contentweb, ""));
//						Thread.sleep(NumberUtil.randomNum());
//					} catch (Exception e1) {
//						e1.printStackTrace();
//					}
			        try {
		        	DownUtil downUtil = new DownUtil(jpgUrl,filePath+jpgUrl.replaceAll(contentweb, ""),4);
					downUtil.downLoad();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					
					//下载mp3
					if(!jsonObj.has("sections"))
						continue;
					
					JSONArray jsonArrSec = (JSONArray)jsonObj.get("sections");
					if(jsonArrSec!=null && jsonArrSec.length()!=0){//如果sections里面有值
						Iterator itSections = jsonArrSec.iterator();
						
						while(itSections.hasNext()){
							JSONObject jsonSections = (JSONObject)itSections.next();

							String sectionAudioUrl = jsonSections.get("section_audio").toString();
							if(StringUtils.isEmpty(sectionAudioUrl))
								continue;
//							System.out.println(sectionAudioUrl);
//							try {
//								http.download(sectionAudioUrl, filePath+sectionAudioUrl.replaceAll(contentweb, ""));
//								Thread.sleep(NumberUtil.randomNum());
//							} catch (Exception e1) {
//								e1.printStackTrace();
//							}
					        try {
					        	DownUtil downUtil = new DownUtil(sectionAudioUrl,filePath+sectionAudioUrl.replaceAll(contentweb, ""),4);
								downUtil.downLoad();
								} catch (Exception ex) {
									ex.printStackTrace();
							}
							
							JSONArray jsonArrPhrases = (JSONArray)jsonSections.get("phrases");
							Iterator itPhrases = jsonArrPhrases.iterator();
							
							while(itPhrases.hasNext()){
								JSONObject jsonPhrases = (JSONObject)itPhrases.next();
								
								JSONArray jsonArrWords = (JSONArray)jsonPhrases.get("words");
								Iterator itWords = jsonArrWords.iterator();
								
								while(itWords.hasNext()){
									JSONObject jsonAudioUrl = (JSONObject)itWords.next();
									String audioUrl = jsonAudioUrl.get("audio_url").toString();
									if(StringUtils.isEmpty(audioUrl))
										continue;
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
							        try {
										if(ConstsUtil.mp3Map.get(audioUrl)!=null)//判断是否以前下过否则就不用再下了。
										continue;
							        	DownUtil downUtil = new DownUtil(audioUrl,filePath+audioUrl.replaceAll(contentweb, ""),4);
										downUtil.downLoad();
										ConstsUtil.mp3Map.put(audioUrl, audioUrl);
										} catch (Exception ex) {
											ex.printStackTrace();
									}
								}
							}
						}
					}
				}
			}
		}
		
		//下载html
		String contentrelpace = ConstsUtil.getValue("contentrelpace");
		html = html.replaceAll("/shared/", "shared/");
		html = html.replaceAll(contentrelpace, "");
		html = html.replaceAll("\"animation_content\"\\:\\{[\\s\\S]*?\\}", "\"animation_content\":false");
		try {
			FileUtils.writeStringToFile(new File(filePath+fileName), html, "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void parseReadPage(String html, String fileName){
		Document doc = Jsoup.parse(html);
		Elements elements = doc.getElementsByTag("script");
		Iterator it = elements.listIterator();
		String filePath = ConstsUtil.getValue("filepath");
		
		//下载html
		String contentrelpace = ConstsUtil.getValue("contentrelpace");
		html = html.replaceAll("/shared/", "shared/");
		html = html.replaceAll(contentrelpace, "");
		html = html.replaceAll("\"animation_content\"\\:\\{[\\s\\S]*?\\}", "\"animation_content\":false");
		try {
			FileUtils.writeStringToFile(new File(filePath+fileName), html, "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
