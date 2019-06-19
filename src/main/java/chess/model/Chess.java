package chess.model;

public enum Chess {
    KING(0) {
        @Override
        public boolean canMove(Point source, Point destination) {
            double xDistance = source.xDistanceFrom(destination);
            double yDistance = source.yDistanceFrom(destination);
            double distance = Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));

            return distance == 1 || distance == Math.sqrt(2);
        }
    },
    QUEEN(9) {
        @Override
        public boolean canMove(Point source, Point destination) {
            return ROOK.canMove(source, destination)
                    || BISHOP.canMove(source, destination);
        }
    },
    ROOK(5) {
        @Override
        public boolean canMove(Point source, Point destination) {
            return source.xDistanceFrom(destination) == 0
                    || source.yDistanceFrom(destination) == 0;
        }
    },
    BISHOP(3) {
        @Override
        public boolean canMove(Point source, Point destination) {
            double xDistance = source.xDistanceFrom(destination);
            double yDistance = source.yDistanceFrom(destination);
            double slope = yDistance / xDistance;

            return Math.abs(slope) == 1;
        }
    },
    KNIGHT(2.5) {
        @Override
        public boolean canMove(Point source, Point destination) {
            double xDistance = source.xDistanceFrom(destination);
            double yDistance = source.yDistanceFrom(destination);
            if (Math.abs(xDistance) + Math.abs(yDistance) != 3) {
                return false;
            }
            if (xDistance == 0 || yDistance == 0) {
                return false;
            }
            return true;
        }
    },
    Pawn(1) {
        @Override
        public boolean canMove(Point source, Point destination) {
            return false;
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

    public abstract boolean canMove(Point source, Point destination);
}
