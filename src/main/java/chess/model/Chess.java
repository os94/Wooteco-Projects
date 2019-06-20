package chess.model;

import java.util.Map;

public enum Chess {
    KING(0) {
        @Override
        public boolean canMove(Point source, Point destination, Map<Boolean, User> users) {
            double xDistance = source.xDistanceFrom(destination);
            double yDistance = source.yDistanceFrom(destination);
            double distance = Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));

            return distance == 1 || distance == Math.sqrt(2);
        }
    },
    QUEEN(9) {
        @Override
        public boolean canMove(Point source, Point destination, Map<Boolean, User> users) {
            return ROOK.canMove(source, destination, users)
                    || BISHOP.canMove(source, destination, users);
        }
    },
    ROOK(5) {
        @Override
        public boolean canMove(Point source, Point destination, Map<Boolean, User> users) {
            return source.xDistanceFrom(destination) == 0
                    || source.yDistanceFrom(destination) == 0;
        }
    },
    BISHOP(3) {
        @Override
        public boolean canMove(Point source, Point destination, Map<Boolean, User> users) {
            double xDistance = source.xDistanceFrom(destination);
            double yDistance = source.yDistanceFrom(destination);
            double slope = yDistance / xDistance;

            return Math.abs(slope) == 1;
        }
    },
    KNIGHT(2.5) {
        @Override
        public boolean canMove(Point source, Point destination, Map<Boolean, User> users) {
            double xDistance = source.xDistanceFrom(destination);
            double yDistance = source.yDistanceFrom(destination);
            if (Math.abs(xDistance) + Math.abs(yDistance) != 3) {
                return false;
            }
            return !(xDistance == 0) && !(yDistance == 0);
        }
    },
    Pawn(1) {
        public boolean canMove(Point source, Point destination, Map<Boolean, User> users) {
            int direction = users.get(true).isContain(source) ? 1 : -1;
            int initialYOfPawn = users.get(true).isContain(source) ? 2 : 7;

            if (checkDirection(source, destination, direction)
                    && !users.containsValue(source.next(0, direction))) {
                return true;
            }

            if (checkDirection(source, destination, direction * 2)
                    && !users.containsValue(source.next(0, direction * 2))
                    && !users.containsValue(source.next(0, direction))
                    && source.getY() == initialYOfPawn) {
                return true;
            }

            // Todo 대각선 이동 로직 수정
            return users.containsValue(source.next(-direction, direction))
                    || users.containsValue(source.next(direction, direction));
        }

        private boolean checkDirection(Point source, Point destination, int expectingYDifference) {
            return source.xDistanceFrom(destination) == 0
                    && source.yDistanceFrom(destination) == expectingYDifference;
        }
    };

    private double score;

    Chess(double score) {
        this.score = score;
    }

    Chess() {
    }

    public double getScore() {
        return score;
    }

    public abstract boolean canMove(Point source, Point destination, Map<Boolean, User> users);
}
