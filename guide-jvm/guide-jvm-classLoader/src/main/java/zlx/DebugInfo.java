package zlx;

public class DebugInfo {
    static class FatherClass{
//        public FatherClass(){
//            System.out.println("父类 无参 构造函数");
//        }
        public FatherClass(int i){
            System.out.println("父类 一个参数构造函数super = "+i);
        }
        public FatherClass(int i,String j){
            System.out.println("父类 一个参数构造函数superi = "+i+",superj = "+j);
        }

    }

    static class SonClass extends FatherClass{
        public SonClass(){
            super(22);//line 1
            System.out.println("子类 无参 构造函数");
        }
        public SonClass(int a){
            super(33,"Hello");//line 2
            System.out.println("子类一个参数构造函数sub = "+a);
        }
        public void fun(int a){//子类中定义一个实例函数
//            super(33,"Hello");//构造函数调用必须声明在构造函数中,这行代码不注释的话会报错
//            System.out.println("子类一个参数构造函数sub = "+a);
        }
    }
    public static class ConstructorExtend {//测试子类继承父类的构造函数
        public static void main(String args[]){
//            FatherClass fa = new FatherClass();
            FatherClass fa1 = new FatherClass(100);
            SonClass son = new SonClass();
            SonClass son1 = new SonClass(200);
            son1.fun(2);
        }
    }

}
