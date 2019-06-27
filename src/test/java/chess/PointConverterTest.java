package chess;

import chess.model.Point;
import chess.util.PointConverter;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PointConverterTest {
    @Test
    void 포인트_변환_확인() {
        assertThat(PointConverter.convertToPoint("1, 2")).isEqualTo(Point.of(1, 2));
    }
}
