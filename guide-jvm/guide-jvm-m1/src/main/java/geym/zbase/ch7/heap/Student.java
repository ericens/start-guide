package geym.zbase.ch7.heap;

import java.util.List;
import java.util.Vector;

/**
 *  8+4      Ù–‘+ 4 +4 +4
 */
public class Student {
    private int xx;
    private int yy;
    private int zz;
    private int id;
    private String name;
    private List<WebPage> history=new Vector<WebPage>();
    
    public Student(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public void visit(WebPage w){
        history.add(w);
    }
}
