package x.y.sp;

import java.util.Random;

public class NumberUtil {

	public static int randomNum(){
		Random r=new Random(); 
		return r.nextInt(4)*1000;
	}
	
	public static void main(String[] args){
		System.out.println(new NumberUtil().randomNum());
	}
}
