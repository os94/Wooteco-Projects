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
    void left() {
        assertThat(node.left()).isEqualTo(new Node(1, 0));
    }

    @Test
    void right() {
        assertThat(node.right()).isEqualTo(new Node(1, 2));
    }

    @Test
    void down() {
        assertThat(node.down()).isEqualTo(new Node(2, 1));
    }
}
