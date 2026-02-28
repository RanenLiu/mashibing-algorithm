package com.howliked.mashibing.algorithm.class10;

import java.util.ArrayList;
import java.util.List;

/**
 * 递归方式遍历二叉树的前、中、后序
 */
public class Code02_RecursiveTraversalBT {

    public static void main(String[] args) {
        TreeNode root = TreeNode.getTreeNode();
        pre(root);
        System.out.println("二叉树的前序遍历:" + preNodeList);
        in(root);
        System.out.println("二叉树的中序遍历:" + inNodeList);
        post(root);
        System.out.println("二叉树的后序遍历:" + postNodeList);
    }

    private static final List<Integer> preNodeList = new ArrayList<>();
    private static final List<Integer> inNodeList = new ArrayList<>();
    private static final List<Integer> postNodeList = new ArrayList<>();

    /**
     * 在递归方法中递归左右子节点前打印（或执行操作）是前序遍历
     * @param treeNode 头节点
     */
    private static void pre(TreeNode treeNode) {
        if (treeNode == null) {
            return;
        }
        preNodeList.add(treeNode.value);
        pre(treeNode.left);
        pre(treeNode.right);
    }

    /**
     * 在递归方法中递归左右子节点中间打印（或执行操作）是中序遍历
     * @param treeNode 头节点
     */
    private static void in(TreeNode treeNode) {
        if (treeNode == null) {
            return;
        }
        in(treeNode.left);
        inNodeList.add(treeNode.value);
        in(treeNode.right);
    }

    /**
     * 在递归方法中递归左右子节点之后打印（或执行操作）是后序遍历
     * @param treeNode 头节点
     */
    private static void post(TreeNode treeNode) {
        if (treeNode == null) {
            return;
        }
        post(treeNode.left);
        post(treeNode.right);
        postNodeList.add(treeNode.value);
    }
}
