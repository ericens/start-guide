public class Main {
    static public class Parent{
       private int a=2;
       private int b=3;
    }

    static class Node{
        String nodeName="";

        boolean isErrorOut=true;// 此节点产出的数据，是否是错误的。
        boolean isErrorNode=true; //是否出问题 节点
        String  errorCause="";    // 出问题的原因
        boolean isAtomicNode= false; // 是否最终原子节点

        Node[]  subNodes;// 所有子流程

    }

    public static void mainFindError(String [] args) {
        Node errorNode=new Node();
        setSubFlow(errorNode);
        findError(errorNode.subNodes,0,errorNode.subNodes.length);
    }

    public static Node findError(Node[] nodes,int start,int end) {
        Node midNode=nodes[ start+(end-start)/2];
        if(midNode.isErrorOut==true ){//此节点数据异常
            if(midNode.isErrorNode==true){ //此节点就是异常
                if(midNode.isAtomicNode==true){
                    System.out.println("找到最终原因：midNode："+midNode);
                    return midNode;
                }else{
                    //查找子模块
                    setSubFlow(midNode);
                    return findError(midNode.subNodes,0,midNode.subNodes.length);
                }
            }else{//往上游二分查找，异常节点
                return findError(nodes,start,(end-start)/2);
            }
        }else {
            //此节点数据正常，所以让下游查找数据
            return findError(nodes,(end-start)/2,end);
        }
    }
    /**
     * 根据节点，找出所有子节点，
     * @param node
     */
    public static void setSubFlow(Node node){
        Node[] subNodes=new Node[2];
        System.out.println("1. 找出所有节点，对流程要熟悉");
        System.out.println("2. 对节点，按照流程排序");
        node.subNodes=subNodes;
    }


    static public class Son extends Parent{
        int a=4;
        int b=5;
    }

    public static void main(String[] args) {
        Son s=new Son();


        String a = "a";
        String b = "b";
        String c = a+b;
        String d = a+b;

        System.out.println(c == c.intern()); //true
        System.out.println(d == d.intern()); //false
        System.out.println(d == c.intern()); //false

        System.out.println(s);

    }  
}  