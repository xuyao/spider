package x.y.sp;

/**
 * Hello world!
 *
 */
public class App 
{

	
	public static void main(String[] args) {
			HttpUtil http = new HttpUtil();
			String a = null;
			try {
				a = http.getRequest("https://www.kidsa-z.com/main/Activity/id/5481", 3000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(a);
	}
    
    
}
