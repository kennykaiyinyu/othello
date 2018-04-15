package othello;

import java.lang.StringBuilder;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

class Board {


    private PlayerSymbol[][] playerSymbols;




    public Board(final int rows, final int columns) {

        if (rows <= 1 || columns <= 1) {
            throw new IllegalArgumentException("rows and columns must be >= 1");
        }

        playerSymbols = new PlayerSymbol[rows][columns];

        for (int i = 0; i < playerSymbols.length; i++) {
            for (int j = 0; j < playerSymbols[i].length; j++) {
                playerSymbols[i][j] = PlayerSymbol.Neither;
            }
        }

    }

    public void set(int row, int column, PlayerSymbol playerSymbol) {
        playerSymbols[row][column] = playerSymbol;
    }

    public PlayerSymbol get(int row, int column) {
        return playerSymbols[row][column];
    }

    public int countPieces(PlayerSymbol player) {
        int counter = 0;
        for (int i = 0; i < playerSymbols.length; i++) {
            for (int j = 0; j < playerSymbols[i].length; j++) {
                if (playerSymbols[i][j] == player) {
                    counter++;
                }
            }
        }
        return counter;
    }

    public int getNumberOfRows() {
        return playerSymbols.length;
    }

    public int getNumberOfColumns() {
        return playerSymbols[0].length;
    }


    public String toString() {
        final String newLine = System.getProperty("line.separator");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < playerSymbols.length; i++) {
            sb.append("" + (i + 1) + " ");
            for (PlayerSymbol ps: playerSymbols[i]) {
                sb.append(ps.toString());
            }
            sb.append(newLine);
        }
        sb.append("  ");
        char colLabel = 'a';
        for (int i = 0; i < getNumberOfColumns(); i++) {

            sb.append(colLabel);
            colLabel = (char)((int)colLabel + 1);
        }
        sb.append(newLine);
        return sb.toString();
    }

    public boolean isAllCellsFilled() {
        for (PlayerSymbol[] row : playerSymbols) {
            for (PlayerSymbol cell: row) {
                if (cell == PlayerSymbol.Neither) {
                    return false;
                }
            }
        }

        return true;

    }

    public static Optional<Coordinate> parseCoordinate(String coordinateLabel) {

        coordinateLabel = coordinateLabel.trim();
        char c1 = coordinateLabel.charAt(0);
        char c2 = coordinateLabel.charAt(1);
        if (c1 >= '1' && c1 <= '8' && c2 >= 'a' && c2 <= 'h') {
            // part1 : row; part 2 : column
            return Optional.of(
                    new Coordinate(Integer.parseInt(""+c1) -1, ((int)c2) - ((int)'a'))
            );

        } else if (c2 >= '1' && c2 <= '8' && c1 >= 'a' && c1 <= 'h') {
            // part2 : row; part 1 : column

            return Optional.of(
                    new Coordinate(Integer.parseInt(""+c2) -1, ((int)c1) - ((int)'a'))
            );
        } else {
            return Optional.empty();
        }



    }




    public Set<Coordinate> findCapturableCoordinatesInBetween(Coordinate coord1, Coordinate coord2, PlayerSymbol playerSymbol) {

        int x1 = coord1.row;
        int y1 = coord1.column;

        int x2 = coord2.row;
        int y2 = coord2.column;

        Set<Coordinate> ret = new HashSet<Coordinate>();

        final HashSet<Coordinate> emptySet = new HashSet<Coordinate>();

        if (Math.abs(x1 - x2) != Math.abs(y1 - y2) && (x1 != x2 && y1 != y2)) {
            return emptySet;
        }


        int x = x1;
        int y = y1;
        int xStep = (x1 < x2) ? 1 : (x1 > x2) ? -1 : 0;
        int yStep = (y1 < y2) ? 1 : (y1 > y2) ? -1 : 0;
        while (true) {
            x = x + xStep;
            y = y + yStep;
            if (x == x2 && xStep != 0) break;
            if (y == y2 && yStep != 0) break;
            if (playerSymbols[x][y] != playerSymbol.oppositePlayer()) {
                return emptySet;
            }
            ret.add(new Coordinate(x, y));
        }

        return ret;



    }


    public Set<Coordinate> findClosestPiecesOfSamePlayerInAllDirections(PlayerSymbol playerSymbol, int row, int col) {

        Set<Coordinate> ret = new HashSet<Coordinate>();
        // find the closest symbol of the same player, upward in the same column
        for (int i = row - 1; i >= 0; i--) {
            if (playerSymbols[i][col].equals(playerSymbol)) {
                ret.add(new Coordinate(i, col));
                break;
            }
        }

        // find the closest symbol of the same player, downward in the same column
        for (int i = row + 1; i < getNumberOfRows(); i++) {
            if (playerSymbols[i][col].equals(playerSymbol)) {
                ret.add(new Coordinate(i, col));
                break;
            }
        }

        // find the closest symbol of the same player, on the left and in the same row
        for (int i = col - 1; i >= 0; i--) {
            if (playerSymbols[row][i].equals(playerSymbol)) {
                ret.add(new Coordinate(row, i));
                break;
            }
        }

        // find the closest symbol of the same player, on the right and in the same row
        for (int i = col + 1; i < getNumberOfColumns(); i++) {
            if (playerSymbols[row][i].equals(playerSymbol)) {
                ret.add(new Coordinate(row, i));
                break;
            }
        }

        // find the closest symbol of the same player, on top left diagonal
        for (int i = row - 1; i >= 0; i--) {
            int j = col - (row - i);
            if (j < 0) {
                break;
            }

            if (playerSymbols[i][j].equals(playerSymbol)) {
                ret.add(new Coordinate(i, j));
                break;
            }
        }




        // find the closest symbol of the same player, on bottom right diagonal
        for (int i = row + 1; i < getNumberOfRows(); i++) {
            int j = col + (i - row);
            if (j >= getNumberOfColumns()) {
                break;
            }

            if (playerSymbols[i][j].equals(playerSymbol)) {
                ret.add(new Coordinate(i, j));
                break;
            }
        }



        // find the closest symbol of the same player, on top right diagonal
        for (int i = row - 1; i >= 0; i--) {
            int j = col + (row - i);
            if (j >= getNumberOfColumns()) {
                break;
            }

            if (playerSymbols[i][j].equals(playerSymbol)) {
                ret.add(new Coordinate(i, j));
                break;
            }
        }


        // find the closest symbol of the same player, on bottom left diagonal
        for (int i = row + 1; i < getNumberOfRows(); i++) {
            int j = col - (i - row);
            if (j < 0) {

                break;
            }

            if (playerSymbols[i][j].equals(playerSymbol)) {
                ret.add(new Coordinate(i, j));
                break;
            }
        }



        return ret;

    }



    public void move(Coordinate coordinate, PlayerSymbol player) {

        Set<Coordinate> anotherEnds = findClosestPiecesOfSamePlayerInAllDirections(player, coordinate.row, coordinate.column);
        Set<Coordinate> capturable = new HashSet<Coordinate>();
        for (Coordinate c: anotherEnds) {
            capturable.addAll(findCapturableCoordinatesInBetween(c, new Coordinate(coordinate.row, coordinate.column), player));
        }
        if (!capturable.isEmpty()) {
            for (Coordinate c: capturable) {
                set(c.row, c.column, player);
            }
            set(coordinate.row, coordinate.column, player);
        }
    }

    public boolean isValidMove(Coordinate coordinate, PlayerSymbol player) {
        if (get(coordinate.row, coordinate.column) != PlayerSymbol.Neither) {
            return false;
        }
        Set<Coordinate> anotherEnds = findClosestPiecesOfSamePlayerInAllDirections(player, coordinate.row, coordinate.column);
        for (Coordinate c: anotherEnds) {
            if (!findCapturableCoordinatesInBetween(c, new Coordinate(coordinate.row, coordinate.column), player).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public boolean isStillHavingMove(PlayerSymbol player) {
        for (int i = 0; i < getNumberOfRows(); i++) {
            for (int j = 0; j < getNumberOfColumns(); j++) {
                if (get(i, j).equals(PlayerSymbol.Neither)) {

                    if (isValidMove(new Coordinate(i, j), player)) {
                        return true;
                    }

                }
            }

        }
        return false;
    }


    void draw(PrintStream pw) {
        pw.println(""+ this);
    }
}