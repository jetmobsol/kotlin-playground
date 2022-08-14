package inc.evil.playground;

import inc.evil.playground.classes.Course;
import inc.evil.playground.classes.Person;

/**
 * @author ionpa
 */
public class JavaCaller {
    public static void main(String[] args) {
        System.out.println(CollectionsUtil.ascending("Java"));
        System.out.println(CollectionsUtil.lastChar("Kotlin"));
        System.out.println(CollectionsUtil.getCOURSE());
        System.out.println(CollectionsUtil.UNIX_LINE_SEPARATOR);
        System.out.println(new Course(1, "", new Person()));
    }
}
