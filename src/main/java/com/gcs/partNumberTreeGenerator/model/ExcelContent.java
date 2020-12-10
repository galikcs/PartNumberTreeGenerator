package com.gcs.partNumberTreeGenerator.model;

import java.util.List;

public class ExcelContent {

    private final List<ExcelPartNumberParentChild> parentChildList;


    public ExcelContent(List<ExcelPartNumberParentChild> parentChildList) {
        this.parentChildList = parentChildList;
    }

    public List<ExcelPartNumberParentChild> getParentChildList() {
        return parentChildList;
    }

}
