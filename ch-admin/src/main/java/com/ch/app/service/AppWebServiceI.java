package com.ch.app.service;

import java.util.List;

import com.ch.app.model.FootballSkill;
import com.ch.base.service.SimpleServiceI;

/**
 * @author : lijunjie
 * @version ：2017年2月3日 上午12:40:54
 * 类说明
 */

public interface AppWebServiceI extends SimpleServiceI {
	
	public static String FP_Football_Rss = "http://voice.hupu.com/generated/voice/news_soccer.xml";
	public static String Football_SKILL_WEB = "http://www.jqzfxxgk.cn/authord/zuqiujiaoxue";
	
	public List<FootballSkill> getFootballSkill();
	
	public String getRss(String rssUrl);

}
