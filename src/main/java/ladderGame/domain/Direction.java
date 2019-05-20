package ladderGame.domain;

public enum Direction {
    LEFT {
        @Override
        public boolean canMove(Ladder ladder, Node currentNode) {
            if (ladder.atFirstColumn(currentNode)) {
                return false;
            }
            return currentNode.previous().isConnected(ladder);
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
    },
    DOWN {
        @Override
        public boolean canMove(Ladder ladder, Node currentNode) {
            if (ladder.atFirstColumn(currentNode)) {
                return !currentNode.isConnected(ladder);
            }
            if (ladder.atLastColumn(currentNode)) {
                return !currentNode.previous().isConnected(ladder);
            }
            return !currentNode.previous().isConnected(ladder)
                    && !currentNode.isConnected(ladder);
        }
    };

    public boolean canMove(Ladder ladder, Node currentNode) {
        return false;
    }
}
