package cn.ac.ict.modules.ws.listener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.TimerTask;

import cn.ac.ict.modules.ws.entity.Stream;


/** 
 * @ClassName: TestTimeTask
 * @description: 
 * @author: LiGuoqiang
 * @Date: 2020年2月20日 下午3:58:15
 */
public class TestTimeTask extends TimerTask {
	 
	@Override
	public void run() {
		System.out.println("定时器"+new Date());
		 Stream stream =new Stream();
			stream.setSystemName("系统名称");
			stream.setSystemNameEng("系统英文名称");
			stream.setSystemOperationStatus("01");
			stream.setOnlineUser("100");
			stream.setRegisterUserAllNum("100");
			stream.setRegisterUserNum("10");
			stream.setDailyVisits("160");
			stream.setUrlAdress("http://10.1.198.106:9080/sys");
			stream.setUrlRespTime("136");
			stream.setUrlRespStatusCode("01");
			stream.setSysRespTime("4,16,10");
			stream.setBusinessVolume("A系统:15,B系统:85,C系统:66");
			try {
				sendBW(stream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void sendBW(Stream stream) throws IOException{
       //创建服务地址
	   URL url = new URL("");  
       HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
       connection.setRequestMethod("POST");  
       connection.setRequestProperty("content-type", "text/xml;charset=utf-8");  
       connection.setDoInput(true);  
       connection.setDoOutput(true);  
 
       System.err.println(stream.toString());
       String soapXML = stream.toString();  
       OutputStream os = connection.getOutputStream();  
       os.write(soapXML.getBytes());  
         
       int responseCode = connection.getResponseCode();  
       if(200 == responseCode){//表示服务端响应成功  
           InputStream is = connection.getInputStream();  
           InputStreamReader isr = new InputStreamReader(is);  
           BufferedReader br = new BufferedReader(isr);  
             
           StringBuilder sb = new StringBuilder();  
           String temp = null;  
             
           while(null != (temp = br.readLine())){  
               sb.append(temp);  
           }  
             
           System.out.println(sb.toString());  
             
           is.close();  
           isr.close();  
           br.close();  
       }  
 
       os.close();  
   }  
	
	

}
 

