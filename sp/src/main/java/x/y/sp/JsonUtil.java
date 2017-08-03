package x.y.sp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
				System.out.println(ae.attr("href"));
				list.add(ConstsUtil.getValue("website")+ae.attr("href"));
			}
		}
		return list;
	}
	
	public void parsePage(String html){
		
		
	}
	
	
	
}
