package com.ch.app.controller;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ch.app.model.app.AppUser;
import com.ch.base.controller.BaseController;
import com.ch.base.model.SessionInfo;
import com.ch.base.model.easyui.Json;
import com.ch.base.service.BaseServiceI;
import com.ch.base.util.ConfigUtil;
import com.ch.base.util.JsonUtil;
import com.ch.sys.model.User;

@Controller
@RequestMapping(value="/app/web")
public class AppWebController extends BaseController<User> {

	@Autowired
	private BaseServiceI<User> baseService;
	
	private static String FP_Football_Rss = "http://news.qq.com/newsgn/rss_newsgn.xml";
	
	/**
	 * 访问外部RSS资源，解决JS跨域问题。
	 * @param model
	 * @param response
	 * @param request
	 * @param session
	 * @param pw
	 */
	@RequestMapping(value="getFpRss")
	public void getFpRss(Model model,HttpServletResponse response,HttpServletRequest request,HttpSession session,PrintWriter pw){
		
		String xml = getRss(FP_Football_Rss);
		
//		Json json = new Json();
// 		json.setMsg("加入成功!");
// 		json.setSuccess(true);
// 		json.setObj(xml);
 			

// 		JsonUtil.writeJson(json, pw);
		response.setContentType("text/xml;charset=UTF-8");
		pw.print(xml);
	}
	
	
	private String getRss(String rssUrl){
		

		
		StringBuffer temp = new StringBuffer();  
        try {  
             
              
        	URL url = new URL("http://voice.hupu.com/generated/voice/news_soccer.xml");
            
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
         // 由于服务器屏蔽java作为客户端访问rss，所以设置User-Agent  
            conn.setRequestProperty("User-Agent",  
                    "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			if(conn.getResponseCode() == 200){
				//得到输入流
				InputStream in = conn.getInputStream();
				byte[] b = new byte[1024];
				int len;
				Reader rd = new InputStreamReader(in, "Gb2312");  
	            int c = 0;  
	            while ((c = rd.read()) != -1) {  
	                temp.append((char) c);  
	            } 
				in.close();  
			}
            //System.out.println(temp.toString());  
            
            
           

  
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
		
        return temp.toString();
		
	}

}
