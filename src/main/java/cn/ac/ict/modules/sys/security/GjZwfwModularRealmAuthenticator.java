package cn.ac.ict.modules.sys.security;

import java.util.Collection;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.realm.Realm;

public class GjZwfwModularRealmAuthenticator extends ModularRealmAuthenticator {

	@Override
	protected AuthenticationInfo doMultiRealmAuthentication(Collection<Realm> realms, AuthenticationToken token) {
		Realm uniqueRealm = getUniqueRealm(realms, token);
		if(uniqueRealm == null){
			throw new UnsupportedTokenException("没有类型匹配的realm");
		}
		return uniqueRealm.getAuthenticationInfo(token);
	}
	
	private Realm getUniqueRealm(Collection<Realm> realms, AuthenticationToken token){
		for(Realm realm : realms){
			if(realm.supports(token)){
				return realm;
			}
		}
		return null;
	}

	
}
