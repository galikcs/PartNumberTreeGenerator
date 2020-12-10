package com.gcs.partNumberTreeGenerator.model;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {

    private T data;

    private List<Node<T>> children;

    private boolean isRoot;

    public Node(T data){
        this.data = data;
        this.children = new ArrayList<>();
    }

    public void addChild(Node<T> child){
        this.children.add(child);
    }

    public T getData(){
        return data;
    }

    public List<Node<T>> getChildren() {
        return children;
    }
}

