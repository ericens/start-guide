package zlx.jvmDebugTool;

public class HotSwapClassLoader extends ClassLoader {

    public HotSwapClassLoader() {
        super(HotSwapClassLoader.class.getClassLoader());  //    指定双亲 protected ClassLoader(ClassLoader parent) {
    }

    public Class loadByte(byte[] classByte) {  // 公开方法
        return defineClass(null, classByte, 0, classByte.length);
    }

}