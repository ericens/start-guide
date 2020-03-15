package junit.alg;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

public class LinkedListReverse {
    @Data
    @AllArgsConstructor
    static class Node{
        int i;
        Node next;
    }

    /**

     假定原链表为1→2→3→4→5→6→7，递归法的主要思路为：先逆序除第一个结点以外的子链表（将1→2→3→4→5→6→7变为1→7→6→5→4→3→2），
     接着把结点1添加到逆序的子链表的后面（1→2→3→4→5→6→7 变为7→6→5→4→3→2→1）。
     同理，在逆序链表2→3→4→5→6→7时，也是先逆序子链表3→4→5→6→7（逆序为2→7→6→5→4→3），
     接着实现链表的整体逆序（2→7→6→5→4→3转换为7→6→5→4→3→2）

     * @param head
     * @return
     */

    public Node reserver(Node head) {

        if(head.next==null){
            return head;
        }
        Node newHead=reserver(head.next);  //返回  逆序的子链表的头
        head.next.next=head;   // head.next=2会成为 逆序的子链表的尾节点。  所以head=1接在这个未节点后面
        head.next=null;        // head=1是尾节点了，没有后续节点了（边界问题）
        return newHead;
    }

    public void print(Node head){
        while(head!=null){
            System.out.print(","+head.i);
            head=head.next;

        }
        System.out.println();
    }

    @Test
    public void test(){
        Node head=new Node(1,null);
        Node current = head;
        for (int i = 2; i <=7; i++) {
            Node p=new Node(i,null);
            current.next=p;
            current=p;
        }
        print(head);

        head=reserver(head);

        print(head);
    }

}
