package com.grubmenow.service.persist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersistenceFactory {

    private static PersistenceHandler persistenceHandler;
    
    @Autowired(required=true)
    public void setPersistenceHandler(PersistenceHandler persistenceHandler)
    {
        PersistenceFactory.persistenceHandler = persistenceHandler;
    }
    
    public static PersistenceHandler getInstance()
    {
        return persistenceHandler;
    }
}
