package com.yx.chinahadoop;

public class StringLession {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "abcd";
		char[] arr = s.toCharArray();
		LeftRotateString(arr.length, 1, arr);
		System.out.println(arr);
		
	}
	
	/**
	 * @param len - 总长度
	 * @param k - 左移动 k 位
	 * @param arr
	 */
	public static void LeftRotateString(int len, int k, char[] arr){
		//总长度 6  左移动8 位  就相当于  左移动  8 % 6 = 2 位
		k = k % len;
		reverseString(0, k-1, arr);
		reverseString(k, len -1, arr);
		reverseString(0, len -1, arr);
	}
	
	/**
	 * @param from 移动开始位置
	 * @param to   移动结束位置
	 * @param arr
	 */
	public static void reverseString(int from, int to, char[] arr){
		char tmp ;
		//交换 from  和 to，交换完成后 from++,to-- 直到 from = to
		while(from < to ){
			tmp = arr[from];
			arr[from++] = arr[to];
			arr[to--] = tmp;
		}
	}

}
