package geym.zbase.ch7.heap;

/**
 * 8head+ 4引用+4引用+ 4hashcode= 20,  对齐24。
 *
 * string = 8head+ 4引用value  +4hash+ 4hashcode=20
 */
public class WebPage {
    private String url;
    private String content;
    
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String cotent) {
        this.content = cotent;
    }
    
}
