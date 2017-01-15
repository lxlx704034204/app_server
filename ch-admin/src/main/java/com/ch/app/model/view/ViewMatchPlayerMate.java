package com.ch.app.model.view;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.ch.app.model.Team;

@Entity
@Table(name = "v_match_player_mate_user", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class ViewMatchPlayerMate implements Serializable {

	private String id;
	private Date createdatetime;
	private Date updatedatetime;
	private String type;
	private Date matchTime;
	private Date endJoinTime;
	private String place;
	private Integer memberCount;
	private String clothColor;
	private Team homeTeam;
	private Team hostTeam;
	private Integer homeGoal;
	private Integer hostGoal;
	private Double fee;
	
	private String mateId;
	private String mateName;
	private String mateUserId;
	
	private String playerId;
	private String assist;
	private String goal;
	private String mvp;
	private String alreadyJoinMateId;
	private String joinType;
	
	
	public ViewMatchPlayerMate() {
	}
	
	@Id
	@Column(name = "ID", length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATEDATETIME", length = 7)
	public Date getUpdatedatetime() {
		return this.updatedatetime;
	}

	public void setUpdatedatetime(Date updatedatetime) {
		this.updatedatetime = updatedatetime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATETIME", length = 7)
	public Date getCreatedatetime() {
		return this.updatedatetime;
	}

	public void setCreatedatetime(Date createdatetime) {
		this.createdatetime = createdatetime;
	}


	@Column(name = "TYPE", length = 50)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MATCH_TIME", length = 7)
	public Date getMatchTime() {
		return matchTime;
	}

	public void setMatchTime(Date matchTime) {
		this.matchTime = matchTime;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_JOIN_TIME", length = 7)
	public Date getEndJoinTime() {
		return endJoinTime;
	}

	public void setEndJoinTime(Date endJoinTime) {
		this.endJoinTime = endJoinTime;
	}
	
	
	@Column(name = "PLACE", length = 200)
	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	@Column(name = "MEMBER", precision = 4, scale = 0)
	public Integer getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(Integer memberCount) {
		this.memberCount = memberCount;
	}
	
	@Column(name = "CLOTHCOLOR", length = 50)
	public String getClothColor() {
		return clothColor;
	}

	public void setClothColor(String clothColor) {
		this.clothColor = clothColor;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HOME_TEAM_ID")
	public Team getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(Team homeTeam) {
		this.homeTeam = homeTeam;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HOST_TEAM_ID")
	public Team getHostTeam() {
		return hostTeam;
	}

	public void setHostTeam(Team hostTeam) {
		this.hostTeam = hostTeam;
	}
	
	@Column(name = "HOME_GOAL", precision = 4, scale = 0)
	public Integer getHomeGoal() {
		return homeGoal;
	}

	public void setHomeGoal(Integer homeGoal) {
		this.homeGoal = homeGoal;
	}
	
	@Column(name = "HOST_GOAL", precision = 4, scale = 0)
	public Integer getHostGoal() {
		return hostGoal;
	}

	public void setHostGoal(Integer hostGoal) {
		this.hostGoal = hostGoal;
	}
	
	@Column(name = "FEE", precision = 8, scale = 2)
	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}
	
	
	@Column(name = "MATE_ID", length = 50)
	public String getMateId() {
		return mateId;
	}
	public void setMateId(String mateId) {
		this.mateId = mateId;
	}
	@Column(name = "NAME", length = 50)
	public String getMateName() {
		return mateName;
	}
	public void setMateName(String mateName) {
		this.mateName = mateName;
	}
	@Column(name = "USER_ID", length = 50)
	public String getMateUserId() {
		return mateUserId;
	}
	public void setMateUserId(String mateUserId) {
		this.mateUserId = mateUserId;
	}
	@Column(name = "PLAYER_ID", length = 50)
	public String getPlayerId() {
		return playerId;
	}
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}
	@Column(name = "ASSIST", length = 50)
	public String getAssist() {
		return assist;
	}
	public void setAssist(String assist) {
		this.assist = assist;
	}
	@Column(name = "GOAL", length = 50)
	public String getGoal() {
		return goal;
	}
	public void setGoal(String goal) {
		this.goal = goal;
	}
	@Column(name = "MVP", length = 50)
	public String getMvp() {
		return mvp;
	}
	public void setMvp(String mvp) {
		this.mvp = mvp;
	}
	@Column(name = "ALREADY_JOIN_MATE", length = 50)
	public String getAlreadyJoinMateId() {
		return alreadyJoinMateId;
	}
	public void setAlreadyJoinMateId(String alreadyJoinMateId) {
		this.alreadyJoinMateId = alreadyJoinMateId;
	}
	@Column(name = "JOIN_TYPE", length = 50)
	public String getJoinType() {
		return joinType;
	}

	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}
	
	
}
