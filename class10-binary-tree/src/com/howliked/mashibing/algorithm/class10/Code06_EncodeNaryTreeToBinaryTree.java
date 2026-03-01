package com.howliked.mashibing.algorithm.class10;

import java.util.ArrayList;
import java.util.List;

/**
 * vip题目: https://leetcode.com/problems/encode-n-ary-tree-to-binary-tree
 * 解题思路:
 * 将多叉树序列化为二叉树,并能将二叉树反序列化成多叉树
 * encode():将多叉树序列化为二叉树。二叉树结构: 当前树的左节点为第一个子节点,其余子节点均为子节点的右子节点
 */
public class Code06_EncodeNaryTreeToBinaryTree {
    // 提交时不要提交这个类
    public static class Node {
        public int val;
        public List<Node> children;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    // 提交时不要提交这个类
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }


    // 只提交这个类即可
    static class Codec {

        // Encodes an n-ary tree to a binary tree.
        public TreeNode encode(Node root) {
            if (root == null) {
                return null;
            }
            // 把多叉树的根节点转为二叉树的头节点
            TreeNode node = new TreeNode(root.val);
            // 用多叉树的所有子节点构建二叉树的左子树
            node.left = en(root.children);
            return node;
        }

        /**
         * 作用：递归用多个子节点构建二叉树（深度优先遍历）
         * 注意：
         *      1. 必须要有一个 head 表示遍历的节点中的第一个作为二叉树左子节点
         *      2. 必须要有一个 cur 指向正在处理的节点（转换为二叉树的）
         * @param children 多叉树根节点的所有子节点
         * @return 用多叉树转成的二叉树头节点
         */
        private TreeNode en(List<Node> children) {
            TreeNode head = null; // 要构建的二叉树的第一个节点
            TreeNode cur = null; // 声明正在处理的节点，会指向每一个正在遍历的节点，每一个节点的子节点只会有一个
            // ****** 遍历所有子节点。当 children 为 null/空时，跳过循环直接返回 head（null）给上层调用 en 的方法
            for (Node child : children) {
                TreeNode node = new TreeNode(child.val);
                // 如果 head 为 null，表示刚开始处理一个节点的所有子节点，需要将第一个节点转为二叉树节点
                if (head == null) {
                    head = new TreeNode(node.val);
                // 否则将刚转换的二叉树节点设置为
                } else {
                    cur.right = node;
                }
                // 每遍历一个节点，将其设置为正在处理的节点，然后把该节点的所有子节点转换为二叉树挂在它的左子节点上
                cur = node;
                // ****** 当 child.children 为 null/空时，en(child.children) 返回 null 所以 cur.left 被设置为 null
                cur.left = en(child.children);
            }
            return head;
        }

        // Decodes your binary tree to an n-ary tree.
        public Node decode(TreeNode root) {
            // 如果 root 为 null，直接返回 null
            if (root == null) {
                return null;
            }
            // 如果二叉树头节点不为空，则以 root 值为多叉树头节点的值为值，二叉树 root 的左子树反序列成新生成的多叉树头节点的子节点
            return new Node(root.val, de(root.left));
        }

        /**
         * 作用：把给定的二叉树节点反序列化成一个多叉树节点列表，结果会用来和一个值共同构建一个多叉树
         * @param root 需要反序列的二叉树节点
         * @return 返回一个多叉树列表
         */
        private List<Node> de(TreeNode root) {
            List<Node> children = new ArrayList<>(); // 声明一个多叉树节点列表用来返回多叉树节点列表
            // 当传进来的二叉树节点不为空的时候，把该节点和它的右子节点（反复递归）放入前边定义的多叉树节点列表
            while (root != null) {
                // 深度优先遍历，只有把底层的左子树构建完多叉树之后，才会进行顶层的多叉树构建
                Node node = new Node(root.val, de(root.left));
                children.add(node);
                root = root.right;
            }
            // 返回多叉树节点列表。如果 root 为 null 直接返回空列表（每个多叉树节点都有这个列表且可以为空）
            return children;
        }
    }
}
