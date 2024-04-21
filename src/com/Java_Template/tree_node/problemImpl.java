package com.Java_Template.tree_node;

import java.util.*;

/**
 * 算法：二叉树、N叉树、先序遍历、中序遍历、后序遍历、迭代实现、前序遍历
 */
public class problemImpl implements problem{
    // 104. 二叉树的最大深度
    @Override
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }


    // bfs写法
    public int maxDepthBFS(TreeNode root) {
        int ans = 0;
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int currentLevelSize = queue.size();
            for (int i = 1; i <= currentLevelSize; ++i) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            ans++; // 数层数就行了
        }
        return ans;
    }

    //100. 相同的树
    @Override
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        } else if (p == null || q == null) {
            return false;
        } else if (p.val != q.val) {
            return false;
        }
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    // bfs写法
    public boolean isSameTreeBFS(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        } else if (p == null || q == null) {
            return false;
        }
        Queue<TreeNode> queue1 = new LinkedList<TreeNode>();
        Queue<TreeNode> queue2 = new LinkedList<TreeNode>();
        queue1.offer(p);
        queue2.offer(q);
        while (!queue1.isEmpty() && !queue2.isEmpty()) {
            TreeNode node1 = queue1.poll();
            TreeNode node2 = queue2.poll();
            if (node1.val != node2.val) {
                return false;
            }
            TreeNode left1 = node1.left, right1 = node1.right, left2 = node2.left, right2 = node2.right;
            // (left1 == null ^ left2 == null)：这是要检查的条件。它使用^（异或）符号，该符号在许多编程语言中表示按位异或运算符。该条件在以下情况为真：要么left1为null且left2不为null，要么left2为null且left1不为null
            if (left1 == null ^ left2 == null) {
                return false;
            }
            if (right1 == null ^ right2 == null) {
                return false;
            }
            if (left1 != null) {
                queue1.offer(left1);
            }
            if (right1 != null) {
                queue1.offer(right1);
            }
            if (left2 != null) {
                queue2.offer(left2);
            }
            if (right2 != null) {
                queue2.offer(right2);
            }
        }
        return queue1.isEmpty() && queue2.isEmpty();
    }

    // 226. 翻转二叉树
    @Override
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return root;
        }
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        root.left = right;
        root.right = left;
        return root;
    }
    // bfs
    public TreeNode invertTree1(TreeNode root) {
        if (root == null) {
            return root;
        }
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            TreeNode t;
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
            t = node.left;
            node.left = node.right;
            node.right = t;
        }
        return root;
    }

    // 101. 对称二叉树
    public boolean isSymmetric(TreeNode root) {
        return checkIsSymmetric(root,root);
    }
    private boolean checkIsSymmetric(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        return p.val == q.val && checkIsSymmetric(p.left, q.right) && checkIsSymmetric(p.right, q.left);
    }

    // bfs
    public boolean isSymmetric1(TreeNode root) {
        if(root==null || (root.left==null && root.right==null)) {
            return true;
        }
        //用队列保存节点
        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
        //将根节点的左右孩子放到队列中
        queue.add(root.left);
        queue.add(root.right);
        while(queue.size()>0) {
            //从队列中取出两个节点，再比较这两个节点
            TreeNode left = queue.poll();
            TreeNode right = queue.poll();
            //如果两个节点都为空就继续循环，两者有一个为空就返回false
            if(left==null && right==null) {
                continue;
            }
            if(left==null || right==null) {
                return false;
            }
            if(left.val!=right.val) {
                return false;
            }
            //将左节点的左孩子， 右节点的右孩子放入队列
            queue.add(left.left);
            queue.add(right.right);
            //将左节点的右孩子，右节点的左孩子放入队列
            queue.add(left.right);
            queue.add(right.left);
        }
        return true;
    }

    // 105. 从前序与中序遍历序列构造二叉树
    private Map<Integer, Integer> map;

    public TreeNode myBuildTree(int[] preorder, int root, int left, int right) {
        if (left > right) {
            return null;
        }
        int index = map.get(preorder[root]);
        TreeNode node = new TreeNode(preorder[root]);
        node.left = myBuildTree(preorder, root + 1, left, index - 1);
        // `index - left + root + 1`含义为在preorder中node的右子树根节点的索引，其中index- left：node的左子树的节点个数；root就是当前节点node在前序preorder中的位置。
        node.right = myBuildTree(preorder, index - left + root + 1, index + 1, right);
        return node;
    }

    @Override
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int n = preorder.length;
        // 构造哈希映射，帮助我们快速定位根节点
        map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.put(inorder[i], i);
        }
        return myBuildTree(preorder, 0, 0, n - 1);
    }

    // 106. 从中序与后序遍历序列构造二叉树
    @Override
    public TreeNode buildTree1(int[] inorder, int[] postorder) {
        map = new HashMap<>();
        int n = inorder.length;
        for (int i = 0; i < n; i++) {
            map.put(inorder[i], i);
        }
        return myBuildTree1(postorder, n - 1, 0, n - 1);
    }

    private TreeNode myBuildTree1(int[] postorder, int root, int left, int right) {
        if (left > right) {
            return null;
        }
        int index = map.get(postorder[root]);
        TreeNode node = new TreeNode(postorder[root]);
        // root - (right - index) - 1
        // right - index:右子树的长度
        node.left = myBuildTree1(postorder, root - right + index - 1, left, index - 1);
        node.right = myBuildTree1(postorder, root - 1, index + 1, right);
        return node;
    }

    int[] pre, post;
    // 889. 根据前序和后序遍历构造二叉树
    @Override
    public TreeNode constructFromPrePost(int[] pre, int[] post) {
        this.pre = pre;
        this.post = post;
        return make(0, 0, pre.length);
    }

    // (i0, i1, N) 指的是 pre[i0:i0+N], post[i1:i1+N].
    public TreeNode make(int i0, int i1, int N) {
        // 如果节点数量为0，返回null
        if (N == 0) return null;
        // 创建根节点，值为先序遍历数组的第一个元素
        TreeNode root = new TreeNode(pre[i0]);

        // 如果节点数量为1，直接返回根节点
        if (N == 1) return root;
        int L = 1;
        // 寻找左子树的根节点在后序遍历数组中的位置
        for (; L < N; ++L)
            if (post[i1 + L - 1] == pre[i0 + 1])
                break;
        // 递归构建左子树和右子树
        root.left = make(i0 + 1, i1, L);
        root.right = make(i0 + L + 1, i1 + L, N - 1 - L);
        return root;
    }

    // 117. 填充每个节点的下一个右侧节点指针 II
    @Override
    public Node connect(Node root){
        if (root == null) {
            return root;
        }
        LinkedList<Node> queue = new LinkedList<Node>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            Node last = null;
            for (int i = 0; i < size; i++) {
                Node node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
                if (i != 0) {
                    last.next = node;
                }
                last = node;
            }
        }
        return root;
    }

    // bfs 空间O(1)
    //从第一层开始（第一层只有一个 root节点），每次循环：
    //遍历当前层的链表节点，通过节点的 left和 right得到下一层的节点。
    //把下一层的节点从左到右连接成一个链表。
    //拿到下一层链表的头节点，进入下一轮循环。
    public Node connect1(Node root) {
        Node dummy = new Node();
        Node cur = root;
        while (cur != null) {
            dummy.next = null;
            Node nxt = dummy; // 下一层的链表,因为nxt的指针在变，所以dummy->next一直在更新
            while (cur != null) { // 遍历当前层的链表
                if (cur.left != null) {
                    nxt.next = cur.left; // 下一层的相邻节点连起来
                    nxt = cur.left;
                }
                if (cur.right != null) {
                    nxt.next = cur.right; // 下一层的相邻节点连起来
                    nxt = cur.right;
                }
                cur = cur.next; // 当前层链表的下一个节点
            }
            cur = dummy.next; // 下一层链表的头节点
        }
        return root;
    }

    // 114. 二叉树展开为链表
    // 使用bfs与dfs的模板可以很容易得到，不过空间复杂度为O(n), 这里写一个空间复杂度为O(1)的写法
    @Override
    public void flatten(TreeNode root) {
        TreeNode cur = root;
        while (cur != null) {
            if (cur.left != null) {
                TreeNode next = cur.left;
                TreeNode pre = next;
                while (pre.right != null) {
                    pre = pre.right;
                }
                pre.right = cur.right;
                cur.left = null;
                cur.right = next;
            }
            cur = cur.right;
        }
    }

    // 112. 路径总和  dfs
    @Override
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        if (root.left == null && root.right == null) {
            return root.val == targetSum;
        }
        return hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right, targetSum - root.val);
    }

    // bfs  使用一个Queue<Integer> queVal = new LinkedList<Integer>();来保存每一条路径的和
    public boolean hasPathSumBFS(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }
        Queue<TreeNode> queNode = new LinkedList<TreeNode>();
        Queue<Integer> queVal = new LinkedList<Integer>();
        queNode.offer(root);
        queVal.offer(root.val);
        while (!queNode.isEmpty()) {
            TreeNode now = queNode.poll();
            int temp = queVal.poll();
            if (now.left == null && now.right == null) {
                if (temp == sum) {
                    return true;
                }
                continue;
            }
            if (now.left != null) {
                queNode.offer(now.left);
                queVal.offer(now.left.val + temp);
            }
            if (now.right != null) {
                queNode.offer(now.right);
                queVal.offer(now.right.val + temp);
            }
        }
        return false;
    }

    // 129. 求根节点到叶节点数字之和  dfs
    @Override
    public int sumNumbers(TreeNode root) {
        return sumNumbersDfs(root, 0);
    }
    private int sumNumbersDfs(TreeNode root, int prevSum) {
        if (root == null) {
            return 0;
        }
        int sum = prevSum * 10 + root.val;
        if (root.left == null && root.right == null) {
            return sum;
        }else{
            return sumNumbersDfs(root.left, sum) + sumNumbersDfs(root.right, sum);
        }
    }
    // bfs
    public int sumNumbersBFS(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int sum = 0;
        Queue<TreeNode> nodeQueue = new LinkedList<TreeNode>();
        Queue<Integer> numQueue = new LinkedList<Integer>();
        nodeQueue.offer(root);
        numQueue.offer(root.val);
        while (!nodeQueue.isEmpty()) {
            TreeNode node = nodeQueue.poll();
            int num = numQueue.poll();
            TreeNode left = node.left, right = node.right;
            if (left == null && right == null) {
                sum += num;
            } else {
                if (left != null) {
                    nodeQueue.offer(left);
                    numQueue.offer(num * 10 + left.val);
                }
                if (right != null) {
                    nodeQueue.offer(right);
                    numQueue.offer(num * 10 + right.val);
                }
            }
        }
        return sum;
    }

    // 124. 二叉树中的最大路径和
    private int maxPathSum_ans = Integer.MIN_VALUE;
    @Override
    public int maxPathSum(TreeNode root) {
        maxPathSumDFS(root);
        return maxPathSum_ans;
    }
    private int maxPathSumDFS(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = maxPathSumDFS(root.left), right = maxPathSumDFS(root.right);
        int t = root.val;
        if (left >= 0) {
            t += left;
        }
        if (right >= 0) {
            t += right;
        }
        maxPathSum_ans = Math.max(maxPathSum_ans, t); // 这里是把当前节点当根节点的情况，因为t同时加入了left与right
        return Math.max(root.val, Math.max(left, right) + root.val); // 选择一条分支加入
    }

    // 222. 完全二叉树的节点个数
    @Override
    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return countNodes(root.left) + countNodes(root.right) + 1;
    }
    // 时间复杂度：O(logn*logn) 二分查找 + 位运算(暂时不会)
    public int countNodes1(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int level = 0;
        TreeNode node = root;
        while (node.left != null) {
            level++;
            node = node.left;
        }
        int low = 1 << level, high = (1 << (level + 1)) - 1;
        while (low < high) {
            int mid = (high - low + 1) / 2 + low;
            if (exists(root, level, mid)) {
                low = mid;
            } else {
                high = mid - 1;
            }
        }
        return low;
    }
    public boolean exists(TreeNode root, int level, int k) {
        int bits = 1 << (level - 1);
        TreeNode node = root;
        while (node != null && bits > 0) {
            if ((bits & k) == 0) {
                node = node.left;
            } else {
                node = node.right;
            }
            bits >>= 1;
        }
        return node != null;
    }

    // 236. 二叉树的最近公共祖先
    @Override
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 后序遍历
        if (root == null || root == p || root == q) {
            return root;
        }
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        // 如果left 和 right都不为空，说明此时root就是最近公共节点
        // 如果left为空，right不为空，就返回right，说明目标节点是通过right返回的，反之亦然。
        if (left != null && right != null) {
            return root;
        }
        if (left == null) {
            return right;
        }
        return left;
    }

    // 235. 二叉搜索树的最近公共祖先
    @Override
    public TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q) {
        if (p.val < root.val && q.val < root.val) {
            return lowestCommonAncestor1(root.left, p, q);
        }
        if (p.val > root.val && q.val > root.val) {
            return lowestCommonAncestor1(root.right, p, q);
        }
        return root;
    }


    // 199. 二叉树的右视图
    // bfs容易想到，dfs就不容易了
    // 思路： 我们按照 「根结点 -> 右子树 -> 左子树」 的顺序访问，就可以保证每层都是最先访问最右边的节点的。
    //
    //（与先序遍历 「根结点 -> 左子树 -> 右子树」 正好相反，先序遍历每层最先访问的是最左边的节点）
    private List<Integer> rightSideViewAns = new ArrayList<>();
    @Override
    public List<Integer> rightSideView(TreeNode root){
        rightSideViewDFS(root, 0);
        return rightSideViewAns;
    }

    private void rightSideViewDFS(TreeNode root, int depth) {
        if (root == null) {
            return;
        }
        if (depth == rightSideViewAns.size()) {
            rightSideViewAns.add(root.val);
        }
        rightSideViewDFS(root.right, depth + 1);
        rightSideViewDFS(root.left, depth + 1);
    }

    // bfs
    public List<Integer> rightSideViewBFS(TreeNode root){
        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
        ArrayList<Integer> ans = new ArrayList<>();
        if (root == null) {
            return ans;
        }
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
                if (i == size - 1) {
                    ans.add(node.val);
                }
            }
        }
        return ans;
    }

    // 637. 二叉树的层平均值 bfs
    public List<Double> averageOfLevels(TreeNode root) {
        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
        ArrayList<Double> ans = new ArrayList<>();
        if (root == null) {
            return ans;
        }
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            double total = 0;
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
                total+=node.val;
            }
            ans.add(total / size);
        }
        return ans;
    }
    // dfs (有点麻烦了，该题推荐使用bfs)
    List<Double> res;
    List<Integer> countList;
    public List<Double> averageOfLevels1(TreeNode root) {
        res = new ArrayList<>();
        countList = new ArrayList<>();
        if (root == null) {
            return res;
        }
        dfs(root, 0);
        for(int i = 0; i < res.size(); i++) {
            res.set(i, res.get(i)/countList.get(i));
        }
        return res;
    }

    private void dfs(TreeNode root, int level) {
        if (root == null) {
            return;
        }
        if (res.size() == level) {
            res.add(Double.valueOf(root.val));
            countList.add(1);
        }else{
            Double v = res.get(level);
            res.set(level, v+root.val);
            Integer i = countList.get(level);
            countList.set(level,i+1);
        }
        dfs(root.left, level+1);
        dfs(root.right, level+1);
    }

    // 103. 二叉树的锯齿形层序遍历  bfs
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        ArrayList<List<Integer>> ans = new ArrayList<>();
        if (root == null) {
            return ans;
        }
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean flag = true; // 正序
        while (!queue.isEmpty()) {
            int size = queue.size();
            ArrayList<Integer> t = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
                t.add(node.val);
            }
            if (!flag) {
                Collections.reverse(t);
            }
            ans.add(t);
            flag = !flag;
        }
        return ans;
    }


    // 530. 二叉搜索树的最小绝对差 dfs
    int ans;
    int pre1;
    public int getMinimumDifference(TreeNode root) {
        ans = Integer.MAX_VALUE;
        pre1 = -1;
        dfs(root);
        return ans;
    }

    private void dfs(TreeNode root) {
        if (root == null) {
            return;
        }
        dfs(root.left);
        if (pre1 == -1) {
            pre1 = root.val;
        }else{
            ans = Math.min(ans, root.val - pre1);
            pre1 = root.val;
        }
        dfs(root.right);
    }

    // 230. 二叉搜索树中第K小的元素
    // 简单写法
    List<Integer> res1;
    public int kthSmallest(TreeNode root, int k) {
        res1 = new ArrayList<Integer>();
        inorder(root, res1);
        return res1.get(k - 1);
    }
    public void inorder(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }
        inorder(root.left, res);
        res.add(root.val);
        inorder(root.right, res);
    }

    // 最好的解法，迭代
    public int kthSmallest1(TreeNode root, int k) {
        Stack<TreeNode> stack = new Stack<>();

        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }

            root = stack.pop();
            --k;
            if (k == 0) {
                break;
            }

            root = root.right;
        }

        return root.val;
    }

    // 98. 验证二叉搜索树  中序遍历
    long prev = Long.MIN_VALUE;
    @Override
    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }
        boolean left = isValidBST(root.left);
        if (prev == Long.MIN_VALUE) {
            prev = root.val;
        } else {
            if (root.val <= prev) {
                return false;
            }
            prev = root.val;
        }
        boolean right = isValidBST(root.right);
        return left && right;
    }

    public boolean isValidBST1(TreeNode root) {
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    public boolean isValidBST(TreeNode node, long lower, long upper) {
        if (node == null) {
            return true;
        }
        if (node.val <= lower || node.val >= upper) {
            return false;
        }
        return isValidBST(node.left, lower, node.val) && isValidBST(node.right, node.val, upper);
    }

    // LCR 047. 二叉树剪枝
    @Override
    public TreeNode pruneTree(TreeNode root) {
        if (root == null) {
            return root;
        }
        root.left = pruneTree(root.left);
        root.right = pruneTree(root.right);
        if (root.left == null && root.right == null && root.val == 0) {
            return null;
        }
        return root;
    }


    // 2641. 二叉树的堂兄弟节点 II
    @Override
    public TreeNode replaceValueInTree(TreeNode root) {
        ArrayDeque<TreeNode> queue = new ArrayDeque<>();
        root.val = 0;
        queue.offer(root);
        while (!queue.isEmpty()) {
            ArrayDeque<TreeNode> queue2 = new ArrayDeque<>();
            int sum = 0;
            for (TreeNode fa : queue) {
                if (fa.left != null) {
                    sum += fa.left.val;
                    queue2.offer(fa.left);
                }
                if (fa.right != null) {
                    sum += fa.right.val;
                    queue2.offer(fa.right);
                }
            }
            for (TreeNode fa : queue) {
                int childSum = (fa.left != null ? fa.left.val : 0) + (fa.right != null ? fa.right.val : 0);
                if (fa.left != null) {
                    fa.left.val = sum - childSum;
                }
                if (fa.right != null) {
                    fa.right.val = sum - childSum;
                }
            }
            queue = queue2;
        }
        return root;
    }

    /*  2385. 感染二叉树需要的总时间
        给你一棵二叉树的根节点 root ，二叉树中节点的值 互不相同 。另给你一个整数 start 。在第 0 分钟，感染 将会从值为 start 的节点开始爆发。
        每分钟，如果节点满足以下全部条件，就会被感染：
        节点此前还没有感染。
        节点与一个已感染节点相邻。
        返回感染整棵树需要的分钟数。*/
    private TreeNode startNode;
    private Map<TreeNode, TreeNode> fa = new HashMap<>();
    public int amountOfTime(TreeNode root, int start) { // 两次遍历
        dfs(root, null, start);
        return maxDepth(startNode, startNode);
    }

    private void dfs(TreeNode node, TreeNode from, int start) {
        if (node == null) {
            return;
        }
        fa.put(node, from); // 记录父节点
        if (node.val == start) {
            startNode = node; // 找到startNode
        }
        dfs(node.left, node, start);
        dfs(node.right, node, start);
    }

    private int maxDepth(TreeNode node, TreeNode from) {
        if (node == null) {
            return -1; // 注意这里是 -1，因为 start 的深度为 0
        }
        int res = -1;
        if (node.left != from) {
            res = Math.max(res, maxDepth(node.left, node) + 1);
        }
        if (node.right != from) {
            res = Math.max(res, maxDepth(node.right, node) + 1);
        }
        if (fa.get(node) != from) {
            res = Math.max(res, maxDepth(fa.get(node), node) + 1);
        }
        return res;
    }
    /*  本题算法如下：
        递归时，除了返回当前子树的最大链长加一，还需要返回一个布尔值，表示当前子树是否包含 start\textit{start}start。
        如果当前节点是空节点，返回 0 和 false。
        设左子树的返回的链长为 lLen，右子树返回的链长为 rLen。
        如果当前节点值等于 start，初始化答案为 max(lLen,rLen)，即子树 start\textit{start}start 的最大深度。然后返回 1 和 true\texttt{true}true。
        如果左右子树都不包含 start，返回 max(\textit{lLen},\textit{rLen}) + 1max(lLen,rLen)+1。
        如果左子树或右子树包含 start，像计算直径那样，用 lLen+rLen 更新答案的最大值。如果左子树包含 start，则返回 lLen 和 true，否则返回 rLen 和 true。这种返回方式可以保证 lLen+rLen 一定是端点为 start 的直径长度。
        */
    // private int ans;
    public int amountOfTime1(TreeNode root, int start) { // 真的强啊！！！
        dfs1(root, start);
        return ans;
    }
    private int[] dfs1(TreeNode root, int start) { // {链长，是否存在start?1:0}
        if (root == null) {
            return new int[]{0, 0};
        }
        int[] left = dfs1(root.left, start);
        int[] right = dfs1(root.right, start);
        if (root.val == start) {
            ans = Math.max(left[0], right[0]);
            return new int[]{1, 1};
        }
        if (left[1] == 1 || right[1] == 1) { // 只有在左子树或右子树包含 start 时，才能更新答案
            ans = Math.max(ans, left[0] + right[0]); // 两条链拼成直径
            // 保证 start 是直径端点
            return new int[]{(left[1] == 1 ? left[0] : right[0]) + 1, 1};
        }
        return new int[]{Math.max(left[0], right[0]) + 1, 0}; // 还没有找到start
    }

}

