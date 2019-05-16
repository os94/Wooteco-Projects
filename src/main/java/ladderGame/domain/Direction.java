package ladderGame.domain;

public enum Direction {
    LEFT {
        @Override
        public boolean canMove(Ladder ladder, Node currentNode) {
            if (ladder.atFirstColumn(currentNode)) {
                return false;
            }
            return ladder.checkBridge(currentNode.getRow(), currentNode.getColumn() - 1);
        }
    },
    RIGHT {
        @Override
        public boolean canMove(Ladder ladder, Node currentNode) {
            if (ladder.atLastColumn(currentNode)) {
                return false;
            }
            return ladder.checkBridge(currentNode.getRow(), currentNode.getColumn());
        }
    },
    DOWN {
        @Override
        public boolean canMove(Ladder ladder, Node currentNode) {
            if (ladder.atFirstColumn(currentNode)) {
                return !ladder.checkBridge(currentNode.getRow(), currentNode.getColumn());
            }
            if (ladder.atLastColumn(currentNode)) {
                return !ladder.checkBridge(currentNode.getRow(), currentNode.getColumn() - 1);
            }

            return !(ladder.checkBridge(currentNode.getRow(), currentNode.getColumn() - 1)
                    || ladder.checkBridge(currentNode.getRow(), currentNode.getColumn()));
        }
    };

    public boolean canMove(Ladder ladder, Node currentNode) {
        return false;
    }
}
