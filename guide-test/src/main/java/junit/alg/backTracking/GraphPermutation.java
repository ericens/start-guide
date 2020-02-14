package junit.alg.backTracking;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class GraphPermutation {

    // ----------------------------图的表示方式------------------------
    private int vertexSize;// 顶点的数量
    private int[] vertexs;// 顶点对应的数组
    private boolean[] isVisited; // 顶点是否已经被访问

    private int[][] matrix;// 邻接矩阵
    private List<Integer>[] link;// 邻接矩阵
    private static final int MAX_WEIGHT = 1;// 代表顶点之间不连通
    private boolean isLink=false;

    public GraphPermutation(int vertexSize, int adjust[][]) {
        this.vertexSize = vertexSize;
        this.matrix = adjust;
        this.isLink=false;
        this.vertexs = new int[vertexSize];
        for (int i = 0; i < vertexSize; i++) {
            vertexs[i] = i;
        }

        isVisited = new boolean[vertexSize];
    }

    public GraphPermutation(int vertexSize, List link[]) {
        this.vertexSize = vertexSize;
        this.link=link;
        this.isLink=true;
        this.vertexs = new int[vertexSize];
        for (int i = 0; i < vertexSize; i++) {
            vertexs[i] = i;
        }

        isVisited = new boolean[vertexSize];
   }

    private void depthFirstSearch(int i, int step,List result){
        if(step==vertexSize){
            log.info("path:{}",result);
        }
        link[i].forEach(
                j-> {
                    //调用一次，遍历了一个节点
                    result.add(j);
                    depthFirstSearch(j,step+1,result);
                    result.remove(j);

                }
        );


    }


    public static void main(String[] args) {

        //无向图
        List[] link={Arrays.asList(1,2,3),Arrays.asList(0,2,3),Arrays.asList(0,1,3),Arrays.asList(0,1,2)};
        GraphPermutation  graph=new GraphPermutation(4,link);

        List result=new ArrayList();
        for (int i = 0; i < 4; i++) {
            result.add(i);
            graph.depthFirstSearch(0,0,result);
        }

    }

}
