package com.garganttua.noghost.backend.api.tools;

public class Emails {

	public static void checkEmail(String email) throws EmailException {
		if( email.split("@").length != 2 ) {
			throw new EmailException("Malformed email, should be formatted : xxx@xxx.xxx");
		}
		if( email.split("\\.").length < 2 ) {
			throw new EmailException("Malformed email, should be formatted : xxx@xxx.xxx");
		}
	}

}
