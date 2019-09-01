package geym.zbase.ch10.clshot;

import java.lang.reflect.Method;

/**
 * 1. MyClassLoader�Ĳ���·���� idea��·����·������DemoA
 * 2. �����ִ��·���� tmp·����tmp·���� û��DemoA
 * 3. ����ִ��ʱ��MyClassLoaderί�� parent�ҵ���ֻ����findClass��,���ڲ���ִ��·���������Ҳ���
 * 4. MyClassLoader �Լ��ڱ���·������ ���ҵ�DemoA
 *
 * 5. ִ���ǣ���new MyClassLoader. ���ԣ����MyClassLoader��ʵ�����µģ�����û�л��棬�������µ������һ�����ء�
 * 6. ������  ͬһ��classLoader ���ص�class������ͬһ��class
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
