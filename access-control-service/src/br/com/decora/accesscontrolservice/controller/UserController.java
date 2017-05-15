package br.com.decora.accesscontrolservice.controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.mongodb.morphia.Key;

import br.com.decora.accesscontrolservice.dao.UserDAO;
import br.com.decora.accesscontrolservice.model.User;
import br.com.decora.accesscontrolservice.providers.EncryptionProvider;

@Path("/users")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public class UserController {

	@Inject
	private UserDAO userDao;
	
	@Inject
	EncryptionProvider encryptionProvider;

	@GET
	@Path("")
	public List<User> getUserList() {
		return userDao.find();

	}
	
	@GET
	@Path("/search")
	public List<User> findByUserName(@QueryParam("term") String term) {
		if (term.equals(null) || term.equals("")) {
			return userDao.find();			
		}
		return userDao.findUsersByName(term);
	}

	@POST
	@Path("")
	public User addUser(User user) {
		encryptionProvider.hashPassword(user);
		userDao.save(user);
		
		return user;
	}


}
