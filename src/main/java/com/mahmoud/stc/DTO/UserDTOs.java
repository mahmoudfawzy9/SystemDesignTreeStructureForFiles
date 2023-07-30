package com.mahmoud.stc.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

public class UserDTOs {


    @Getter
    @Schema(name = "User Registration Data")
    public static class UserRegistrationObject {
        @Schema(example = "testuser@stc.com", required = true)
        public String email;


        @Schema(example = "John Smith", required = true)
        public String name;

        @Schema(example = "Attachment.jpg", required = true)
        public String avatar;

        @Schema(example = "EDIT", required = true)
        public String permissionLevel;
        @JsonProperty("phone_number")
        private String phoneNumber;
        @JsonProperty("date_of_birth")
        private String dateOfBirth;
    }
    @Getter
    @Schema(name = "User Registration Data")
    public static class UserRegistrationObjectV2 {
        public String name;
        public String email;
        public String password;
        @JsonProperty("org_id")
        public Long orgId;

        @JsonProperty("redirect_url")
        private String redirectUrl;
        private String avatar;
        @JsonProperty("phone_number")
        private String phoneNumber;

    }
}
