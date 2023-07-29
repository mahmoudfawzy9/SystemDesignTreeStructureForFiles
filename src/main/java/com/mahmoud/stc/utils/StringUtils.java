package com.mahmoud.stc.utils;


import java.util.Collection;

public class StringUtils extends org.springframework.util.StringUtils{

    public static boolean isBlankOrNull(Object object) {
        if (object == null) {
            return true;
        }
        if (object instanceof String) {
            return ((String) object).isEmpty();
        }
        if (object instanceof Collection<?>) {
            return ((Collection<?>) object).isEmpty();
        }
        return false;
    }
    public static String getFileNameSanitized(String name) {
        return name.replaceAll("[^a-zA-Z0-9\\.]+", "-")
                .replaceAll("^-|-$","")
                .toLowerCase();
    }

    /**
     * validate the name param against Name_PATTERN
     *
     * @param name to be matched against Name_PATTERN
     * @return true if name match Name_PATTERN
     */
    public static boolean isNotBlankOrNull(Object object) {
        return !isBlankOrNull(object);
    }




    }
