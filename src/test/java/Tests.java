import app.Triangle;
import misc.Vector2d;
import org.junit.Test;

public class Tests {

    @Test
    public void test1() {
        Triangle t = new Triangle(
                new Vector2d(0, 2),
                new Vector2d(2, 2),
                new Vector2d(1, -2)
        );

        assert !t.contains(new Vector2d(0, 0));
    }

    @Test
    public void test2() {
        Triangle t = new Triangle(
                new Vector2d(0, 2),
                new Vector2d(7, 5),
                new Vector2d(-3.5, -8)
        );

        assert !t.contains(new Vector2d(10, 0));
    }



    @Test
    public void test3() {
        Triangle t = new Triangle(
                new Vector2d(-10, -10),
                new Vector2d(0, 15),
                new Vector2d(10, -7.5)
        );

        assert t.contains(new Vector2d(2, 3));
    }

    @Test
    public void test4() {
        Triangle t = new Triangle(
                new Vector2d(7, 4),
                new Vector2d(-10, 6),
                new Vector2d(1, -2)
        );

        assert t.contains(new Vector2d(3, 4));
    }
}
