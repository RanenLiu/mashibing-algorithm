package com.howliked.mashibing.algorithm.class09;

/**
 * 8. 给定两个可能有环也可能无环的单链表，头节点 head1 和 head2。
 * 请实现一个函数，如果两个链表相交，请返回相交的第一个节点；如果不相交返回 null
 *【要求】如果两个链表长度之和为 N，时间复杂度达到 O(N)，额外空间复杂度请达到O(1)
 * <p>
 * 步骤：
 * 1. 判断两个链表是否有环
 *    1. 如果都无环则是无环相交问题
 *    2. 如果都有环则是有环相交问题
 *        2.1. 入环前相交（head1 的入环节点 == head2 的入环节点）
 *        2.2. 入环后相交（head1 的入环节点 != head2 的入环节点）
 *        2.3. head1 的入环节点转一圈后没有遇到 head2 的入环节点，无相交
 *    3. 一个无环一个无环不可能相交
 */
public class Code05_FindFirstIntersectNode {

    /**
     * 返回两个链表的相交节点
     * @param head1 链表 1 头节点
     * @param head2 链表 2 头节点
     * @return ListNode 两个链表的相交点
     */
    public static ListNode getIntersectNode(ListNode head1, ListNode head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        // 第一步，首先看链表1和链表2是否有环，有则返回入环节点，否则返回空
        ListNode loop1 = getLoopNode(head1);
        ListNode loop2 = getLoopNode(head2);

        // 第二步，如果都没有环，无环相交问题
        if (loop1 == null && loop2 == null) {
            // 要么相交要么不相交
            return noLoop(head1, head2);
        // 第三步，都有环，有环相交问题
        } else if (loop1 != null && loop2 != null) {
            // 情况 1：入环前相交，有相同的入环节点
            // 情况 2：入环后相交，有不同的入环节点
            // 情况 3：有各自的环，不相交
            return bothNode(head1, loop1, head2, loop2);
        }
        // 第四步，一个有环一个无环不可能相交
        return null;
    }

    /**
     * 作用：判断一个链表有是否有环
     * 方法：使用快慢指针
     * 细节：
     *      设置快慢指针 fast 和 slow，fast 一次跳两个节点（head.next.next），slow 一次跳一个节点（head.next）
     *          - 如果一直跳，直当 fast == null 时说明没有环返回 null
     *          - 如果跳的过程中出现 fast == slow 说明有环，此时slow保持不动，fast 重新从 head 开始跳且速度变为 1，然后两个指针继续跳，
     *            等它们再相遇时 fast 或 slow 指向的节点即为入环节点，返回该节点
     * @param head 需要获取入环节点的链表
     * @return 链表的入环节点
     */
    private static ListNode getLoopNode(ListNode head) {
        // 如果 head 为空 / head.next 为空 / head.next.next 为空直接返回 null 因为不可能有环
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        ListNode slow = head.next;
        ListNode fast = head.next.next;
        while (slow != fast) {
            if (fast == null || fast.next == null) {
                return null;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        fast = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    /**
     * 作用：返回两个无环链表的相交节点，有则返回该节点，否则返回 null
     * 方法：直接分别遍历
     * 细节：定义一个 int 变量 n 用于记录链表长度
     *      定义指针 1 和指针 2，分别指向两个无环链表
     *          先遍历链表 1，当其下个节点不为 null 时，n++ 且指针 1 指向其下个节点。遍历完时得到链表 1 总长度 n
     *          再遍历链表 2，当其下个节点不为 null 时，n-- 且指针 2 指向其下个节点。遍历完时得到链表 1 和链表 2 的长度差 n
     *      执行完两个遍历后
     *          - 如果两个指针不相等说明两个链表不相交返回 null
     *          - 如果两个指针相等说明两个链表相交（一旦相交后续节点都相交不可能出现分叉情况因为只有一个 next 指针），
     *            将长链表给指针 1，短链表给指针 2
     *            让长链表指针先跳多出来的 n 步，然后两个指针在不相等时同时跳，一旦相等此时指针指向的就是两个链表相交的节点，跳出循环并返回
     * @param head1 链表 1 头节点
     * @param head2 链表 2 头节点
     * @return 无环结构的两个链表的相交点
     */
    private static ListNode noLoop(ListNode head1, ListNode head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        ListNode cur1 = head1;
        ListNode cur2 = head2;
        int n = 0;
        while (cur1.next != null) {
            n++;
            cur1 = cur1.next;
        }
        while (cur2.next != null) {
            n--;
            cur2 = cur2.next;
        }
        if (cur1 != cur2) {
            return null;
        }
        cur1 = n > 0 ? head1 : head2;
        cur2 = cur1 == head1 ? head2 : head1;
        n = Math.abs(n);
        while (n-- > 0) {
            cur1 = cur1.next;
        }

        while (cur1 != cur2) {
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1;
    }

    /**
     * 作用：当两个节点都有环的时候，返回首次相交的节点或 null
     * 方法：类别判断类似获取两个无环链表返回第一个相交节点
     * 细节：不存在一个链表有环一个链表无环相交的情况，这种情况返回 null
     *      已知两个链表都有环，分三种情况：相同的入环点、不同的入环点和不相交
     *          首先定义 int 变量 n 用于记录链表长度，最终表示两个链表的长度差
     *          1. 因为已知了各个链表的入环节点，所以分别定义 cur1 和 cur2 指针指向链表 1 和链表 2 的头节点
     *             - 先遍历链表 1，当 cur1.next != loop1 时，执行 n++ 和 cur1 = cur1.next。完成该遍历则得到链表 1 的长度 n
     *             - 再遍历链表 2，当 cur2.next != loop2 时，执行 n-- 和 cur2 = cur2.next。完成该遍历则得到链表 1 和链表 2 的长度差 n
     *             - 如果 loop1 == loop2，意味着链表 1 和链表 2 有相同的入环点，需要获取它们的第一个相交点
     *                  - 让 cur1 指向长链表的头，cur2 指向短链表的头
     *                  - 先让 cur1 跳 Math.abs(n) 步，然后在 cur1 != cur2 的时候一起按速度 1 向下跳，跳出循环时，cur1/cur2 指向的节
     *                    点就是两链表第一次相并的点
     *             - 如果 loop1 != loop2 时，让 loop1/loop2 用速度 1 向后跳，如果回到自身时没有遇到另一个 loop 说明两链表不相交；如果过
     *               程中遇到了另一个 loop，说明两个两环状链表相交，返回任意一个即可
     *
     * @param head1 链表 1 的头节点
     * @param loop1 链表 1 的入环节点
     * @param head2 链表 2 的头节点
     * @param loop2 链表 2 的入环节点
     * @return 返回相交节点
     */
    private static ListNode bothNode(ListNode head1, ListNode loop1, ListNode head2, ListNode loop2) {
        if (loop1 == loop2) {
            ListNode cur1 = head1;
            ListNode cur2 = head2;
            int n = 0;
            while (cur1 != loop1) {
                n++;
                cur1 = cur1.next;
            }
            while (cur2 != loop2) {
                n--;
                cur2 = cur2.next;
            }
            cur1 = n > 0 ? head1 : head2;
            cur2 = cur1 == head1 ? head2 : head1;
            n = Math.abs(n);
            while (n-- > 0) {
                cur1 = cur1.next;
            }
            while (cur1 != cur2) {
                cur1 = cur1.next;
                cur2 = cur2.next;
            }
            return cur1;
        } else {
            ListNode cur1 = loop1.next;
            while (cur1 != loop1) {
                if (cur1 == loop2) {
                    return loop1;
                }
                cur1 = cur1.next;
            }
            return null;
        }
    }


    public static void main(String[] args) {
        // 1->2->3->4->5->6->7->null
        ListNode head1 = new ListNode(1);
        head1.next = new ListNode(2);
        head1.next.next = new ListNode(3);
        head1.next.next.next = new ListNode(4);
        head1.next.next.next.next = new ListNode(5);
        head1.next.next.next.next.next = new ListNode(6);
        head1.next.next.next.next.next.next = new ListNode(7);

        // 0->9->8->6->7->null
        ListNode head2 = new ListNode(0);
        head2.next = new ListNode(9);
        head2.next.next = new ListNode(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectNode(head1, head2).value);

        // 1->2->3->4->5->6->7->4...
        head1 = new ListNode(1);
        head1.next = new ListNode(2);
        head1.next.next = new ListNode(3);
        head1.next.next.next = new ListNode(4);
        head1.next.next.next.next = new ListNode(5);
        head1.next.next.next.next.next = new ListNode(6);
        head1.next.next.next.next.next.next = new ListNode(7);
        head1.next.next.next.next.next.next = head1.next.next.next; // 7->4

        // 0->9->8->2...
        head2 = new ListNode(0);
        head2.next = new ListNode(9);
        head2.next.next = new ListNode(8);
        head2.next.next.next = head1.next; // 8->2
        System.out.println(getIntersectNode(head1, head2).value);

        // 0->9->8->6->4->5->6..
        head2 = new ListNode(0);
        head2.next = new ListNode(9);
        head2.next.next = new ListNode(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectNode(head1, head2).value);

    }

}
