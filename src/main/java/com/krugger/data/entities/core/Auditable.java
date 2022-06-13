package com.krugger.data.entities.core;

/**
 *
 * @author xzabalam
 *
 */
public interface Auditable extends Dated {
	String getUsernameCrea();

	String getUsernameModifica();

	void setUsernameCrea(String usernameCrea);

	void setUsernameModifica(String usernameModifica);
}
