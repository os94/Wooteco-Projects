package ladderGame.domain;

public enum Direction {
    LEFT {
        @Override
        public boolean canMove(Ladder ladder, Node currentNode) {
            if (ladder.atFirstColumn(currentNode)) {
                return false;
            }
            return ladder.isConnected(currentNode.getRow(), currentNode.getColumn() - 1);
        }
    },
    RIGHT {
        @Override
        public boolean canMove(Ladder ladder, Node currentNode) {
            if (ladder.atLastColumn(currentNode)) {
                return false;
            }
            return ladder.isConnected(currentNode.getRow(), currentNode.getColumn());
        }
    },
    DOWN {
        @Override
        public boolean canMove(Ladder ladder, Node currentNode) {
            Boolean hasLeft = ladder.isConnected(currentNode.getRow(), currentNode.getColumn() - 1);
            Boolean hasRight = ladder.isConnected(currentNode.getRow(), currentNode.getColumn());
            if (ladder.atFirstColumn(currentNode)) {
                return !hasRight;
            }
            if (ladder.atLastColumn(currentNode)) {
                return !hasLeft;
            }
            return !hasLeft && !hasRight;
        }
    };

    public boolean canMove(Ladder ladder, Node currentNode) {
        return false;
    }
}
