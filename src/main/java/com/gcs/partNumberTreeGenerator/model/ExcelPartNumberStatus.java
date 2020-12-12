package com.gcs.partNumberTreeGenerator.model;

public class ExcelPartNumberStatus {

    private final String partNumber;

    private final String status;

    public ExcelPartNumberStatus(String partNumber, String status) {
        this.partNumber = partNumber;
        this.status = status;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public String getStatus() {
        return status;
    }
}
