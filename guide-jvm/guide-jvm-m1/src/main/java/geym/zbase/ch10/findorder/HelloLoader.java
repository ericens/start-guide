package geym.zbase.ch10.findorder;

public class HelloLoader {
	public void print(){
//		System.out.println("I am in apploader");
        System.out.println(this.getClass().getClassLoader());
//	    System.out.println("I am in Boot ClassLoader");
	}
}