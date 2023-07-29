package com.mahmoud.stc.service.Impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mahmoud.stc.enums.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@AllArgsConstructor
@NoArgsConstructor
public class UserApiResponse {

    @JsonProperty(value = "id")
    private Long entityId;
    private String token;
    private List<String> roles;
    private String name;
    private String email;

    private String file;

    private List<ResponseStatus> status;

    public UserApiResponse(Long entityId, String fileUrl, List<ResponseStatus> statuses){
        this.entityId = entityId;
        this.file = fileUrl;
        this.status = statuses;
    }

    public <T> UserApiResponse(Long id, List<ResponseStatus> statuses) {
        this.entityId = id;
        this.status = statuses;
    }
}