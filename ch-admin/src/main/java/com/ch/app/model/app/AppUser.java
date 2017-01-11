package com.ch.app.model.app;

import com.ch.app.model.Team;
import com.ch.sys.model.User;

public class AppUser extends User{
	

	private String currentTeamId;
	private String currentTeamName;
	private String currentTeamLogo;
	
	public AppUser(User user) {
		setName(user.getName());
		setProvince(user.getProvince());
		setCity(user.getCity());
		setLoginname(user.getLoginname());
	}



	public void setCurrentTeam(Team currentTeam) {
		setCurrentTeamId(currentTeam.getId());
		setCurrentTeamName(currentTeam.getName());
		setCurrentTeamLogo(currentTeam.getLogoPic());
	}



	public String getCurrentTeamId() {
		return currentTeamId;
	}



	public void setCurrentTeamId(String currentTeamId) {
		this.currentTeamId = currentTeamId;
	}



	public String getCurrentTeamName() {
		return currentTeamName;
	}



	public void setCurrentTeamName(String currentTeamName) {
		this.currentTeamName = currentTeamName;
	}



	public String getCurrentTeamLogo() {
		return currentTeamLogo;
	}



	public void setCurrentTeamLogo(String currentTeamLogo) {
		this.currentTeamLogo = currentTeamLogo;
	}
	
	
}
