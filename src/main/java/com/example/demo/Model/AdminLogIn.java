package com.example.demo.Model;

import io.swagger.v3.oas.annotations.servers.Server;
import lombok.Data;

@Server
@Data
public class AdminLogIn {
	
	private String email;
	private String password;


}
