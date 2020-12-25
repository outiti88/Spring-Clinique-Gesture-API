package com.clinique.app.ws.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

@Entity(name="roles")
public class RoleEntity implements Serializable {

	
	private static final long serialVersionUID = 7374973620355895618L;

	
	@Id
	@GeneratedValue
	private long id;
	
	@NotBlank
	@Column(length=100)
	private String roleId;
	
	@NotBlank
	private String name;
	
	
	
	@OneToOne
	@JoinColumn(name="users_id")
	private UserEntity user;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
