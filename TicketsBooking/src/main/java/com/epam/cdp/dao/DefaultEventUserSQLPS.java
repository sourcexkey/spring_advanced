package com.epam.cdp.dao;

import com.epam.cdp.model.Event;
import com.epam.cdp.model.User;
import com.epam.cdp.model.impl.DefaultUserEvent;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class DefaultEventUserSQLPS extends MapSqlParameterSource {
    private DefaultUserEvent defaultUserEvent;

    public DefaultEventUserSQLPS(DefaultUserEvent defaultUserEvent) {
        this.defaultUserEvent = defaultUserEvent;
    }

    @Override
    public Object getValue(String paramName) {
        User defaultUser = defaultUserEvent.getDefaultUser();
        Event defaultEvent = defaultUserEvent.getDefaultEvent();
        switch (paramName) {
            case DAOConstants.USER_ID_PLACEHOLDER:
                if (defaultUser != null) {
                    return defaultUser.getId();
                }
                break;
            case DAOConstants.EVENT_ID_PLACEHOLDER:
                if (defaultEvent != null) {
                    return defaultEvent.getId();
                }
                break;
        }
        return super.getValue(paramName);
    }

}
