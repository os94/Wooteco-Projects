package coordinate;

import coordinate.model.Point;

public class Message {
    public static final String INPUT_COORDINATE = "좌표를 입력하세요.";

    public static final String OUTPUT_AREA_OF_LINE = "두 점 사이의 거리는 ";
    public static final String OUTPUT_AREA_OF_RECTANGLE = "사각형의 넓이는 ";

    public static final String ERROR_OUT_OF_POINT_RANGE
            = "잘못된 범위의 입력값입니다. 정수 범위는 " + Point.LOWER_LIMIT + " ~ " + Point.UPPER_LIMIT + " 사이의 수로 입력해 주세요.";
    public static final String ERROR_INVALID_COORDINATES = "올바르지 않은 입력값입니다.";
    public static final String ERROR_INVALID_FIGURE_CREATION = "입력된 Point 개수가 유효하지 않습니다.";
    public static final String ERROR_FIGURE_NULL = "Point 입력값이 없습니다.";
    public static final String ERROR_DUPLICATE_POINTS = "중복된 좌표가 존재합니다.";
    public static final String ERROR_INVALID_RECTANGLE = "직사각형 모양이 아닙니다.";
}
