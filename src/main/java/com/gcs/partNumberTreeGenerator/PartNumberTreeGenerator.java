package com.gcs.partNumberTreeGenerator;

import com.gcs.partNumberTreeGenerator.model.ExcelPartNumberParentChild;
import com.gcs.partNumberTreeGenerator.model.ExcelContent;
import com.gcs.partNumberTreeGenerator.model.Node;
import com.gcs.partNumberTreeGenerator.model.PartNumber;
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

        Node<PartNumber> foundNode = findInNodes("4", trees);

        if(foundNode != null){
            drawTree(foundNode);
        }
    }

    private static void drawTree(Node<PartNumber> nodeToDraw) {
        TreeDrawer treeDrawer = new TreeDrawer(nodeToDraw);
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

    private static Node<PartNumber> findInNodes(String partNumberString, List<Node<PartNumber>> rootNode) {
        for (Node<PartNumber> partNumberNode : rootNode) {
            Node<PartNumber> foundNode = findInNode(partNumberString, partNumberNode);
            if (foundNode != null) {
                return partNumberNode;
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
