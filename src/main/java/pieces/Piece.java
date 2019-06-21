package pieces;

import java.util.List;

import pieces.Position.Degree;

public abstract class Piece {
    public enum Color {
        WHITE, BLACK, NOCOLOR;
    }

    public enum Type {
        PAWN('p', 1.0),
        ROOK('r', 5.0),
        KNIGHT('n', 2.5),
        BISHOP('b', 3.0),
        QUEEN('q', 9.0),
        KING('k', 0.0),
        NO_PIECE('.', 0.0);

        private char representation;
        private double defaultPoint;

        private Type(char representation, double defaultPoint) {
            this.representation = representation;
            this.defaultPoint = defaultPoint;
        }
        
        public double getDefaultPoint() {
            return defaultPoint;
        }

        public char getWhiteRepresentation() {
            return this.representation;
        }
        
        public char getBlackRepresentation() {
            return Character.toUpperCase(this.representation);
        }       
    }    
    
    private Color color;
    
    private Type type;

    private Position position;

    protected Piece(Color color, Type type, Position position) {
        this.color = color;
        this.type = type;
        this.position = position;
    }
    
    Color getColor() {
        return color;
    }
    
    Type getType() {
        return type;
    }
    
    public Position getPosition() {
        return position;
    }
    
    public char getRepresentation() {
        return isWhite() ? this.type.getWhiteRepresentation() : this.type.getBlackRepresentation();
    }
    
    boolean isWhite() {
        return matchColor(Color.WHITE);
    }
    
    boolean isBlack() {
        return matchColor(Color.BLACK);
    }
    
    public boolean matchColorAndType(Color color, Type type) {
        return matchColor(color) && matchType(type);
    }
    
    private boolean matchColor(Color color) {
        return this.color == color;
    }

    private boolean matchType(Type type) {
        return this.type == type;
    }
    
    public void addPiecesByColor(Color color, List<Piece> piecesByColor) {
        if (matchColor(color)) {
            piecesByColor.add(this);
        }
    }
    
    public double getPoint(List<Piece> pieces) {
        if (!matchType(Type.PAWN)) {
            return this.type.getDefaultPoint();
        }
        
        List<Position> columnNeighbors = this.position.getColumnNeighbors();
        for (Position position : columnNeighbors) {
            if (pieces.contains(Pawn.create(this.color, position))) {
                return this.type.getDefaultPoint() - 0.5;
            }
        }
        
        return this.type.getDefaultPoint();
    }
    
    public void move(Position target) {
        this.position = target;
    }
    
    public List<Position> verifyMovePosition(Piece target) throws InvalidMovePositionException {
        if (isSameTeam(target)) {
            throw new InvalidMovePositionException(target + " 위치는 이동할 수 없는 위치입니다.");
        }
        
        Direction direction = direction(position, target.position);
        return position.getMovablePositions(direction, target.position);
    }
    
    abstract Direction direction(Position source, Position target);
    
    public boolean isSameTeam(Piece target) {
        if (isWhite() && target.isWhite()) {
            return true;
        }
        
        if (isBlack() && target.isBlack()) {
            return true;
        }
        
        return false;
    }
    
    protected Degree degree(Position target) {
        return position.degree(target);
    }
    
    public String getSymbol() {
        return isWhite() ? getWhiteSymbol() : getBlackSymbol();
    }
    
    protected abstract String getWhiteSymbol();
    
    protected abstract String getBlackSymbol();
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((color == null) ? 0 : color.hashCode());
        result = prime * result + ((position == null) ? 0 : position.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        Piece other = (Piece) obj;
        if (color != other.color)
            return false;
        if (position == null) {
            if (other.position != null)
                return false;
        } else if (!position.equals(other.position))
            return false;
        if (type != other.type)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Piece [color=" + color + ", type=" + type + ", position=" + position + "]";
    }
}
