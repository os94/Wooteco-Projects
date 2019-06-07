package coordinate.model;

import java.util.List;

public interface Figure {
    List<Point> getPoints();

    int size();

    String getName();

    double area();

    boolean hasPoint(Point other);

    String getAreaInfo();
}
