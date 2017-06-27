package com.zhendong.li.mvc.co.jsoup;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/jsoup")
public class JsoupTest {
   
	@RequestMapping("/geturl.do")
	@ResponseBody
	public Object getHtml(String url,String urltype){
		URL urls=null;
		String htmlcode="";

		try {
			urls=new URL(url);
			HttpURLConnection urlconn=(HttpURLConnection) urls.openConnection();
			urlconn.setRequestMethod(urltype);
			urlconn.setDoInput(true);
			urlconn.setDoOutput(true);	
		    urlconn.setConnectTimeout(5000);
		    if(urlconn.getResponseCode()==200){
		    	InputStream in=urlconn.getInputStream();
		    	htmlcode=this.inputStream2String(in);
		    }
	        
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("我的爬虫页面"+htmlcode);
		return htmlcode;
	}
	public String inputStream2String(InputStream   is)   throws   IOException{ 
          ByteArrayOutputStream  baos = new ByteArrayOutputStream(); 
          int i=-1; 
          while((i=is.read())!=-1){ 
            baos.write(i); 
          } 
          return   baos.toString(); 
    }


}
