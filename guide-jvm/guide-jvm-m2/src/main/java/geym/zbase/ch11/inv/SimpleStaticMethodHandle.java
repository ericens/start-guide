package geym.zbase.ch11.inv;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * invokeSpecial
 * @author Administrator
 *
 */
public class SimpleStaticMethodHandle{
	public static void main(String[] args) throws Throwable {

		Encode encode = Base::encrypt;
		System.out.println(encode);


		SimpleStaticMethodHandle obj = new SimpleStaticMethodHandle();
		System.out.println(obj.callSin());
	}

	public double callSin() throws Throwable {
		MethodHandle mh = MethodHandles.lookup().findStatic(Math.class, "sin",
				MethodType.methodType(double.class,double.class));
		return (double)mh.invokeExact(Math.PI/2);
	}

	interface Encode {
		void encode(Derive person);
	}
	class Base {
		public void encrypt() {
			System.out.println("Base::speak");
		}
	}
	class Derive extends Base {
		@Override
		public void encrypt() {
			System.out.println("Derive::speak");
		}
	}
}