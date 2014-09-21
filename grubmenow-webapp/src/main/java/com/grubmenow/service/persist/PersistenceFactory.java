package com.grubmenow.service.persist;

import lombok.Getter;

public class PersistenceFactory {

	@Getter
	private static final PersistenceHandler instance = new PersistenceHandlerImpl();
}
