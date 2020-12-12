package com.gcs.partNumberTreeGenerator.model;

import java.util.List;

public class ExcelContent {

    private final List<ExcelPartNumberParentChild> parentChildList;

    private final List<ExcelPartNumberStatus> partNumberStatuses;

    public ExcelContent(List<ExcelPartNumberParentChild> parentChildList, List<ExcelPartNumberStatus> partNumberStatuses) {
        this.parentChildList = parentChildList;
        this.partNumberStatuses = partNumberStatuses;
    }

    public List<ExcelPartNumberParentChild> getParentChildList() {
        return parentChildList;
    }

    public List<ExcelPartNumberStatus> getPartNumberStatuses() {
        return partNumberStatuses;
    }
}
