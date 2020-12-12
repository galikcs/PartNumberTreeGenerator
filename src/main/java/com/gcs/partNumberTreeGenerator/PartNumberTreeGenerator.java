package com.gcs.partNumberTreeGenerator;

import com.gcs.partNumberTreeGenerator.model.*;
import com.gcs.partNumberTreeGenerator.service.ExcelReader;
import com.gcs.partNumberTreeGenerator.service.TreeDrawer;

import java.util.*;
import java.util.stream.Collectors;

public class PartNumberTreeGenerator {

    public static void main(String[] args) {
        // write your code here

        System.out.println("Hello");

        ExcelReader reader = new ExcelReader();
        ExcelContent excelData = reader.readFile("e:\\PartNumbers\\PartNumbers.xlsx");
        List<ExcelPartNumberParentChild> parentChildList = excelData.getParentChildList();

        Set<Node<PartNumber>> nodes = parentChildList.stream().map(PartNumberTreeGenerator::createNode).collect(Collectors.toSet());

        Map<String, Node<PartNumber>> partNumberStringToNode = new HashMap<>();
        for (Node<PartNumber> node : nodes) {
            partNumberStringToNode.put(node.getData().getPartNumber(), node);
        }

        List<Node<PartNumber>> trees = createTree(partNumberStringToNode, parentChildList);

        RootNodeWithNodeToOpen foundTreeWithNode = findInNodes("9", trees);

        if(foundTreeWithNode!=null && foundTreeWithNode.getRootNode() != null){
            drawTree(foundTreeWithNode.getRootNode(), foundTreeWithNode.getNodeToOpen());
        }
    }

    private static void drawTree(Node<PartNumber> rootNodeToDraw, Node<PartNumber> nodeToOpen) {
        TreeDrawer treeDrawer = new TreeDrawer(rootNodeToDraw, nodeToOpen);
        treeDrawer.drawTree();
    }

    private static List<Node<PartNumber>> createTree(Map<String, Node<PartNumber>> partNumberStringToNode, List<ExcelPartNumberParentChild> parentChildList) {

        List<Node<PartNumber>> mainList = new ArrayList<>();

        parentChildList.forEach(parentChild -> {
                    Node<PartNumber> childNode = partNumberStringToNode.get(parentChild.getChild());
                    if (parentChild.getParent() == null) {
                        mainList.add(childNode);
                    } else {
                        Node<PartNumber> parentNode = partNumberStringToNode.get(parentChild.getParent());
                        parentNode.addChild(childNode);
                    }

                }
        );

        return mainList;
    }

    private static Node<PartNumber> createNode(ExcelPartNumberParentChild parentChildLink) {

        String partNumberString = parentChildLink.getChild();

        PartNumber partNumber = new PartNumber(partNumberString, null);

        return new Node<>(partNumber);
    }

    private static RootNodeWithNodeToOpen findInNodes(String partNumberString, List<Node<PartNumber>> rootNode) {
        for (Node<PartNumber> partNumberNode : rootNode) {
            Node<PartNumber> foundNode = findInNode(partNumberString, partNumberNode);
            if (foundNode != null) {
                return new RootNodeWithNodeToOpen(partNumberNode, foundNode);
            }
        }
        return null;
    }

    private static Node<PartNumber> findInNode(String partNumberString, Node<PartNumber> rootNode) {

        if (rootNode.getData().getPartNumber().equals(partNumberString)) {
            return rootNode;
        } else {
            for (Node<PartNumber> child : rootNode.getChildren()) {
                Node<PartNumber> childResult = findInNode(partNumberString, child);
                if (childResult != null) {
                    return childResult;
                }
            }
        }
        return null;
    }

}
