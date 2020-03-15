package junit.alg.backTracking;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class EightQueen {


    /**

     一行，放一个
     二行，放一个
     。。。。。
     */
    int count=0;
    public void queen(int row, int len,int[][] output){
        if(row ==len){
            count++;
            log.info("找到一种方法.:{}",count);
            for (int i = 0; i < len; i++) {
                for (int j = 0; j < len; j++) {
                    System.out.print(output[i][j]+",");
                }
                System.out.println();
            }
            return;
        }
        for (int column = 0; column < len; column++) {
            if (isOk(output,row,column,len)){
                output[row][column]=1;  //尝试
                queen(row+1,len,output);
                output[row][column]=0;  //回退
            }
        }

    }


    @Test
    public void test(){
        for (int k = 4; k <=8;k++ ){
            int len=k;
            count=0;
            int out[][]=new int[len][len];
            for (int i =0 ; i < len;i++){
                for (int j = 0; j < len; j++) {

                    out[i][j]=0;
                }
            }
            queen(0,len,out);
            log.info("the count:{},",count);
        }

    }

    /**
     *
     *   0 1 0 0
     *   0 0 0 1
     *   1 0 0 0
     *   0 0 1 0
     *
     *   表示将要 output[row][cloumn]=1
     *   1. 同行是否有
     *   1. 同列是否有
     *   2. 斜对角是否有
     * @param output
     * @param column
     */
    public boolean isOk(int[][] output, int row,int column,int size){

        for (int i = 0; i < size; i++) {
            if(output[row][i] ==1){
                return false; //同行有
            }

            if(output[i][column] ==1){
                return false; //同列有
            }
        }


        int s=0,t=0;
        for(s=row-1,t=column-1; s>=0&&t>=0; s--,t--) {
            //判断左上方
            if (output[s][t] == 1) {
                return false;
            }
        }
        for(s=row+1,t=column+1; s<size&&t<size;s++,t++){
            //判断右下方
            if (output[s][t] == 1) {
                return false;
            }
        }
        for(s=row-1,t=column+1; s>=0&&t<size; s--,t++){
            //判断右上方
            if (output[s][t] == 1) {
                return false;
            }
        }
        for(s=row+1,t=column-1; s<size&&t>=0; s++,t--){
            //判断左下方
            if (output[s][t] == 1) {
                return false;
            }
        }
        return true;
    }



}
