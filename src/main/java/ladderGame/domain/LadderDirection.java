package ladderGame.domain;

public enum LadderDirection {
    LEFT {
        @Override
        public boolean canMove(Ladder ladder, Node currentNode) {
            if (ladder.atFirstColumn(currentNode)) {
                return false;
            }
            return currentNode.left().isConnected(ladder);
        }

        @Override
        public Node move(Node node) {
            return node.left().down();
        }
    },
    RIGHT {
        @Override
        public boolean canMove(Ladder ladder, Node currentNode) {
            if (ladder.atLastColumn(currentNode)) {
                return false;
            }
            return currentNode.isConnected(ladder);
        }

        @Override
        public Node move(Node node) {
            return node.right().down();
        }
    },
    DOWN {
        @Override
        public boolean canMove(Ladder ladder, Node currentNode) {
            if (ladder.atFirstColumn(currentNode)) {
                return !currentNode.isConnected(ladder);
            }
            if (ladder.atLastColumn(currentNode)) {
                return !currentNode.left().isConnected(ladder);
            }
            return !currentNode.left().isConnected(ladder)
                    && !currentNode.isConnected(ladder);
        }

        @Override
        public Node move(Node node) {
            return node.down();
        }
    };

    public static LadderDirection valueOf(Ladder ladder, Node currentNode) {
        for (LadderDirection direction : LadderDirection.values()) {
            if (direction.canMove(ladder, currentNode)) {
                return direction;
            }
        }
        throw new IllegalArgumentException();
    }

    public boolean canMove(Ladder ladder, Node currentNode) {
        return false;
    }

    public Node move(Node node) {
        return node;
    }
}
