package br.com.decora.accesscontrolservice.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.bson.types.ObjectId;
import org.codehaus.jackson.JsonGenerator;
import org.mongodb.morphia.Key;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;

import br.com.decora.accesscontrolservice.dao.UserDAO;
import br.com.decora.accesscontrolservice.model.SessionDTO;
import br.com.decora.accesscontrolservice.model.User;
import br.com.decora.accesscontrolservice.model.UserRole;
import br.com.decora.accesscontrolservice.providers.EncryptionProvider;
import br.com.decora.accesscontrolservice.providers.JwtAuthenticationProvider;

@Path("/login")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public class LoginController {

	private static final Long SESSION_EXPIRATION_TIME = new Long(1000000);

	@Inject
	private UserDAO userDao;

	@Inject
	private JwtAuthenticationProvider jwt;

	@Inject
	EncryptionProvider encryptionProvider;

	@POST
	@Path("")
	public SessionDTO login(User userInput) {
		if (userDao.find().isEmpty()) {
			createDeafultUser();
		}
		User user = validateUser(userInput);
		if (user == null) {
			return null;
		}
		SessionDTO sessionDTO = new SessionDTO(user.getUserName(), jwt.createJWT(user, SESSION_EXPIRATION_TIME));
		return sessionDTO;
	}

	@POST
	@Path("/validateSession")
	public Boolean validateSession(SessionDTO sessionDTO) {
		User user = userDao.findByUserName(sessionDTO.getUserName());		
		return jwt.parseJwtAsUser(sessionDTO.getJwtToken(), user);
	}

	@POST
	@Path("/validateSessionAsAdmin")
	public Boolean validateSessionAsAdmin(SessionDTO sessionDTO) {		
		User user = userDao.findByUserName(sessionDTO.getUserName());		
		return jwt.parseJwtAsAdmin(sessionDTO.getJwtToken(), user);
	}
	
	

	private User validateUser(User userInput) {
		User user = userDao.findByUserName(userInput.getUserName());
		if (user != null) {
			String passwordToValidate = encryptionProvider.getSecurePassword(userInput.getPassword());
			if (passwordToValidate.equals(user.getPassword())) {
				return user;
			}			
		}
		return null;
	}
	private User createDeafultUser() {
		User user = new User();
		user.setFirstName("admin");
		user.setLastName("admin");
		user.setUserName("admin");
		user.setPassword("admin");
		user.setUserRole(UserRole.ADMIN);
		encryptionProvider.hashPassword(user);
		userDao.save(user);
		return user;
	}
}
