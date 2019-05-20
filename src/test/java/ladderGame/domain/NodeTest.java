package ladderGame.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NodeTest {
    Node node;

    @BeforeEach
    void setUp() {
        node = new Node(1,1);
    }

    @Test
    void move_left() {
        node.move(Direction.LEFT);
        assertThat(node).isEqualTo(new Node(2, 0));
    }

    @Test
    void move_right() {
        node.move(Direction.RIGHT);
        assertThat(node).isEqualTo(new Node(2, 2));
    }

    @Test
    void move_down() {
        node.move(Direction.DOWN);
        assertThat(node).isEqualTo(new Node(2, 1));
    }

    @Test
    void previous() {
        assertThat(node.previous()).isEqualTo(new Node(1, 0));
    }
}
