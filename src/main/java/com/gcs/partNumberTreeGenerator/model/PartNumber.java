package com.gcs.partNumberTreeGenerator.model;

import java.util.Objects;

public class PartNumber {

    private final String partNumber;

    private final Integer status;

    public PartNumber(String partNumber, Integer status){
        this.partNumber = partNumber;
        this.status = status;
    }

    public String getPartNumber() {
        return partNumber;
    }


    public Integer getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartNumber that = (PartNumber) o;
        return partNumber.equals(that.partNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(partNumber);
    }

    @Override
    public String toString() {
        return partNumber;
    }
}
