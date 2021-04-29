package com.example.crudwithvaadin.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

@Entity
@Table(name = "energy_output")
public class EnergyOutput implements Serializable {
    private static final long serialVersionUID = 5686845785686592876L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotNull
    private String transactionId;
    private String deviceType;
    @NotNull
    private LocalDateTime deviceDateTime;
    private String transactionConfig;
    private String transactionResponseCode;
    private String inputReading;
    private Integer calculatedValue;
    private String deviceId;
    private Integer dataLength;
    private String deviceIndex;
    private LocalDateTime createdAt;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime updatedAt;

    public EnergyOutput() {
    }

    @PrePersist
    private void prePersist() {
        createdBy = "CREATOR";
        createdAt = LocalDateTime.now();
        updatedBy = "CREATOR";
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    private void preUpdate() {
        updatedBy = "UPDATER";
        updatedAt = LocalDateTime.now();
    }

    public EnergyOutput(String id, @NotNull String transactionId, String deviceType, @NotNull LocalDateTime deviceDateTime, String transactionConfig, String transactionResponseCode, String inputReading, Integer calculatedValue, String deviceId, Integer dataLength, String deviceIndex, LocalDateTime createdAt, String createdBy, String updatedBy, LocalDateTime updatedAt) {
        this.id = id;
        this.transactionId = transactionId;
        this.deviceType = deviceType;
        this.deviceDateTime = deviceDateTime;
        this.transactionConfig = transactionConfig;
        this.transactionResponseCode = transactionResponseCode;
        this.inputReading = inputReading;
        this.calculatedValue = calculatedValue;
        this.deviceId = deviceId;
        this.dataLength = dataLength;
        this.deviceIndex = deviceIndex;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public LocalDateTime getDeviceDateTime() {
        return deviceDateTime;
    }

    public void setDeviceDateTime(LocalDateTime deviceDateTime) {
        this.deviceDateTime = deviceDateTime;
    }

    public String getTransactionConfig() {
        return transactionConfig;
    }

    public void setTransactionConfig(String transactionConfig) {
        this.transactionConfig = transactionConfig;
    }

    public String getTransactionResponseCode() {
        return transactionResponseCode;
    }

    public void setTransactionResponseCode(String transactionResponseCode) {
        this.transactionResponseCode = transactionResponseCode;
    }

    public String getInputReading() {
        return inputReading;
    }

    public void setInputReading(String inputReading) {
        this.inputReading = inputReading;
    }

    public Integer getCalculatedValue() {
        return calculatedValue;
    }

    public void setCalculatedValue(Integer calculatedValue) {
        this.calculatedValue = calculatedValue;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getDataLength() {
        return dataLength;
    }

    public void setDataLength(Integer dataLength) {
        this.dataLength = dataLength;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeviceIndex() {
        return deviceIndex;
    }

    public void setDeviceIndex(String deviceIndex) {
        this.deviceIndex = deviceIndex;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, JSON_STYLE)
                .append("id", id)
                .append("transactionId", transactionId)
                .append("deviceType", deviceType)
                .append("deviceDateTime", deviceDateTime)
                .append("transactionConfig", transactionConfig)
                .append("transactionResponseCode", transactionResponseCode)
                .append("inputReading", inputReading)
                .append("calculatedValue", calculatedValue)
                .append("deviceId", deviceId)
                .append("deviceIndex", deviceIndex)
                .append("dataLength", dataLength)
                .append("createdAt", createdAt)
                .append("createdBy", createdBy)
                .append("updatedBy", updatedBy)
                .append("updatedAt", updatedAt)
                .toString();
    }
}
