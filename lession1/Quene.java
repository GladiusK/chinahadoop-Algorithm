/**
 * 
 */
package com.yx.chinahadoop.lession1;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 队列相关
 * @author yuxuan
 * 
 * @github https://github.com/GladiusK/chinahadoop-Algorithm
 *
 */
public class Quene {
	public static final int N = 16;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		int[][] G = new int[N][N];
//		init(G);
//		System.out.println(Calc(G,5));
		int[][] G = new int[13][13];
		int[] indegree = new int[13];
		initTopSort(G,indegree);
		int[] sort = new int[13];
		topsort(G,indegree, sort);
	}
	
	/**
	 * 初始化各个边距，点距
	 * @param G
	 */
	public static void init(int[][] G){
		G[0][4] = G[0][1] = 1;
		G[1][5] = G[1][2] = G[1][0] = 1;
		G[2][6] = G[2][3] = G[2][1] = 1;
		G[3][2] = G[3][7] = 1;
		G[4][0] = G[4][5] = 1;
		G[5][4] = G[5][1] = G[5][6] = G[5][9] = 1;
		G[6][5] = G[6][2] = G[6][7] = G[6][10] = 1;
		G[7][6] = G[7][3] = 1;
		G[8][12] = G[8][9] = 1;
		G[9][10] = G[9][13] = G[9][5] = G[9][8] = 1;
		G[10][14] = G[10][6] = G[10][11] = G[10][9] = 1;
		G[11][10] = G[11][15] = 1;
		G[12][8] = G[12][13] = 1;
		G[13][9] = G[13][12] = 1;
		G[14][13] = G[14][10] = 1;
		G[15][14] = G[15][11] = 1;
	}
	
	/**
	 * 计算 从 编号为 0 的元素 到 编号为 end 的元素的 最短路径的 条数
	 * @param G
	 * @param end
	 * @return
	 */
	public static int Calc(int[][] G, int end){
		// 记录从 from  到 i 位置 的 总步数
		int[] step = new int[16];
		// 记录从 from 到 i位置的 总路径条数
		int[] stepNum = new int[16];
		stepNum[0] = 1;
		//当前搜索的节点
		Queue<Integer> q = new LinkedList<Integer>();
		q.offer(0);
		int from,i,s;
		while(!q.isEmpty()){
			//取出队列中的 位置 作为 起点
			from = q.poll();
			//从 0 到 from位置的步数 ＋1
			s = step[from] + 1;
			for(i = 1; i < end +1; i++){
				// 如果from 和 i 位置 有路径可到达
				if(G[from][i] == 1){
					// 如果 i 位置 没有被 走过 或者 有更快路径
					if(step[i] == 0 || step[i] > s){
						// 从 0 到 i 位置 的总步数 为 从 0 到 from 位置 总步数 + 1
						step[i] = s;
						// 从 0 到 i 位置 的总路径条数  为 从 0  到 from  位置 的 总路径条数
						stepNum[i] = stepNum[from];
						// 将这个i 放入队列 作为 起点
						q.offer(i);
					}else if(step[i] == s){
						//如果 已经走到过 i  并且 从 0 到 i 的步数 等于 从 0 到 from 的步数 ＋ 1
						//从0 到 i 的 总路径条数 ＝  从 0 到 from 总路径条数 ＋ 从 0 到 i 的 路径条数
						//    因为 step[i] != 0 所以 已经有了 stepNum[i] 条路径，因此 只需要 再加上 stepNum[from] 条路径，
						//    就得到 从 0 到 i 的总路径
						stepNum[i] += stepNum[from];
					}
				}

			}
		}
		return stepNum[end];
	}
	
	
	/**
	 * 拓扑 排序辅助函数
	 *   用来生成 拓扑数组 和 入度 数组
	 * @param G
	 * @param indegree
	 */
	public static void initTopSort(int[][] G, int[] indegree){
		G[0][6] = G[0][5] = G[0][1] = 1;
		G[2][0] = G[2][3] = 1;
		G[3][5] = 1;
		G[5][4] = 1;
		G[6][4] = G[6][9] = 1;
		G[7][6] = 1;
		G[8][7] = 1;
		G[9][11] = G[9][10] = G[9][12] = 1;
		G[11][12] = 1;
		
		for(int i = 0 ; i < G.length; i++){
			for(int j = 0 ; j < G.length; j++){
				if(G[i][j] == 1){
					indegree[j] += 1;
				}
			}
		}
	}
	
	/**
	 * 拓扑排序
	 *   1-找出入度为 0 的元素
	 *   2-将和［1］中元素有连通关系的 元素 入度－1 
	 *   3－重复 ［1］和 ［2］
	 * @param G
	 * @param indegree
	 * @param sort
	 */
	public static void topsort(int[][] G, int[] indegree, int[] sort){
		// 存放 degree 为 0 的 元素
		int n = indegree.length;
		Queue<Integer> q = new LinkedList<Integer>();
		for(int i = 0; i < n; i++){
			if(indegree[i] == 0){
				q.offer(i);
			}
		}
		//记录 当前入度为0 的元素
		int cur;
		//sort 数组中的 位置下标
		int idx = 0;
		while(!q.isEmpty()){
			cur = q.poll();
			sort[idx++] = cur;
			for(int i = 0 ; i < n; i++){
				// 如果可以连通
				if(G[cur][i] != 0){
					//将连通元素的入度 减一
					indegree[i]--;
					if(indegree[i] == 0){
						//如果当前元素 入度 ＝＝0 则入队列
						q.offer(i);
					}
				}
				
			}
		}
		
		for(int i = 0; i < sort.length; i++){
			System.out.print(sort[i]);
			if(i != sort[i] -1){
				System.out.print( " ----> " );
			}
		}
		
	}

}
