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

import com.ch.sys.model.User;

@Entity
@Table(name = "MATCH_PLAYER", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class MatchPlayer implements Serializable {

	private String id;
	private Date createdatetime;
	private Date updatedatetime;
	private TeamMate teamMate;
	private Integer goal;
	private Integer assist;
	private Integer mvp;
	private Double fee;
	private String joinType;
	private TeamMatch teamMatch;
	
	public MatchPlayer() {
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TEAM_MATE_ID")
	public TeamMate getTeamMate() {
		return teamMate;
	}
	public void setTeamMate(TeamMate teamMate) {
		this.teamMate = teamMate;
	}
	
	@Column(name = "FEE", precision = 8, scale = 2)
	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TEAM_MATCH_ID")
	public TeamMatch getTeamMatch() {
		return teamMatch;
	}

	public void setTeamMatch(TeamMatch teamMatch) {
		this.teamMatch = teamMatch;
	}

	@Column(name = "GOAL", precision = 8, scale = 0)
	public Integer getGoal() {
		return goal;
	}

	public void setGoal(Integer goal) {
		this.goal = goal;
	}
	
	@Column(name = "ASSIST", precision = 8, scale = 0)
	public Integer getAssist() {
		return assist;
	}

	public void setAssist(Integer assist) {
		this.assist = assist;
	}
	
	@Column(name = "MVP", precision = 8, scale = 0)
	public Integer getMvp() {
		return mvp;
	}

	public void setMvp(Integer mvp) {
		this.mvp = mvp;
	}
	
	@Column(name = "JOIN_TYPE", length = 50)
	public String getJoinType() {
		return joinType;
	}

	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}
	
	
	
}
