package com.yx.chinahadoop;

import java.util.ArrayList;
import java.util.List;

/**
 * 哈夫曼编码 java 实现
 * 
 * @author yuxuan
 *
 */
public class HuffmanCode {
	//记录当前节点的左节点
	private static int left;
	//记录当前节点的右节点
	private static int right;

	public static void main(String[] args) {
		// java unicode 编码
		String str = "when I was young I'd listen to the radio"+
				"waiting for my favorite songs"+
				"when they played I'd sing along,"+ 
				"it make me smile."+
				"those were such happy times and not so long ago"+
				"how I wondered where they'd gone."+
				"but they're back again just like a long lost friend"+
				"all the songs I love so well."+
				"every shalala every wo'wo"+
				"still shines."+
				"every shing-a-ling-a-ling that they're starting to sing"+
				"so fine";
		HaffmanMethod(str);

	}

	/**
	 * 给定字符串，进行哈夫曼编码
	 * @param str
	 */
	public static void HaffmanMethod(String str) {
		// java unicode 编码
		int N = (int) Math.pow(2, 16);
		// 待统计的字符数组
		char[] arr = str.toCharArray();
		// 记录词频的数组
		int[] warr = new int[N];
		// 统计词频
		calCharNum(arr, warr);
		// 记录出现的字符
		List<Integer> pchar = new ArrayList<Integer>();
		// 剔除未出现的字符
		removeNoUseChar(warr, pchar);
		int N2 = pchar.size();
		List<List<Character>> code = new ArrayList<>();
		// 哈夫曼编码
		HaffmanCoding(warr, N2, code);
		Print(code, pchar);
	}

	/**
	 * 打印哈夫曼编码结果
	 * @param code
	 * @param pchar
	 */
	public static void Print(List<List<Character>> code, List<Integer> pchar){
		int len = code.size();
		for(int i = 0; i < len; i++){
			PrintCode(code.get(i), (char)pchar.get(i).intValue());
		}
	}
	
	/**
	 * 打印哈夫曼编码结果
	 * @param code
	 * @param c
	 */
	public static void PrintCode(List<Character> code, char c){
		StringBuffer sb = new StringBuffer();
		for(int i =0 ; i < code.size(); i++){
			sb.append(code.get(i));
		}
		int tmp = c;
		System.out.println(tmp+ " "+c +" : " +sb.toString());
	}
	/**
	 * 计算字符出现的频度
	 * 
	 * @param arr
	 *            待统计的字符数组
	 * @param warr
	 *            记录字符对应的频度
	 */
	public static void calCharNum(char[] arr, int[] warr) {
		for (int i = 0; i < arr.length; i++) {
			warr[arr[i]]++;
		}
		warr['\t'] = 0;
	}

	/**
	 * 将未出现的字符，从出现的字符中间剔除
	 * 
	 * @param warr
	 *            字符频度数组
	 * @param pchar
	 *            记录频度 > 0 的字符
	 */
	public static void removeNoUseChar(int[] warr, List<Integer> pchar) {
		int j = 0;
		for (int i = 0; i < warr.length; i++) {
			if (warr[i] != 0) {
				pchar.add(i);
				if (i != j) {
					warr[j] = warr[i];
				}
				j++;
			}
		}
	}

	/**
	 * 得到当前节点 的左节点 和 右节点 的位置 （求从 0 到 n－1 中 最小的weight 和 次小的 )
	 * 
	 * @param arr
	 *            哈夫曼节点数组
	 * @param n
	 *            当前节点位置
	 */
	public static void selectNode(HuffmanNode[] arr, int n) {
		int min = -1;
		int sec = -1;
		for (int i = 0; i < n; i++) {
			if (arr[i].weight > 0 && arr[i].parent == 0) {
				if (left < 0 || min > arr[i].weight) {
					sec = min;
					right = left;
					left = i;
					min = arr[i].weight;
				}  else if (right < 0 || sec > arr[i].weight) {
					right = i;
					sec = arr[i].weight;
				}
			}
		}
	}

	/**
	 * 反转链表
	 * 
	 * @param arr
	 */
	public static void reverse(List<Character> arr) {
		int i = 0, j = arr.size() - 1;
		char tmp;
		while (i < j) {
			tmp = arr.get(i);
			arr.set(i++, arr.get(j));
			arr.set(j--, tmp);
		}
	}

	/**
	 * 进行哈夫曼编码 1-构建哈夫曼数组，数组长度为原词频数组节点的长度 ＊ 2 －1 
	 *             2-初始化哈夫曼数组节点中 频度 
	 *             3-构建从 N 到 m 个哈夫曼树节点 
	 *             4-对已经构建好的哈夫曼树进行 哈夫曼编码
	 * 
	 * @param weight
	 * @param N
	 * @param code
	 */
	public static void HaffmanCoding(int[] weight, int N, List<List<Character>> code) {
		if (N > 0) {
			int m = 2 * N - 1;
			HuffmanNode[] hfTree = new HuffmanNode[m];
			for (int i = 0; i < m; i++) {
				hfTree[i] = new HuffmanNode();
				hfTree[i].parent = 0;
				hfTree[i].left = 0;
				hfTree[i].right = 0;
			}
			for (int i = 0; i < N; i++) {
				// 此处只需要循环到 原词频数组的长度 N
				hfTree[i].weight = weight[i];
			}

			for (int i = N; i < m; i++) {
				left = -1;
				right = -1;
				selectNode(hfTree, i);
				//将当前节点的 左右节点的父节点设置为 当前节点，并设置当前节点的左右节点位置 和 频度
				hfTree[left].parent = hfTree[right].parent = i;
				hfTree[i].left = left;
				hfTree[i].right = right;
				hfTree[i].weight = hfTree[left].weight + hfTree[right].weight;
			}

			int node, parent;
			List<Character> tmp;
			for (int i = 0; i < N; i++) {
				/**
				 * 从叶子节点开始设置编码，
				 * 将当前节点 设置为 父节点，继续设置编码 一直设置到最上层节点
				 * 倒序刚才得到的编码，使其成为 哈夫曼 编码
				 **/
				tmp = new ArrayList<Character>();
				node = i;
				parent = hfTree[node].parent;
				while (parent != 0) {
					if (hfTree[parent].left == node) {
						tmp.add('0');
					} else {
						tmp.add('1');
					}
					node = parent;
					parent = hfTree[node].parent;
				}
				reverse(tmp);
				code.add(tmp);
			}
		}
	}


}

class HuffmanNode {
	int weight;
	int left;
	int right;
	int parent;

}
