package br.com.decora.accesscontrolservice.providers;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

@Singleton
@Startup
//@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class MongoClientProvider {
		
	private static final String DB_NAME = "accessControl";
	private MongoClient mongoClient;
	private Morphia morphia;
		
	//@Lock(LockType.READ)
	public MongoClient getMongoClient(){	
		return mongoClient;
	}

	//@Lock(LockType.READ)
	public Morphia getMorphhia(){	
		return morphia;
	}
	
	@PostConstruct
	public void init() {
		try {
			mongoClient = new MongoClient();
			morphia = new Morphia();
			System.out.println("Connected to Mongo!");
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
		
	public String getDbName(){	
		return DB_NAME;
	}

}