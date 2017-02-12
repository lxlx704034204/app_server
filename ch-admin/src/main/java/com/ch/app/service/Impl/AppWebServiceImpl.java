package com.ch.app.service.Impl;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ch.app.model.FootballSkill;
import com.ch.app.service.AppWebServiceI;
import com.ch.base.dao.SimpleDaoI;
import com.ch.base.service.impl.SimpleServiceImpl;

/**
 * @author : lijunjie
 * @version ：2017年2月3日 上午12:48:05
 * 类说明
 */

@Service
public class AppWebServiceImpl extends SimpleServiceImpl implements AppWebServiceI {

	@Autowired
	private SimpleDaoI simpleDao;

	@Resource
	private JdbcTemplate jdbcTemplate;
	
	
	private SimpleDateFormat sdf = new SimpleDateFormat();
	
	
	
	
	@Override
	@Cacheable(value = "NoTimeOutCache", key = "'FootballSkillList'")
	public List<FootballSkill> getFootballSkill() {
		return simpleDao.findByHql("from FootballSkill m");
	}

	@Override
	@Cacheable(value = "20MinsTimeOutCache", key = "'RssString_'+#rssUrl")
	public String getRss(String rssUrl) {

		StringBuffer temp = new StringBuffer();  
        try {  
             
              
        	URL url = new URL(rssUrl);
            
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
