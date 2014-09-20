package com.grubmenow.service.persist;

import lombok.Getter;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class SessionHandler {

	@Getter
	private Session session;
	
	private Transaction transaction;
	
	public SessionHandler(Session session) {
		this.session = session;
		this.transaction = session.beginTransaction();
	}
	
	public void commit() {
		transaction.commit();
		session.close();
	}

	public void rollback() {
		transaction.rollback();
		session.close();		
	}

}
