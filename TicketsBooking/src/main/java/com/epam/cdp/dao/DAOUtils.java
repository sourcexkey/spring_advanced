package com.epam.cdp.dao;

public class DAOUtils {
    public static int calcOffset(int pageSize, int pageNum) {
        return (pageNum - 1) * pageSize;
    }
}
