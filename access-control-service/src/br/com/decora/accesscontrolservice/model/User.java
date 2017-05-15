package br.com.decora.accesscontrolservice.model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

@Entity
public class User {

	@Id
	private ObjectId id;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String userRole;

	public User() {
	}

	public User(ObjectId id, String userName, String password, String firstName, String lastName, String userRole) {
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userRole = userRole;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	@Override
	public String toString() {
		return new StringBuffer(" User Name : ").append("First Name").append(this.firstName).append("Last Name")
				.append(this.lastName).append(this.userName).append(" Password : ").append(this.password)
				.append(" User Role : ").append(this.userRole).append(this.id).toString();
	}

}
