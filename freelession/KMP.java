/**
 * 
 */
package com.yx.chinahadoop;

/**
 * @author yuxuan
 * @github https://github.com/GladiusK/chinahadoop-Algorithm
 *
 */
public class KMP {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//testKMP();
		test("kmp");
		test("kmp2");
		test("bp");
	}
	
	/**
	 * 暴力求解 KMP
	 * 当 m[i+j] == p[j] 的时候 j 后移 继续判断
	 * 当 m[i+j] != p[j] 的时候 j 重置为0，i后移
	 * @param p
	 * @param m
	 * @return
	 */
	public static int BF(char[] p, char[] m){
		int j = 0;
		int i = 0;
		while(i < m.length && j < p.length){
			if(m[i+j] == p[j]){
				j++;
			}else{
				i++;
				j = 0;
			}
		}
		if(j >= p.length){
			return i;
		}
		return -1;
	}
	
	public static void test(String type){
		String str = "abaabc";
		String mstr = "caabaabaabcaa";
		char[] m = mstr.toCharArray();
		char[] p = str.toCharArray();
		if(type.equals("kmp")){
			int[] next = new int[p.length];
			getNext(p, next);
			System.out.println(kmp(m,p,next));
		}else if(type.equals("kmp2")){
			int[] next = new int[p.length];
			getNext2(p, next);
			System.out.println(kmp(m,p,next));
		}else if(type.equals("bp")){
			System.out.println(BF(p,m));
		}

	}
	
	/**
	 * kmp 算法根据 模式字符串 自身的性质决定 当匹配到j 位的时候，发生不匹配的情况，模式字符串需要右移 next位置
	 * 可以通过例子 待匹配串 a  b  a  a  b  a  a  b  c
	 *             模式串 a  b  a  a  b  c
	 * 进行思考  
	 * @param p
	 * @param next
	 */
	public static int kmp(char[] m, char[] p,int[] next){
		int i = 0;
		int j = 0;
		int ans = -1;
		while(i < m.length){
			//j == -1 表示 当前的i 位置对应的 next数组中无满足 前缀后缀相等，因此需要将模式串重置到 首位，然后 i后移一位 
			if(j == -1 || m[i] == p[j]){
				++i;
				++j;
			}else{
				j = next[j];
			}
			
			if(j == p.length){
				ans = i - p.length;
				break;
			}
		}
		return ans;
	}
	
	/**
	 * kmp next 数组根据模式字符串自身的性质，找到模式字符串中从0到当前位置－1位置中 长度为k 的前缀 和 长度 为k 的后缀相等的最长后缀的长度。eg：
	 * 模式串    a  b  a  a  b  c  a  b  a
	 * next    -1 0  0  1  1  2  0  1   2
	 * 对于模式串位置j，有next[j] = k, 既：P0P1...Pk-2Pk-1 = Pj-kPj-k+1..Pj-2Pj-1
	 *               则，对于 模式串中j+1位置，
	 *               1- p[k] == p[j]，则next[j+1] = next[j]+1
	 *               2- p[k] != p[j],如果p[next[k]] == p[j], 则next[j+1] = next[k] + 1
	 *                               如果不想的，再重复［2］的过程
	 * @param p
	 * @param next
	 */
	public static void getNext(char[] p, int[] next){
		int k = -1;
		int j = 0;
		next[0] = -1;
		//通过 next[j-1]得到 next[j] 值
		while(j < p.length -1 ){
			// 此时 k = next[j-1], p[k]表示前缀, p[j]表示后缀
			// k == -1 表示 未找到k前缀和k后缀相等
			if(k == -1 || p[j] == p[k]){
				++j;
				++k;
				next[j]= k;
			}else{
				// 如果p[k] != p[j]， 则继续递归计算p[next[k]] 是不是等于p[j]
				k = next[k];
			}
		}
	}
	
	/**
	 * 求next数组的 变种
	 * @param p
	 * @param next
	 */
	public static void getNext2(char[] p, int[] next){
		int k = -1;
		int j = 0;
		next[0] = -1;
		//通过 next[j-1]得到 next[j] 值
		while(j < p.length -1 ){
			// 此时 k = next[j-1], p[k]表示前缀, p[j]表示后缀
			// k == -1 表示 未找到k前缀和k后缀相等
			if(k == -1 || p[j] == p[k]){
				++j;
				++k;
				if(p[j] == p[k]){
					next[j] = next[k];
				}else{
					next[j]= k;
				}
			
			}else{
				// 如果p[k] != p[j]， 则继续递归计算p[next[k]] 是不是等于p[j]
				k = next[k];
			}
		}
	}

}
