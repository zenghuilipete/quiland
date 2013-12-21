package loading;

public class TestClassLoader {
    public static void main(String[] args) {
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        /*sun.misc.Launcher.AppClassLoader*/
        System.out.println(systemClassLoader.getClass().getCanonicalName());

        ClassLoader extClassLoader = systemClassLoader.getParent();
        /*sun.misc.Launcher.AppClassLoader*/
        System.out.println(extClassLoader.getClass().getCanonicalName());

        ClassLoader pcl = extClassLoader.getParent();
        System.out.println(pcl == null);
    }
}
