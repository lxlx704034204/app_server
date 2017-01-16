package com.ch.app.controller;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ch.app.model.FootballSkill;
import com.ch.app.model.TeamMate;
import com.ch.base.controller.BaseController;
import com.ch.base.service.BaseServiceI;
import com.ch.base.util.JsonUtil;
import com.ch.sys.model.User;
import com.ch.sys.service.UserServiceI;

@Controller
@RequestMapping(value="/app/web")
public class AppWebController extends BaseController<User> {

	@Resource
	public void setService(UserServiceI service) {
		this.service = service;
	}

	@Resource
	private JdbcTemplate jdbcTemplate;
	
	private SimpleDateFormat sdf = new SimpleDateFormat();
	
	private static String FP_Football_Rss = "http://voice.hupu.com/generated/voice/news_soccer.xml";
	private static String Football_SKILL_WEB = "http://www.jqzfxxgk.cn/authord/zuqiujiaoxue";
	
	
	@RequestMapping(value="/getFootballSkill")
	public void getFootballSkill(Model model,HttpServletResponse response,HttpServletRequest request,HttpSession session,PrintWriter pw){
		
		List<FootballSkill> returnList = service
				.findByHql("from FootballSkill m");
		
		JsonUtil.writeJson(returnList, pw); 
		
	}
	
	/**
	 * 访问外部RSS资源，解决JS跨域问题。
	 * @param model
	 * @param response
	 * @param request
	 * @param session
	 * @param pw
	 */
	@RequestMapping(value="/getFpRss")
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
