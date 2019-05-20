package ladderGame.domain;

public enum Direction {
    LEFT {
        @Override
        public boolean canMove(Ladder ladder, Node currentNode) {
            if (ladder.atFirstColumn(currentNode)) {
                return false;
            }
            return ladder.isConnected(currentNode.previous());
        }
    },
    RIGHT {
        @Override
        public boolean canMove(Ladder ladder, Node currentNode) {
            if (ladder.atLastColumn(currentNode)) {
                return false;
            }
            return ladder.isConnected(currentNode);
        }
    },
    DOWN {
        @Override
        public boolean canMove(Ladder ladder, Node currentNode) {
            if (ladder.atFirstColumn(currentNode)) {
                return !ladder.isConnected(currentNode);
            }
            if (ladder.atLastColumn(currentNode)) {
                return !ladder.isConnected(currentNode.previous());
            }
            return !ladder.isConnected(currentNode.previous())
                    && !ladder.isConnected(currentNode);
        }
    };

    public boolean canMove(Ladder ladder, Node currentNode) {
        return false;
    }
}
