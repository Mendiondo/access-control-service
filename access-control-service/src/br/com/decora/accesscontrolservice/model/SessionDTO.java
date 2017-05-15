package br.com.decora.accesscontrolservice.model;

import org.mongodb.morphia.annotations.Entity;

@Entity
public class SessionDTO {
	
	private String userName;
	private String jwtToken;
	
	public SessionDTO(){ }
	
	public SessionDTO(String userName, String jwtToken){
		this.userName = userName;
		this.jwtToken = jwtToken;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getJwtToken() {
		return jwtToken;
	}
	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	
	@Override
	public String toString() {
		return new StringBuffer(" User Name : ").append(this.userName).append("jwtToken")
				.append(this.jwtToken).toString();
	}
		
}
