/**
 * 
 */
package com.yx.chinahadoop.lession1;

import java.util.Random;

/**
 * @author yuxuan
 * 
 * @github https://github.com/GladiusK/chinahadoop-Algorithm
 *
 */
public class List {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ListNode head = createNode(3);
		ListNode head2 = createNode(8);
//		Print(head);
//		deleteDupl(head);
//		Print(head);
//		partionList(head,5);
//		Print(head);
		//Print(head2);
		//Print(sum(head,head2));
		//reverse(head,4,8);
		ListNode a1 = new ListNode(4);
		ListNode a2 = new ListNode(1);
		ListNode a3 = new ListNode(2);
		ListNode a4 = new ListNode(3);
		ListNode a5 = new ListNode(3);
		
		a1.next = a2;
		a2.next = a3;
		
		a4.next = a5;
		a5.next = a1;
		
		findFirstSameNode(a1,a5);
		
		
		
	}
	
	/**
	 * 创建初始化 列表辅助函数
	 * @param cnt
	 * @return
	 */
	public static ListNode  createNode(int cnt){
		ListNode head = new ListNode(0);
		Random rd = new Random();
		ListNode cur;
		int tmp;
		for(int i = 0 ; i < cnt; i++){
			tmp = rd.nextInt(100) % 10;
			cur = new ListNode(tmp);
			cur.next = head.next;
			head.next = cur;
		}
		return head;
	}
	
	/**
	 * 查找 2个 单向列表中 第一个 指向内存地址相同 的节点
	 *     由于都是单向列表，当出现第一个相同节点后，后面所有节点必然都是一样，呈现一个 Y 型
	 *     设较长链为 p1 长度 n1  较短链为 p2 长度 n2 ，必然在 p1中 n1 - n2后的节点开始 才会有相同节点
	 * @param head1
	 * @param head2
	 * @return
	 */
	public static ListNode findFirstSameNode(ListNode head1, ListNode head2){
		 ListNode p1 = head1.next;
		 ListNode p2 = head2.next;
		 int n1 = calSize(p1);
		 int n2 = calSize(p2);
		 //默认 p2 比较长
		 if(n1 > n2){
			 swap(p1,p2);
			 int tmp = n1;
			 n1 = n2;
			 n2 = tmp;
		 }
		 
		 // 空转 n2 - n1 次
		 for(int i = 0; i < n2 -n1; i++){
			  p2 = p2.next;
		 }
		 
		 //同时向后
		 while(p1 != null){
			 if(p2 == p1){
				 return p1;
			 }
			 p1 = p1.next;
			 p2 = p2.next;
		 }
		 
		 return null;
		 
	}
	
	/**
	 * 将链表进行分区 组合
	 * @param head  -- 头节点
	 * @param pnum  -- 按照 pnum 分区 链表
	 * @return
	 */
	public static ListNode partionList(ListNode head, int pnum){
		ListNode leftHead = new ListNode(0);
		ListNode rightHead = new ListNode(0);
		ListNode cur = head.next;
		ListNode left = leftHead;
		ListNode right = rightHead;
		while(cur!= null){
			if(cur.val < pnum){
				left.next = cur;
				left = cur;
			}else{
				right.next = cur;
				right = cur;
			}
			cur = cur.next;
		}
		left.next = rightHead.next;
		// 由于此时的 right.next 指向 cur 而 cur 又和right 指向 同一地址 存在循环，所以需要将 right.next 置空
		right.next = null;
		head.next = leftHead.next;
		return head;
	}
	
	/**
	 * 两数 相加
	 * @param head1
	 * @param head2
	 * @return
	 */
	public static ListNode sum(ListNode head1, ListNode head2){
		ListNode sum = new ListNode(0);
		ListNode p1 = head1.next;
		ListNode p2 = head2.next;
		// 用这个表示尾节点的 指针
		ListNode ptail = sum;
		// 进位
		int carry = 0;
		int val = 0;
		ListNode cur;
		while(p1 != null || p2 != null || carry != 0){
			val = (p1==null?0:p1.val) + (p2==null?0:p2.val) + carry; 
			carry = val / 10 ;
			val = val % 10;
			cur = new ListNode(val);
			ptail.next = cur;
			ptail = cur;
			
			//向后移动
			p1 = p1==null?p1:p1.next;
			p2 = p2==null?p2:p2.next;
			
		}
		if(carry != 0){
			ptail.next = new ListNode(carry);
		}
		return sum;
	}
	
	/**
	 * 关键为 head pre  cur next 的使用
	 *    1      2     3     4      5
	 *    head   pre   cur   next 
	 * @param head  原始链表
	 * @param start 开始位子
	 * @param end   结束位子
	 */
	public static void reverse(ListNode head, int start, int end){
		//保留原始 head 引用
		ListNode headTmp = head;
		ListNode cur = head.next;
		
		//将头指针移动到 需要反转的起始位子的前面
		for(int i = 0; i < start-1; i++){
			head = cur;
			cur = cur.next;
		}
		//这个 pre 用来跟踪 需要反转的 倒数第二个位子。
		ListNode pre = cur;
		cur = cur.next;
		//next 用于记录当前位子的后一个元素
		ListNode next;
		for(int i = start -1; i < end - 1; i++){
			next = cur.next;
			//将cur --> next 变成 next -->cur
			cur.next = head.next;
			head.next = cur;
			// 将当前元素进行 头插
			pre.next = next;
			cur = next;
		}
		Print(headTmp);
		
	}
	
	/**
	 * 删除 重复的 第二个元素
	 * @param head
	 */
	public static void deleteDupl(ListNode head){
		ListNode pre = head.next;
		ListNode cur;
		while(pre != null){
			cur = pre.next;
			if(cur != null &&(cur.val == pre.val)){
				pre.next = cur.next;
			}else{
				pre = cur;
			}
		}
	}
	
	/**
	 * 删除 第一个重复的元素
	 * @param head
	 */
	public static void deleteDuplFirst(ListNode head){
		ListNode pre = head;
		ListNode cur = pre.next;
		ListNode next;
		while(cur != null){
			next = cur.next;
			while(next !=null && (cur.val == next.val)){
				pre.next = next;
				cur = next;
				next = cur.next;
			}
			//继续查找后面的元素
			pre = cur;
			cur = next;
		}
	}
	
	/**
	 * 删除所有 重复元素
	 * @param head
	 */
	public static void deleteAllDup(ListNode head){
		ListNode pre = head;
		ListNode cur = pre.next;
		ListNode next;
		while( cur !=null ){
			next = cur.next;
			boolean flag = false;
			while(next !=null && (cur.val == next.val)){
				pre.next = next;
				cur = next;
				next = cur.next;
				flag = true;
			}
			if(flag){
				pre.next = next;
			}else{
				pre = cur;
			}
			cur = next;
		}
	}
	
	public static void swap(ListNode p1, ListNode p2){
		ListNode tmp ;
		tmp = p1;
		p1 = p2;
		p2 = tmp;
	}
	
	/**
	 * 计算列表长度辅助函数
	 * @param p
	 * @return
	 */
	public static int calSize(ListNode p){
		int i = 0;
		while(p != null){
			i++;
			p = p.next;
		}
		return i;
	}
	
	/**
	 * 打印列表辅助函数 (不打印 头节点)
	 * @param p
	 */
	public static void Print(ListNode p){
		ListNode tmp = p.next;
		while(tmp != null){
			System.out.print(tmp.val);
			tmp = tmp.next;
			if(tmp != null){
				System.out.print("-->");
			}
		}
		System.out.println();
	}
}

/**
 * @author yuxuan
 * 单向链表 结构体
 */
class ListNode {
	int val;
	ListNode next;

	ListNode(int x) {
		val = x;
	}
}
