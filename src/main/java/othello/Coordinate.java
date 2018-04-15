package othello;

/**
 * Created by kennyyu on 4/15/2018.
 */
public class Coordinate {


    public final int row;
    public  final int column;



    @Override
    public int hashCode() {

        return row * 100 + column;
    }

    public Coordinate(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Coordinate) || o == null) {
            return false;
        }
        Coordinate anotherCoord = (Coordinate)o;
        return (anotherCoord.row == this.row && anotherCoord.column == this.column);

    }

    @Override
    public String toString() {
        return "(" + row + ", " + column + ")";
    }




}
