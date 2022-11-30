package com.example.demo.Model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class UserSesionControll {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Integer userId;
	private String email;
	private LocalDateTime sesionDateTime;
	public UserSesionControll( Integer userId, String email, LocalDateTime sesionDateTime) {
		super();
		this.userId = userId;
		this.email = email;
		this.sesionDateTime = sesionDateTime;
	}
	public UserSesionControll() {
		// TODO Auto-generated constructor stub
	}
	

}
