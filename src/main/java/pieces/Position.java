package pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Position {
    private static final String X_ALPHABET = "abcdefgh";
    public static final int ROW_SIZE = 8;
    public static final int COLUMN_SIZE = 8;
    
    private int x;
    private int y;
    
    public Position(String position) {
        if (position.length() != 2) {
            throw new InvalidPositionException(position + "은 유효한 위치가 아닙니다.");
        }
        
        this.x = getX(position);
        this.y = getY(position);
        
        valid(x, y);
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        
        valid(x, y);
    }
    
    private void valid(int x, int y) {
        if (x < 0 || x >= COLUMN_SIZE) {
            throw new InvalidPositionException(String.format("X : %d, Y : %d 는 유효한 위치가 아닙니다.", x, y));
        }
        
        if (y < 0 || y >= ROW_SIZE) {
            throw new InvalidPositionException(String.format("X : %d, Y : %d 는 유효한 위치가 아닙니다.", x, y));
        }
    }
    
    private static int getX(String position) {
        return position.charAt(0) - 'a';
    }
    
    private static int getY(String position) {
        return Character.getNumericValue(position.charAt(1)) - 1;
    }

    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public String getCharPosition() {
        return X_ALPHABET.charAt(x) + "" + (y + 1);
    }
    
    public Position movePosition(Direction direction) {
        return new Position(x + direction.getXDegree(), y + direction.getYDegree());
    }

    public List<Position> getColumnNeighbors() {
        List<Position> columnNeighbors = new ArrayList<>();
        Optional<Position> position = createPosition(getX(), getY() - 1);
        if (position.isPresent()) {
            columnNeighbors.add(position.get());
        }
        
        position = createPosition(getX(), getY() + 1);
        if (position.isPresent()) {
            columnNeighbors.add(position.get());
        }
        return columnNeighbors;
    }
    
    private Optional<Position> createPosition(int x, int y) {
        try {
            return Optional.of(new Position(x, y));
        } catch (InvalidPositionException e) {
            return Optional.empty();
        }
    }

    public List<Position> getMovablePositions(Direction direction, Position target) {
        List<Position> movablePositions = new ArrayList<>();
        getMovablePositions(movablePositions, direction, target);
        return movablePositions;
    }

    private void getMovablePositions(List<Position> movablePositions, Direction direction, Position target) {
        Position movablePosition = movePosition(direction); 
        if (!movablePosition.equals(target)) {
            movablePositions.add(movablePosition);
            movablePosition.getMovablePositions(movablePositions, direction, target);
        }
    }
    
    public Degree degree(Position target) {
        return new Degree(target.x - this.x, target.y - this.y);
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Position other = (Position) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Position [x=" + x + ", y=" + y + "]";
    }
    
    public static class Degree {
        private int xDegree;
        private int yDegree;

        private Degree(int xDegree, int yDegree) {
            this.xDegree = xDegree;
            this.yDegree = yDegree;
        }
        
        public int getXDegree() {
            return xDegree;
        }
        
        public int getYDegree() {
            return yDegree;
        }
        
        public Degree getDegreeOne() {
            return new Degree(convertToOne(xDegree), convertToOne(yDegree));
        }

        public boolean isOverOneXDegree() {
            return xDegree > 1;
        }
        
        public boolean isOverOneYDegree() {
            return yDegree > 1;
        }
        
        public boolean isUnderThreeYDegree() {
            return yDegree > -3 && yDegree < 3;
        }
        
        public boolean isLinear() {
            return xDegree == 0 || yDegree == 0;
        }

        public boolean isDiagonal() {
            try {
                int remainder = xDegree % yDegree;
                return remainder == 0;
            } catch (ArithmeticException e) {
                return false;
            }
        }
        
        public static Degree of(int xDegree, int yDegree) {
            return new Degree(xDegree, yDegree);
        }

        public static int convertToOne(int number) {
            if (number == 0) {
                return 0;
            }
            
            if (number > 0) {
                return 1;
            }
            
            return -1;
        }
    }    
}
