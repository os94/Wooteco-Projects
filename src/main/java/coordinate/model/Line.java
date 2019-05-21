package coordinate.model;

import java.util.List;

public class Line extends Figure {
    private List<Point> points;

    public Line(List<Point> points) {
        this.points = points;
    }
}
