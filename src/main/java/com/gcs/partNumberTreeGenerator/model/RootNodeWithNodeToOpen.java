package com.gcs.partNumberTreeGenerator.model;

public class RootNodeWithNodeToOpen {

    private final Node<PartNumber> rootNode;

    private final Node<PartNumber> nodeToOpen;

    public RootNodeWithNodeToOpen(Node<PartNumber> rootNode, Node<PartNumber> nodeToOpen) {
        this.rootNode = rootNode;
        this.nodeToOpen = nodeToOpen;
    }

    public Node<PartNumber> getRootNode() {
        return rootNode;
    }

    public Node<PartNumber> getNodeToOpen() {
        return nodeToOpen;
    }
}
