package geym.zbase.ch10.findorder;

/**
 * -Xbootclasspath/a:D:/tmp/clz
 * -Xbootclasspath/a:/Users/ericens/tmp
 *
 * -XBootClasspath 指定 说明加载先从启动classloader开始
 * @author Administrator
 *
 */
public class FindClassOrder {
	public static void main(String args[]){
		HelloLoader loader=new HelloLoader();

		loader.print();
	}
}
