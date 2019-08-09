package pl.app.jpa.dao;

import pl.app.jpa.model.SystemConfiguration;

public class SystemConfigurationDAO {

    public Integer addSystemConfiguration(SystemConfiguration systemConfiguration) {
        return ObjectCrudDAO.addObject(systemConfiguration);
    }

}
