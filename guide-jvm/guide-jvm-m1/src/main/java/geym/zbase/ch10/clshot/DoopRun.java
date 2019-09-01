package geym.zbase.ch10.clshot;

import java.lang.reflect.Method;

/**
 * 1. MyClassLoader的查找路径是 idea的路径，路径下有DemoA
 * 2. 程序的执行路径是 tmp路径，tmp路径下 没有DemoA
 * 3. 程序执行时，MyClassLoader委托 parent找到（只定义findClass）,由于不在执行路径，所以找不到
 * 4. MyClassLoader 自己在本地路径查找 ，找到DemoA
 *
 * 5. 执行是，先new MyClassLoader. 所以，这个MyClassLoader的实例是新的，所以没有缓存，被当成新的类加载一样加载。
 * 6. 所以是  同一个classLoader 加载的class，才是同一个class
 */
public class DoopRun {
	public static void main(String args[]) {
		while(true){
			try{
				MyClassLoader loader = new MyClassLoader("/Users/ericens/git/my/start-guide/guide-jvm/guide-jvm-m1/target/classes");
				Class cls = loader.loadClass("geym.zbase.ch10.clshot.DemoA");
				Object demo = cls.newInstance();
		
				Method m = demo.getClass().getMethod("hot", new Class[] {});
				m.invoke(demo, new Object[] {});
				Thread.sleep(5000);
			}catch(Exception e){
				System.out.println("not find");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
				}
			}
		}
	}
}
