package com.epam.cdp.services.utils;

import com.epam.cdp.model.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class ArgsCheckingUtils {
    private static final Logger LOG = LoggerFactory.getLogger(ArgsCheckingUtils.class);

    public static void checkIsIdSet(Entity entity) {
        if (entity.getId() == 0) {
            String message = "Entities id is not set";
            LOG.warn(message);
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkPageSize(int pageSize) {
        if (pageSize <= 0) {
            String message = "Illegal page size: " + pageSize;
            LOG.warn(message);
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkPageNum(int pageNum) {
        if (pageNum <= 0) {
            String message = "Illegal page number: " + pageNum;
            LOG.warn(message);
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkIsNull(Object object) {
        if (object == null) {
            String message = "Argument is null";
            LOG.warn(message);
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkIsEmptyString(String str) {
        if (StringUtils.isEmpty(str)) {
            String message = "String argument is empty";
            LOG.warn(message);
            throw new IllegalArgumentException(message);
        }
    }
}
