package com.example.demo.Dao;

import com.example.demo.Model.UserSesionControll;

public interface UserSesionDao {
	public UserSesionControll startSesion(UserSesionControll users);
	public UserSesionControll endSesion(String userEmail);
	public boolean cheackUserSesion(String ueserEmail);
	
}
