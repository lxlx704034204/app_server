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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.ch.sys.model.Resource;
import com.ch.sys.model.User;


@Entity
@Table(name = "TEAM", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Team implements Serializable {

	private String id;
	private Date createdatetime;
	private Date updatedatetime;
	private String name;
	private String logoPic;
	private String description;
	private String city;
	private Boolean isJoin;
	
	private Set<TeamMate> teamMates = new HashSet<TeamMate>(0);

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

	@Column(name = "DESCRIPTION", length = 200)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "LOGO_PIC", nullable = false, length = 200)
	public String getLogoPic() {
		return logoPic;
	}

	public void setLogoPic(String logoPic) {
		this.logoPic = logoPic;
	}

	@Column(name = "CITY", nullable = false, length = 100)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "team")
	public Set<TeamMate> getTeamMates() {
		return teamMates;
	}

	public void setTeamMates(Set<TeamMate> teamMates) {
		this.teamMates = teamMates;
	}

	@Column(name = "IS_JOIN")
	@org.hibernate.annotations.Type(type="yes_no")
	public Boolean getIsJoin() {
		return isJoin;
	}

	public void setIsJoin(Boolean isJoin) {
		this.isJoin = isJoin;
	}
	
	
}
