package x.y.sp;

import java.util.Random;

public class NumberUtil {

	public int randomNum(){
		Random r=new Random(); 
		return r.nextInt(5)*1000;
	}
	
	public static void main(String[] args){
		System.out.println(new NumberUtil().randomNum());
	}
}
