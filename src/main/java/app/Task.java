package app;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.humbleui.jwm.MouseButton;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import lombok.Getter;
import misc.CoordinateSystem2d;
import misc.CoordinateSystem2i;
import misc.Vector2d;
import misc.Vector2i;
import panels.PanelLog;

import java.util.ArrayList;


/**
 * Класс задачи
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
public class Task {
    /**
     * Текст задачи
     */
    public static final String TASK_TEXT = """
            ПОСТАНОВКА ЗАДАЧИ:
            На плоскости задано множество треугольников.
            Найти такие два треугольника, что площадь фигуры,
            находящейся внутри обоих треугольников,
            будет максимальна. В качестве ответа:
            выделить найденные два треугольника,
            выделить контур фигуры,находящейся внутри обоих     
            треугольников,желательно "залить цветом"
             внутреннее пространство этой фигуры.
            """;


    /**
     * Очистить задачу
     */
    public void clear() {
        solved = false;
        triangles.clear();
    }

    /**
     * проверка, решена ли задача
     *
     * @return флаг
     */
    public boolean isSolved() {
        return solved;
    }

    /**
     * Список точек
     */
    @Getter
    private final ArrayList<Triangle> triangles;
    /**
     * Флаг, решена ли задача
     */
    private boolean solved;

    /**
     * Получить  тип мира
     *
     * @return тип мира
     */
    public CoordinateSystem2d getOwnCS() {
        return ownCS;
    }

    /**
     * Получить название мира
     *
     * @return название мира
     */
    public ArrayList<Triangle> getTriangles() {
        return triangles;
    }

    /**
     * Вещественная система координат задачи
     */
    @Getter
    private final CoordinateSystem2d ownCS;

    /**
     * Решить задачу
     */
    public void solve() {

        solved = true;
    }

    /**
     * Отмена решения задачи
     */
    public void cancel() {
        solved = false;

    }

    /**
     * Размер точки
     */
    private static final int POINT_SIZE = 3;
    /**
     * последняя СК окна
     */
    protected CoordinateSystem2i lastWindowCS;

    /**
     * Задача
     *
     * @param ownCS     СК задачи
     * @param triangles массив точек
     */
    @JsonCreator
    public Task(
            @JsonProperty("ownCS") CoordinateSystem2d ownCS,
            @JsonProperty("triangles") ArrayList<Triangle> triangles
    ) {
        this.ownCS = ownCS;
        this.triangles = triangles;
    }

    int addPos = 0;

    /**
     * Клик мыши по пространству задачи
     *
     * @param pos         положение мыши
     * @param mouseButton кнопка мыши
     */
    public void click(Vector2i pos, MouseButton mouseButton) {
        if (lastWindowCS == null) return;
        // получаем положение на экране
        Vector2d taskPos = ownCS.getCoords(pos, lastWindowCS);
        addPoint(taskPos, addPos);
        addPos++;
        if (addPos > 2)
            addPos = 0;
    }

    Vector2d[] ps = new Vector2d[3];


    /**
     * обавление через панель управления
     *
     * @param pos положение мыши
     * @param num номер точки
     */
    public void addPoint(Vector2d pos, int num) {
        if (num < 2)
            ps[num] = pos;
        else
            triangles.add(new Triangle(ps[0], ps[1], pos));

        PanelLog.info(pos.toString() + " " + num);
        // todo: написать добавление треугольника по клику мышью по кнопке


    }

    /**
     * Добавить случайные точки
     *
     * @param cnt кол-во случайных точек
     */
    public void addRandomTriangles(int cnt) {
        // если создавать точки с полностью случайными координатами,
        // то вероятность того, что они совпадут крайне мала
        // поэтому нужно создать вспомогательную малую целочисленную ОСК
        // для получения случайной точки мы будем запрашивать случайную
        // координату этой решётки (их всего 30х30=900).
        // после нам останется только перевести координаты на решётке
        // в координаты СК задачи

        // повторяем заданное количество раз
        for (int i = 0; i < cnt; i++) {
            // получаем случайные координаты на решётке
            Vector2d pos = ownCS.getRandomCoords();
            Vector2d pos2 = ownCS.getRandomCoords();
            Vector2d pos3 = ownCS.getRandomCoords();
            triangles.add(new Triangle(pos, pos2, pos3));
        }
    }

    /**
     * Рисование задачи
     *
     * @param canvas   область рисования
     * @param windowCS СК окна
     */
    public void paint(Canvas canvas, CoordinateSystem2i windowCS) {
        // Сохраняем последнюю СК
        lastWindowCS = windowCS;

        canvas.save();
            for (Triangle t : triangles) {
                t.paint(canvas, windowCS, ownCS);
            }

        canvas.restore();
    }

}