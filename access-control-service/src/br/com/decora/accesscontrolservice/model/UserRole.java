package br.com.decora.accesscontrolservice.model;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class UserRole {

	public final static String ADMIN = "[ADMIN]";
	public final static String USER = "[USER]";
	
}
