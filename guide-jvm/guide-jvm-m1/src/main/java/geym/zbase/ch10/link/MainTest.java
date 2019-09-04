package geym.zbase.ch10.link;

public class MainTest {
	static {
//		-XX:+TraceClassLoading
	}
	public static void main(String[] args) throws ClassNotFoundException {
//		Class.forName("java.lang.String");
		Class.forName("geym.zbase.ch10.link.P");
//		test2();
//		test1();

		C.test1();
		P.test1();

	}

	public static void test1(){
	  C c=new C();
	}

	public static void test3(){
		{
			int i =3;
			int j=5;
			System.out.println(i+j);

		}
		int a=1;
		System.out.println(a);
	}



	public static void test2(){


		System.out.println(C.j);
	}


}
