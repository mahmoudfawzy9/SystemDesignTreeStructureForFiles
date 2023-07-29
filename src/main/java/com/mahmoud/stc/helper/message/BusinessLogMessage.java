package com.mahmoud.stc.helper.message;

import com.sun.jdi.request.InvalidRequestStateException;

public class BusinessLogMessage {

    //TODO for time reasons would use these instead of the runtime Exception provided at service layers
    private BusinessLogMessage() {
        throw new IllegalStateException(BusinessMessage.ILLEGAL_STATE_EXCEPTION);
    }


    public static class PermissionGroup {
        private PermissionGroup() {
            throw new IllegalStateException(BusinessMessage.ILLEGAL_STATE_EXCEPTION);
        }

        public static final String PERMISSION_GROUP_CREATED = "Permission group created successfully";
        public static final String PERMISSION_GROUP_UPDATED = "Permission group updated successfully : {}";
        public static final String PERMISSION_GROUP_DELETED = "Permission group deleted successfully : {}";
        public static final String PERMISSION_GROUP_FOUND = "Permission group found successfully : {}";
        public static final String PERMISSION_GROUP_LIST_FOUND = "Permission group list found successfully";
        public static final String PERMISSION_GROUP_NOT_FOUND = "Permission group not found : {}";
        public static final String PERMISSION_GROUP_LIST_EMPTY = "Permission group list is empty";
    }

    public static class Folder {

        private Folder() {
            throw new InvalidRequestStateException(BusinessMessage.INVALID_REQUEST_EXCEPTION);
        }

        public static final String FOLDER_CREATED = "Folder created successfully";
        public static final String FOLDER_UPDATED = "Folder updated successfully : {}";
        public static final String FOLDER_DELETED = "Folder deleted successfully : {}";
        public static final String FOLDER_FOUND = "Folder found successfully : {}";
        public static final String FOLDER_LIST_FOUND = "Folder list found successfully";
        public static final String FOLDER_NOT_FOUND = "Folder not found : {}";
        public static final String FOLDER_LIST_EMPTY = "Folder list is empty";
        public static final String FOLDER_ALREADY_EXISTS = "Folder already exists : {}";
    }
}
