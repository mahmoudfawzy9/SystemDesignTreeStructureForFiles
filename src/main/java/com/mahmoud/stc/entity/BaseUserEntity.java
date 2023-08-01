package com.mahmoud.stc.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
public abstract class BaseUserEntity extends DefaultBusinessEntity<Long> {

    @Column(name = "email")
    private String email;

    @Column(name = "encrypted_password")
    private String encryptedPassword;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @Column(name = "reset_password_sent_at")
    private LocalDateTime resetPasswordSentAt;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "organization_id")
    private Long organizationId;

    @Column(name = "authentication_token")
    private String authenticationToken;

    @Column(name = "last_sign_in_at")
    private LocalDateTime lastSignInDate;

    @Column(name = "current_sign_in_at")
    private LocalDateTime currentSignInDate;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "sign_in_count")
    private int signInCount;

    @Column(name = "user_status")
    private Integer userStatus;

    @Column(name = "remember_created_at")
    @CreationTimestamp
    private LocalDateTime creationTime;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public LocalDateTime getResetPasswordSentAt() {
        return resetPasswordSentAt;
    }

    public void setResetPasswordSentAt(LocalDateTime resetPasswordSentAt) {
        this.resetPasswordSentAt = resetPasswordSentAt;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

    public LocalDateTime getLastSignInDate() {
        return lastSignInDate;
    }

    public void setLastSignInDate(LocalDateTime lastSignInDate) {
        this.lastSignInDate = lastSignInDate;
    }

    public LocalDateTime getCurrentSignInDate() {
        return currentSignInDate;
    }

    public void setCurrentSignInDate(LocalDateTime currentSignInDate) {
        this.currentSignInDate = currentSignInDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getSignInCount() {
        return signInCount;
    }

    public void setSignInCount(int signInCount) {
        this.signInCount = signInCount;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }
}
