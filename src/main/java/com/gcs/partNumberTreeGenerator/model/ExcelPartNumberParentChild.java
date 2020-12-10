package com.gcs.partNumberTreeGenerator.model;

public class ExcelPartNumberParentChild {

    private final String child;

    private final String parent;

    public ExcelPartNumberParentChild(String parent, String child) {
        this.child = child;
        this.parent = parent;
    }

    public String getChild() {
        return child;
    }

    public String getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return "ExcelPartNumberParentChild{" +
                "parent='" + parent + '\'' +
                ", child='" + child + '\'' +
                '}';
    }
}
