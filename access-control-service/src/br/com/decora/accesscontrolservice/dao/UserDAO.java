package br.com.decora.accesscontrolservice.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryResults;

import com.mongodb.MongoClient;

import br.com.decora.accesscontrolservice.model.User;
import br.com.decora.accesscontrolservice.model.UserRole;
import br.com.decora.accesscontrolservice.providers.MongoClientProvider;
import br.com.decora.accesscontrolservice.qualifiers.Dao;

@Dao
public class UserDAO {
	
	@Inject
	MongoClientProvider mongoClientProvider;
	
	private UserBasicDAO userBasicDAO;
	
	@PostConstruct
	public void init(){
		MongoClient mongoClient = mongoClientProvider.getMongoClient();
		String dbName = mongoClientProvider.getDbName();
		Morphia morphia = mongoClientProvider.getMorphhia();
		userBasicDAO=new UserBasicDAO(mongoClient, morphia, dbName);	
	}
	
	public List<User> find() {
        return userBasicDAO.find().asList();
    }

	public User findByUserName(String userName) {
		return userBasicDAO.createQuery().field("userName").equal(userName).get();
	}
	
	public List<User> findUsersByName(String name) {
		Query<User> query = userBasicDAO.createQuery();
		query.or(
		  query.criteria("firstName").containsIgnoreCase(name),
		  query.criteria("lastName").containsIgnoreCase(name));
		return query.asList();
	}
	
	public String findIdByUserName(String userName) {
		Key<User> keyUser = userBasicDAO.findOneId(userBasicDAO.createQuery().field("userName").equal(userName));
		assert(keyUser.getId().toString() != null);	
		return keyUser.getId().toString();
	}

	public Key<User> save(User user) {
		return userBasicDAO.save(user);
	} 
	
}
