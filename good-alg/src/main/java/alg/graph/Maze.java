package junit.alg.graph;

import java.util.*;

public class Maze {
    //初始化迷宫矩阵
    public static int[][] maze =
            {
                    {1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,0,0,0,1,1,1,1,1,1,1,1},
                    {1,1,1,0,1,1,0,0,0,0,1,1},
                    {1,1,1,0,1,1,0,1,1,0,1,1},
                    {1,1,1,0,0,0,0,1,1,0,1,1},
                    {1,1,1,0,1,1,0,1,1,0,1,1},
                    {1,1,1,0,0,0,0,1,1,0,1,1},
                    {1,1,1,1,1,1,0,1,1,0,1,1},
                    {1,1,0,0,0,0,0,0,0,0,0,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1}
            };
    public static int bx = 1, by = 1, ex = 8, ey = 10;//入口、出口的行列下标
    public static int count = 0;//记录方案的个数
    public static int[][] state = new int[maze.length][maze[0].length];//记录节点是否被访问，0表示未，1表示已

    public static void main(String[] args){
        for(int i=0; i<state.length; i++){
            Arrays.fill(state[i],0);
        }
        printMaze();
        maze[bx][by] = 2;
        move(bx,by);
    }

    public static void printMaze(){
        for(int k=0;k<maze.length;k++){//打印方案
            for(int l=0;l<maze[k].length;l++){
                System.out.print(maze[k][l]+" ");
            }
            System.out.println();
        }
    }

    public static void move(int i, int j){
        maze[i][j] = 2;//用2标记该节点，表示选择该节点作为路径节点之一
        state[i][j] = 1;//用1表示该节点已被访问，避免出现环路
        if(i==ex&&j==ey){
            count++;
            System.out.println("方案"+count+":");
            printMaze();
        }
        else{//判断上下左右可通节点
            if(maze[i][j+1]==0&&state[i][j+1]==0){
                move(i,j+1);
            }
            if(maze[i+1][j]==0&&state[i+1][j]==0){
                move(i+1,j);
            }
            if(maze[i-1][j]==0&&state[i-1][j]==0){
                move(i-1,j);
            }
            if(maze[i][j-1]==0&&state[i][j-1]==0){
                move(i,j-1);
            }
        }
        maze[i][j] = 0;
        state[i][j] = 0;
    }
}