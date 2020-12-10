package com.gcs.partNumberTreeGenerator.service;

import com.gcs.partNumberTreeGenerator.model.Node;
import com.gcs.partNumberTreeGenerator.model.PartNumber;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.util.List;

public class TreeDrawer implements TreeSelectionListener {

    private final Node<PartNumber> nodeToDraw;
    private JFrame frame;
    private JTree jt;

    public TreeDrawer(Node<PartNumber> nodeToDraw) {
        this.nodeToDraw = nodeToDraw;
        frame = new JFrame();
    }

    public void drawTree() {

        DefaultMutableTreeNode tree = createTreeNodes(nodeToDraw);

        jt = new JTree(tree);
        jt.setRowHeight(25);
        jt.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        jt.addTreeSelectionListener(this);

        frame.add(jt);

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
            if (childNode.getChildren() != null && childNode.getChildren().size() > 0) {
                addChildToParent(childTreeNode, childNode.getChildren());
            }
        }
        return rootTreeNode;
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                jt.getLastSelectedPathComponent();
        PartNumber selectedPartNumber = (PartNumber)node.getUserObject();
        System.out.println(selectedPartNumber.getStatus());
    }
}
