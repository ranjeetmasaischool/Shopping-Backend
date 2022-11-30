package com.example.demo.Repositry;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSesionControll extends JpaRepository<com.example.demo.Model.UserSesionControll, Integer> {
	
	public com.example.demo.Model.UserSesionControll findByEmail(String email);

}
