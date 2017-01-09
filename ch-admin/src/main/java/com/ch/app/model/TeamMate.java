package com.ch.app.model;

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

import com.ch.sys.model.User;

@Entity
@Table(name = "TEAM_MATE", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TeamMate implements Serializable {

	private String id;
	private Date createdatetime;
	private Date updatedatetime;
	private String name;
	private Team team;
	private User user;
	private Boolean isTeamLeader;
	private Boolean isTeamManager;
	private Integer totalGoal;
	private Integer totalAssist;
	private Integer totalMvp;
	private Integer age;
	private Integer height;
	private Integer weight;
	private String position;
	

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

	@Column(name = "NAME", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TEAM_ID")
	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "IS_TEAM_LEADER", precision = 2, scale = 0)
	@org.hibernate.annotations.Type(type="byte")
	public Boolean getIsTeamLeader() {
		return isTeamLeader;
	}

	public void setIsTeamLeader(Boolean isTeamLeader) {
		this.isTeamLeader = isTeamLeader;
	}

	@Column(name = "IS_TEAM_MAGAGER", precision = 2, scale = 0)
	@org.hibernate.annotations.Type(type="byte")
	public Boolean getIsTeamManager() {
		return isTeamManager;
	}

	public void setIsTeamManager(Boolean isTeamManager) {
		this.isTeamManager = isTeamManager;
	}

	@Column(name = "TOTAL_GOAL", precision = 8, scale = 0)
	public Integer getTotalGoal() {
		return totalGoal;
	}

	public void setTotalGoal(Integer totalGoal) {
		this.totalGoal = totalGoal;
	}

	@Column(name = "TOTAL_ASSIST", precision = 8, scale = 0)
	public Integer getTotalAssist() {
		return totalAssist;
	}

	public void setTotalAssist(Integer totalAssist) {
		this.totalAssist = totalAssist;
	}

	@Column(name = "TOTAL_MVP", precision = 8, scale = 0)
	public Integer getTotalMvp() {
		return totalMvp;
	}

	public void setTotalMvp(Integer totalMvp) {
		this.totalMvp = totalMvp;
	}

	@Column(name = "AGE", precision = 8, scale = 0)
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Column(name = "HEIGHT", precision = 8, scale = 0)
	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	@Column(name = "WEIGHT", precision = 8, scale = 0)
	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	@Column(name = "POSITION", nullable = false, length = 200)
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
}
