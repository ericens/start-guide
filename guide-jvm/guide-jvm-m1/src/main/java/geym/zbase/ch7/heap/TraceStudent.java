
package geym.zbase.ch7.heap;

import java.util.List;
import java.util.Vector;

/**
 * dump the heap
 * -XX:+HeapDumpBeforeFullGC -XX:HeapDumpPath=/Users/ericens/tmp/stu.hprof
 * @author geym
 *
 */
public class TraceStudent {

    static List<WebPage> webpages = new Vector<WebPage>();

    public static void createWebPages() {
        for (int i = 0; i < 100; i++) {
            WebPage wp = new WebPage();
            wp.setUrl("http://www." + Integer.toString(i) + ".com");
            wp.setContent(Integer.toString(i));
            webpages.add(wp);
        }
    }

    public static void main(String[] args) {
        createWebPages();
        Student st3 = new Student(3, "billy");
        Student st5 = new Student(5, "alice");
        Student st7 = new Student(7, "taotaoxxxxxxx");

        StringBuffer str37=new StringBuffer();
        StringBuffer str57=new StringBuffer();
        StringBuffer str7=new StringBuffer();
        for (int i = 0; i < webpages.size(); i++) {
            if (i % st3.getId() == 0 && i % st7.getId() == 0){
                str37.append(","+i);
            }
            if (i % st5.getId() == 0 && i % st7.getId() == 0){
                str57.append(","+i);
            }
            if (i % st7.getId() == 0){
                str7.append(","+i);
            }
        }
        System.out.println("37:"+str37.toString());
        System.out.println("57:"+str57.toString());
        System.out.println("7:"+str7.toString());

        for (int i = 0; i < webpages.size(); i++) {
            if (i % st3.getId() == 0)
                st3.visit(webpages.get(i));
            if (i % st5.getId() == 0)
                st5.visit(webpages.get(i));
            if (i % st7.getId() == 0)
                st7.visit(webpages.get(i));
        }

        webpages.clear();
        System.gc();
    }

}
