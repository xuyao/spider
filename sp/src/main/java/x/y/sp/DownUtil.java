package x.y.sp;

import java.net.URL;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;

import org.apache.commons.io.FileUtils;

public class DownUtil {
    //定义下载路径
    private String path;
    //指定所下载的文件的保存位置
    private String targetFile;
    //定义下载线程的数量
    private int threadNum;
    //定义下载线程的对象
    private DownThread[] threads;
    //下载文件的总大小
    private int fileSize;

    public DownUtil(String path,String targetFile,int threadNum){
        this.path = path;
        this.targetFile = targetFile;
        this.threadNum = threadNum;
        threads = new DownThread[threadNum];
        this.targetFile = targetFile;
    }
    public void downLoad() throws Exception{
        URL url = new URL(path);
        String cookie = FileUtils.readLines(new File("src/main/java/cookie.txt"),"utf-8").get(0);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection(); 
        conn.setConnectTimeout(30 * 1000);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");  
        conn.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch, br");
        conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.6,en;q=0.4"); 
        conn.setRequestProperty("Cache-Control", "max-age=0"); 
		conn.setRequestProperty("Cookie", cookie);
		conn.setRequestProperty("Connection", "keep-alive");
		conn.setRequestProperty("Host", "www.kidsa-z.com");
		conn.setRequestProperty("Referer", "https://www.kidsa-z.com/main/ReadingBookRoom");
		conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");


        //得到文件大小
        fileSize = conn.getContentLength();
        conn.disconnect();
        int currentPartSize = fileSize / threadNum + 1;
	    File f = new File(targetFile);
		try {
			if(!f.getParentFile().exists()){
				f.getParentFile().mkdirs();//创建父目录
			}
			if (!f.exists()) {
				f.createNewFile();//创建文件
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
        RandomAccessFile file = new RandomAccessFile(targetFile,"rw");
        //设置本地文件大小
        file.setLength(fileSize);
        file.close();
        for(int i = 0;i < threadNum;i++){
            //计算每个线程的下载位置
            int startPos = i * currentPartSize;
            //每个线程使用一个RandomAccessFile进行下载
            RandomAccessFile currentPart = new RandomAccessFile(targetFile,"rw");
            //定位该线程的下载位置
            currentPart.seek(startPos);
            //创建下载线程
            threads[i] = new DownThread(startPos, currentPartSize, currentPart);
            threads[i].start();
        }
    }
    public double getCompleteRate(){
        int sumSize = 0;
        for(int i = 0;i < threadNum;i++){
            sumSize += threads[i].length;
        }
        return sumSize * 1.0 / fileSize;
    }
    private class DownThread extends Thread{
        //当前线程的下载位置
        private int startPos;
        //定义当前线程负责下载的文件大小
        private int currentPartSize;
        //当前线程需要下载的文件块,此类的实例支持对随机访问文件的读取和写入。
        private RandomAccessFile currentPart;
        //定义该线程已下载的字节数
        public int length;
        private DownThread(int startPos,int currentPartSize,RandomAccessFile currentPart){
            this.startPos = startPos;
            this.currentPartSize = currentPartSize;
            this.currentPart = currentPart;
        }
        public void run(){
            try{
                URL url = new URL(path);
                //url.openConnection():返回一个 URLConnection 对象，它表示到 URL 所引用的远程对象的连接。
                String cookie = FileUtils.readLines(new File("src/main/java/cookie.txt"),"utf-8").get(0);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection(); 
                conn.setConnectTimeout(30 * 1000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");  
                conn.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch, br");
                conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.6,en;q=0.4"); 
                conn.setRequestProperty("Cache-Control", "max-age=0"); 
        		conn.setRequestProperty("Cookie", cookie);
        		conn.setRequestProperty("Connection", "keep-alive");
        		conn.setRequestProperty("Host", "www.kidsa-z.com");
        		conn.setRequestProperty("Referer", "https://www.kidsa-z.com/main/ReadingBookRoom");
        		conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
        		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
                InputStream inputStream = conn.getInputStream();
                //inputStream.skip(n);跳过和丢弃此输入流中数据的 n 个字节
                inputStream.skip(this.startPos); 
                byte[] buffer = new byte[1024];
                int hasRead = 0;
                //读取网络数据写入本地
                while(length < currentPartSize && (hasRead = inputStream.read(buffer)) != -1){
                    currentPart.write(buffer, 0, hasRead);
                    length += hasRead;
                }
                currentPart.close();
                inputStream.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}