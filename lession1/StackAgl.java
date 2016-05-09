/**
 * 
 */
package com.yx.chinahadoop.lession1;

import java.util.Stack;

/**
 * 栈相关
 * @author yuxuan
 * 
 * @github https://github.com/GladiusK/chinahadoop-Algorithm
 *
 */
public class StackAgl {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "6327*-+";
		//int p = getLongestParentheseByLoop(s.toCharArray());
		int p = reversePolishNotation(s.toCharArray());
		System.out.println(p);

	}
	
	/**
	 * 用 栈实现 最长 匹配 括号
	 * 时间 复杂度 O(n) 空间 复杂度O(n)
	 * @param p
	 * @return
	 */
	public static int getLongestParenthese(char[] p){
		if(p == null || p.length <= 0){
			return -1;
		}
		int start = -1;
		Stack<Integer> stack = new Stack<Integer>();
		char tmp;
		int ans = -1;
		for(int i = 0; i < p.length; i++){
			tmp = p[i];
			if(tmp == '('){
				//如果是左括号 则入栈 i
				stack.push(i);
			}else{
				//如果是右括号 则出栈一个 元素
				if(stack.isEmpty()){
					// 如果栈为空 则修改 start 位置
					start = i;
				}else{
					stack.pop();
					if(stack.isEmpty()){
						// 如果出栈后，栈为空，则证明 刚好匹配完
						ans = max(ans, i - start);
					}else{
						// 如果出栈后，栈不为空， 则证明 匹配到最近的一个
						ans = max(ans, i - stack.firstElement());
					}

				}
			}
		}
		return ans;
	}
	
	/**
	 * 用 非栈实现 最长 匹配 括号
	 * 时间 复杂度 O(n) 空间 复杂度O(1)
	 *   先从左到右 匹配 左括号(针对右括号 >= 左括号情况)，再从 右到左 匹配 右括号（针对 左括号 >= 右括号情况)
	 * @param p
	 * @return
	 */
	public static int getLongestParentheseByLoop(char[] p){
		int ans = -1;
		int start = -1;
		int deep = 0 ; //记录有多少左括号
		
		for(int i = 0; i < p.length; i++){
			if(p[i] == '('){
				//如果左扩号 deep 增加
				deep++;
			}else{
				deep--;
				if(deep == 0){
					// 此时 左右括号 匹配 
					ans = max(ans, i - start);
				}else if(deep < 0){
					// 右括号 大于 左括号
					start = i;
				}
			}
		}
		
		// 如果左括号 多于右括号的时候
		deep = 0; // 记录右括号数目
		start = p.length; // 从最右边位置＋1 作为 记录起点
		for(int i = p.length - 1; i >= 0; i--){
			if(p[i] == ')'){
				//如果右括号 deep++
				deep++;
			}else{
				deep--;
				if(deep == 0){
					ans = max(ans, start - i);
				}else if(deep < 0){
					start = i;
				}
			}
		}
		
		return ans;
	}
	
	/**
	 * 用栈 实现 逆波兰序列 并打印计算过程
	 * @param exp
	 * @return
	 */
	public static int reversePolishNotation(char[] exp){
		int ans = -1;
		int a,b;
		char tmp;
		StringBuffer sb = new StringBuffer();// 记录计算过程
		Stack<Integer> s = new Stack<Integer>();// 用与后序遍历
		int last = -1;
		for(int i = 0; i < exp.length; i++){
			tmp = exp[i];
			if(!isOperator(tmp)){
				//如果不是操作数 则入栈
				s.push(Integer.valueOf(String.valueOf(tmp)));
			}else{
				//如果时操作数 则从栈中弹出两个数 进行操作
				a = s.pop();
				b = s.pop();
				if(a != last && b != last){
					sb.append(a).append(tmp).append(b);
				}else if(b != last && a == last){
					sb.append(tmp).append(b);
				}else if(a != last && b == last){
					sb.append(tmp).append(a);
				}
				
				if(tmp == '+'){
					last = a+b;
				}else if(tmp == '-'){
					last = a-b;
				}else if(tmp == '*'){
					last = a*b;
				}else if(tmp == '/'){
					last = a/b;
				}
				s.push(last);
			}
		}
		if(!s.isEmpty()){
			int rs = s.pop();
			System.out.println(sb.toString() + " = " + rs);
			return rs;
		}
		return ans;
	}
	
	/**
	 * 判断是否操作符
	 * @param op
	 * @return
	 */
	public static boolean isOperator(char op){
		return op == '+' || op == '-' || op == '*' || op == '/';
	}
	
	/**
	 * 返回两者中更加大的数
	 * @param a
	 * @param b
	 * @return
	 */
	public static int max(int a, int b){
		return a > b ? a: b;
	}

}
