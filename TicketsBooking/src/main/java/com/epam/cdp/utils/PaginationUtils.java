package com.epam.cdp.utils;

import java.util.List;

public class PaginationUtils {
    public static <T> List<T> getPaginationSubList(List<T> objects, int pageSize, int pageNum) {
        int beginEventIndex = (pageNum - 1) * pageSize;
        int endEventIndex = beginEventIndex + pageSize;
        endEventIndex = endEventIndex <= objects.size() ? endEventIndex : objects.size();
        return objects.subList(beginEventIndex, endEventIndex);
    }
}
