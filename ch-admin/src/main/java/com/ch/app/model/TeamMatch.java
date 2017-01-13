package com.ch.app.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


@Entity
@Table(name = "TEAM_MATCH", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TeamMatch implements Serializable{

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
	private Set<MatchPlayer> matchPlayers = new HashSet<MatchPlayer>(0);
	
	
	public TeamMatch() {

	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	public String getId() {
		if (!StringUtils.isBlank(this.id)) {
			return this.id;
		}
		return UUID.randomUUID().toString();
	}

	public void setId(String id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATEDATETIME", length = 7)
	public Date getUpdatedatetime() {
		if (this.updatedatetime != null)
			return this.updatedatetime;
		return new Date();
	}

	public void setUpdatedatetime(Date updatedatetime) {
		this.updatedatetime = updatedatetime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATETIME", length = 7)
	public Date getCreatedatetime() {
		if (this.createdatetime != null)
			return this.createdatetime;
		return new Date();
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

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "MATCH_TEAM_MATE", schema = "", joinColumns = { @JoinColumn(name = "MATCH_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "PLAYER_ID", nullable = false, updatable = false) })
	public Set<MatchPlayer> getMatchPlayers() {
		return matchPlayers;
	}

	public void setMatchPlayers(Set<MatchPlayer> matchPlayers) {
		this.matchPlayers = matchPlayers;
	}
	
	
}
