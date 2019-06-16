package coordinate.model;

import java.util.List;
import java.util.Map;

public interface Figure {
    List<Point> getPoints();

    int size();

    String getName();

    double area();

    boolean hasPoint(Point other);

    String getAreaInfo();

    Map<Integer, Integer> getXYCoordinates();
}
