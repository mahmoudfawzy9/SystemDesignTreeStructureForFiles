package com.mahmoud.stc.helper.message;

public class BusinessMessage {
    public static final String ILLEGAL_STATE_EXCEPTION = "Utility class, cannot be instantiated";

    public static final String INVALID_REQUEST_EXCEPTION = "A parent must be specified for the folder";


    private BusinessMessage() {
        throw new IllegalStateException(ILLEGAL_STATE_EXCEPTION);
    }


    public static class PermissionGroup {
        private PermissionGroup() {
            throw new IllegalStateException(ILLEGAL_STATE_EXCEPTION);
        }

        public static final String PERMISSION_GROUP_NOT_FOUND = "Permission Group not found";
        public static final String  PERMISSION_GROUP_LIST_EMPTY = "Permission Group list is empty";
    }
}
