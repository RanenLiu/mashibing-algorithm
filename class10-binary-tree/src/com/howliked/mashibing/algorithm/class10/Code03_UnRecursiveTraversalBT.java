package com.howliked.mashibing.algorithm.class10;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 非递归方式 —— 二叉树的前、中、后序遍历
 */
public class Code03_UnRecursiveTraversalBT {

    public static void main(String[] args) {
        TreeNode root = TreeNode.getTreeNode();
        System.out.println("二叉树的前序遍历:" + preOrder(root));
        System.out.println("二叉树的中序遍历:" + inOrder(root));
        System.out.println("二叉树的后序遍历:" + postOrder(root));
    }


    /**
     * 前序遍历
     * 过程：
     *      1. 头节点入栈
     *      2. 把头节点出栈，如果有右子节点入栈，右子节点入栈；如果还有左子节点，左子节点入栈
     *      3. 把左子节点出栈，若左子节点有入子节点，右子节点入栈；如果左子节点还有左子节点，左左子节点入栈
     *      4. 依次执行，栈中节点出栈的顺序就是前序遍历的顺序
     *      5. 注意：每一次出栈都是栈不为空的候进行
     *
     * @param root 头节点
     * @return 前序遍历的结果
     */
    private static List<Integer> preOrder(TreeNode root) {
        if (root == null) {
            return null;
        }
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.add(node.value);
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
        return result;
    }

    /**
     * 中序遍历
     * 过程：
     *      需要一个栈
     *      1. 直接从头节点开始前遍历所有左子节点，遇到就入栈
     *      2. 当遍历到的左子节点为 null 时，栈顶元素出栈并将其设置为正在遍历的节点
     *      3. 当栈顶元素出栈时，把其存在中序遍历结果或者执行实际操作，然后查看该元素的右子节点
     *      4. 循环执行第 2 和 3 步，当所有元素出栈跳出循还后，即为中序遍历结果
     *
     * @param cur 头节点
     * @return 中序遍历的结果
     */
    private static List<Integer> inOrder(TreeNode cur) {
        if (cur == null) {
            return null;
        }
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        while (cur != null || !stack.isEmpty()) {
            // 只要 cur 不为空就先查看其左子节占，有则入栈
            if (cur != null) {
                stack.push(cur);
                cur = cur.left;
            } else {
                // 只要出栈的时候就加入结果，然后查看右子节点
                cur = stack.pop();
                result.add(cur.value);
                cur = cur.right;
            }
        }
        return result;
    }

    /**
     * 后序遍历
     * 过程（类似于前序遍历），需要额外的栈两个栈：
     *      1. 头节点入栈 1
     *      2. 把头节点出栈 1，入栈 2；如果有左子节点则左子节点入栈 1；如果还有右子节点，右子节点入栈 1
     *      3. 把右子节点出栈 1，入栈 2；若右子节点有左子节点，右左子节点入栈；如果右子节点还有右子节点，右右子节点入栈
     *      4. 依次执行，当栈 1 为空时，栈 2 中元素出栈，顺序就是后序遍历的顺序
     *      5. 注意：每一次出栈都是栈不为空的候进行
     * @param root 头节点
     * @return 返回遍历结果
     */
    private static List<Integer> postOrder(TreeNode root) {
        if (root == null) {
            return null;
        }
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        stack1.push(root);
        while (!stack1.isEmpty()) {
            TreeNode node = stack1.pop();
            stack2.push(node);
            if (node.left != null) {
                stack1.push(node.left);
            }
            if (node.right != null) {
                stack1.push(node.right);
            }
        }

        while (!stack2.isEmpty()) {
            result.add(stack2.pop().value);
        }
        return result;
    }


}
