package com.gcs.partNumberTreeGenerator.service;

import com.gcs.partNumberTreeGenerator.model.Node;
import com.gcs.partNumberTreeGenerator.model.PartNumber;
import com.gcs.partNumberTreeGenerator.custom.CustomTreeCellRenderer;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.util.List;

public class TreeDrawer implements TreeSelectionListener {

    private final Node<PartNumber> nodeToDraw;
    private final Node<PartNumber> nodeToDisplay;
    private final JFrame frame;
    private JTree partNumberTree;
    private TreePath nodeToOpenTreePath;

    public TreeDrawer(Node<PartNumber> nodeToDraw, Node<PartNumber> nodeToOpen) {
        this.nodeToDraw = nodeToDraw;
        this.nodeToDisplay = nodeToOpen;
        frame = new JFrame("Partnumber Tree");
    }

    public void drawTree() {

        DefaultMutableTreeNode tree = createTreeNodes(nodeToDraw);

        partNumberTree = new JTree(tree);
        partNumberTree.setRowHeight(25);
        partNumberTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        partNumberTree.addTreeSelectionListener(this);
        if(nodeToOpenTreePath !=null){
            partNumberTree.expandPath(nodeToOpenTreePath);
        }

        frame.add(partNumberTree);
        partNumberTree.setCellRenderer(new CustomTreeCellRenderer(nodeToDisplay));

        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


    private DefaultMutableTreeNode createTreeNodes(Node<PartNumber> rootNode) {
        if (rootNode != null) {
            if (rootNode.getChildren() != null) {
                List<Node<PartNumber>> children = rootNode.getChildren();
                DefaultMutableTreeNode rootTreeNode = new DefaultMutableTreeNode(rootNode.getData());
                return addChildToParent(rootTreeNode, children);
            } else {
                return new DefaultMutableTreeNode(rootNode.getData());
            }
        }

        return null;
    }

    private DefaultMutableTreeNode addChildToParent(DefaultMutableTreeNode rootTreeNode, List<Node<PartNumber>> childrenNodes) {
        for (Node<PartNumber> childNode : childrenNodes) {
            DefaultMutableTreeNode childTreeNode = new DefaultMutableTreeNode(childNode.getData());
            rootTreeNode.add(childTreeNode);
            if(childNode == nodeToDisplay){
                //nodeToOpenTreePath = new TreePath(childTreeNode.getPath());
                nodeToOpenTreePath = new TreePath(rootTreeNode.getPath());
            }
            if (childNode.getChildren() != null && childNode.getChildren().size() > 0) {
                addChildToParent(childTreeNode, childNode.getChildren());
            }
        }
        return rootTreeNode;
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                partNumberTree.getLastSelectedPathComponent();
        PartNumber selectedPartNumber = (PartNumber)node.getUserObject();
        System.out.println(selectedPartNumber.getStatus());
    }
}
