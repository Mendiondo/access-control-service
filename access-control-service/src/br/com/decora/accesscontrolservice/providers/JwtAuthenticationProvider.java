package br.com.decora.accesscontrolservice.providers;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.xml.bind.DatatypeConverter;

import br.com.decora.accesscontrolservice.dao.UserDAO;
import br.com.decora.accesscontrolservice.model.User;
import br.com.decora.accesscontrolservice.model.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Singleton
public class JwtAuthenticationProvider {

	@Inject
	private UserDAO userDao;

	public String createJWT(User user, long ttlMillis) {

		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		String userid = userDao.findIdByUserName(user.getUserName());
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(userid);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		Claims claims = Jwts.claims();
		claims.setSubject(user.getUserName());
		claims.setIssuedAt(now);
		claims.put("userRole", user.getUserRole());

		JwtBuilder builder = Jwts.builder().setClaims(claims).signWith(signatureAlgorithm, signingKey);

		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}

		return builder.compact();
	}

	public Boolean parseJwtAsUser(String jwt, User user) {
		try {			
			Claims claims = parseJWT(jwt, user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
	}

	public Boolean parseJwtAsAdmin(String jwt, User user) {
		try {			
			Claims claims = parseJWT(jwt, user);
			if (!claims.get("userRole").equals(UserRole.ADMIN)) {
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
	}
	
	private Claims parseJWT(String jwt, User user) {
			String userid = userDao.findIdByUserName(user.getUserName());
			Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(userid))
					.parseClaimsJws(jwt).getBody();
			return claims;
	}
}
