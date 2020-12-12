package com.gcs.partNumberTreeGenerator.custom;

import com.gcs.partNumberTreeGenerator.model.Node;
import com.gcs.partNumberTreeGenerator.model.PartNumber;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class CustomTreeCellRenderer extends DefaultTreeCellRenderer {

    private final Node<PartNumber> nodeToHighlight;

    public CustomTreeCellRenderer(Node<PartNumber> nodeToHighLight){
        this.nodeToHighlight = nodeToHighLight;
    }


    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean sel, boolean exp, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, exp, leaf, row, hasFocus);

        // Assuming you have a tree of Strings
        PartNumber node = (PartNumber) ((DefaultMutableTreeNode) value).getUserObject();

        // If the node is a leaf and ends with "xxx"
        if (node.getPartNumber().equals(nodeToHighlight.getData().getPartNumber())) {
            // Paint the node in blue
            setForeground(new Color(200, 0 ,0));
        }

        return this;
    }
}
