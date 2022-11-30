package com.example.demo.Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.UserSesionControll;


@Service
public class UserSesionImpli implements UserSesionDao{
	
	
	@Autowired
	private com.example.demo.Repositry.UserSesionControll useCon;


	@Override
	public UserSesionControll startSesion(UserSesionControll users) {
	return	useCon.save(users);
	}

	@Override
	public UserSesionControll endSesion(String userEmail) {
		
		UserSesionControll us= useCon.findByEmail(userEmail);
		
		useCon.delete(us);
		return us;
	}

	@Override
	public boolean cheackUserSesion(String ueserEmail) {
		UserSesionControll us=	useCon.findByEmail(ueserEmail);
		if(us!=null) {
			return true;
		}
		return false;
	}

	

}
