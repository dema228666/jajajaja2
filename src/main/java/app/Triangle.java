package app;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import misc.*;

import java.util.Objects;

/**
 * Класс точки
 */
public class Triangle {

    /**
     * Координаты точки A
     */
    public final Vector2d posA;
    /**
     * Координаты точки B
     */
    public final Vector2d posB;
    /**
     * Координаты точки C
     */
    public final Vector2d posC;

    /**
     * Конструктор точки
     *
     * @param posA положение точки A
     * @param posB положение точки B
     * @param posC положение точки C
     */
    public Triangle(Vector2d posA, Vector2d posB, Vector2d posC) {
        this.posA = posA;
        this.posB = posB;
        this.posC = posC;
    }

    /**
     * Провекра, содержится ли точка v внутри треугольника
     *
     * @param v - координаты точки
     * @return - флаг, содержится ли точка v внутри треугольника
     */
    public boolean contains(Vector2d v) {
        double x0 = v.x;
        double y0 = v.y;

        double x1 = posA.x;
        double y1 = posA.y;
        double x2 = posB.x;
        double y2 = posB.y;
        double x3 = posC.x;
        double y3 = posC.y;

        double a = (x1 - x0) * (y2 - y1) - (x2 - x1) * (y1 - y0);
        double b = (x2 - x0) * (y3 - y2) - (x3 - x2) * (y2 - y0);
        double c = (x3 - x0) * (y1 - y3) - (x1 - x3) * (y3 - y0);

        if((a>0&&b>0&&c>0)||(a<0&&b<0&&c<0))
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    public void paint(Canvas canvas, CoordinateSystem2i windowCS, CoordinateSystem2d ownCS) {
        // создаём перо
        try (Paint p = new Paint()) {
            p.setColor(getColor());
            // вершины треугольника
            Vector2i pointA = windowCS.getCoords(posA, ownCS);
            Vector2i pointB = windowCS.getCoords(posB, ownCS);
            Vector2i pointC = windowCS.getCoords(posC, ownCS);
            // рисуем его стороны
            canvas.drawLine(pointA.x, pointA.y, pointB.x, pointB.y, p);
            canvas.drawLine(pointB.x, pointB.y, pointC.x, pointC.y, p);
            canvas.drawLine(pointC.x, pointC.y, pointA.x, pointA.y, p);
        }
    }

    /**
     * Получить цвет точки по её множеству
     *
     * @return цвет точки
     */
    public int getColor() {
        return Misc.getColor(0xCC, 0xFF, 0x00, 0xFF);
    }


    /**
     * Получить положение A
     * (нужен для json)
     *
     * @return положение
     */
    public Vector2d getPosA() {
        return posA;
    }
    /**
     * Получить положение B
     * (нужен для json)
     *
     * @return положение
     */
    public Vector2d getPosB() {
        return posB;
    }
    /**
     * Получить положение C
     * (нужен для json)
     *
     * @return положение
     */
    public Vector2d getPosC() {
        return posC;
    }

    /**
     * Строковое представление объекта
     *
     * @return строковое представление объекта
     */
    @Override
    public String toString() {
        return "Point{" +
                ", pos=" + posA +
                ", pos=" + posB +
                ", pos=" + posC +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triangle triangle = (Triangle) o;
        return Objects.equals(posA, triangle.posA) && Objects.equals(posB, triangle.posB) && Objects.equals(posC, triangle.posC);
    }

    @Override
    public int hashCode() {
        return Objects.hash(posA, posB, posC);
    }

}
