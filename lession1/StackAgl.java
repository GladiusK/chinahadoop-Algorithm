/**
 * 
 */
package com.yx.chinahadoop.lession1;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

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
		//int p = reversePolishNotation(s.toCharArray());
		List<Integer> arr = new ArrayList<Integer>();
		arr.add(2);
		arr.add(7);
		arr.add(5);
		arr.add(6);
		arr.add(4);
		int p = calMaxHistogram(arr);
		int[] height = {0,1,0,2,1,0,1,3,2,1,2,1};
		System.out.println(trappingRainWater(height));

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
	 * @param exp  后序表达式
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
	 * 计算直方图 最大矩形面积
	 *   1-如果 h[i] > h[i-1]  那么从 i-1 到 i 的直方图最大面积 为 从0到i－1 面积＋ i－1 到 i的面积 
	 *   2-如果 h[i] < h[i-1]  那么应该计算的最大面积为 从 0 到 i-1的 面积
	 *   3- ［1］的时候 将满足条件的i 放入缓冲区
	 *   4- ［2］的时候 将缓冲区最后放入的数据 与当前数据进行比较
	 *       如果当前数据 > 缓冲区中最后放入的数据 那么可以计算最大面积 ＝ (栈顶元素 和 当前元素 的横向距离) * 缓冲区中最后放入的数据
	 *       如果当前数据 < 缓冲区中最后放入的数据 那么将数据 压入缓冲区
	 * @param height  矩形数组
	 * @return
	 */
	public static int calMaxHistogram(List<Integer> height){
		//为了使得 程序保持一致性
		height.add(0);
		//用于记录满足h[i] > h[i-1] 条件时的 i
		Stack<Integer> s = new Stack<Integer>();
		int ans = 0;
		int tmp;
		for(int i = 0 ; i < height.size(); ){
			if(s.isEmpty() || height.get(i) > height.get(s.peek())){
				s.push(i);
				i++;
			}else{
				tmp = s.pop();
				ans = max(ans, height.get(tmp) * (s.isEmpty()?i: i-s.peek()-1));
			}
		}
		return ans;
	}
	
	/**
	 * 直方图蓄水量
	 *  1－ 直方图最左边L 和 最右边R 为边界，类似外壁的概念不进行蓄水
	 *  2-  如果 L > R R边为短边 令和 短边相邻的方柱为 X 短边为Y
	 *      如果 X > 短边 那么 不能进行蓄水 丢弃 X
	 *      如果 X < 短边 那么 可以蓄水  Y-X 水量
	 *  3- 循环 ［1］和 ［2］ 直到 L ＝ R
	 * @param rain 方柱数组
	 * @return
	 */
	public static int trappingRainWater(int[] rain){
		int left = 0;
		int right = rain.length -1;
		int trap = 0;
		int secHeight = 0;
		while(left < right){
			if(rain[left] < rain[right]){
				//如果 左边 小于 右边 那么 从左边开始 计算
				secHeight = max(secHeight, rain[left]);
				trap += (secHeight - rain[left]);
				left++;
			}else{
				secHeight = max(secHeight, rain[right]);
				trap += (secHeight - rain[right]);
				right--;
			}
		}
		return trap;
	}
	
	/**
	 * 判断是否操作符
	 * @param op
	 * @return
	 */
	private static boolean isOperator(char op){
		return op == '+' || op == '-' || op == '*' || op == '/';
	}
	
	/**
	 * 返回两者中更加大的数
	 * @param a
	 * @param b
	 * @return
	 */
	private static int max(int a, int b){
		return a > b ? a: b;
	}

}
