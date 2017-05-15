package br.com.decora.accesscontrolservice.dao;

import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;

import com.mongodb.MongoClient;

import br.com.decora.accesscontrolservice.model.User;


public class UserBasicDAO extends BasicDAO<User, String> {

	public UserBasicDAO(MongoClient mongoClient, Morphia morphia, String dbName) {
		super(mongoClient, morphia, dbName);
	}

}
