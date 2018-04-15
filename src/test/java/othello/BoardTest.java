package othello;


import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by kennyyu on 4/15/2018.
 */
public class BoardTest {
    @Test
    public void testBoardCreate() {

        /*
        This is to test if the orignally created board has all the cells initialized as "PlayerSymbol.Neither"
         */

        Board b = new Board(2, 2);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                Assert.assertEquals(PlayerSymbol.Neither, b.get(i, j));
            }
        }


    }

    @Test
    public void testBoardModification() {
        /*
        This is to test the effect of the cells if having called board.set(0, 1, PlayerBoard.O)
         */
        Board b = new Board(2, 2);
        b.set(0, 1, PlayerSymbol.O);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (i == 0 && j == 1) {
                    Assert.assertEquals(PlayerSymbol.O, b.get(i, j));
                } else {
                    Assert.assertEquals(PlayerSymbol.Neither, b.get(i, j));
                }
            }
        }
    }

    @Test
    public void testBoardPresentation() {
        /*
        This is to test if the board.toString works as expected.
         */
        final String NEW_LINE = System.getProperty("line.separator");
        Board b = new Board(2, 2);
        b.set(0, 1, PlayerSymbol.X);
        StringBuilder sb = new StringBuilder();
        String expected = sb.
                append ("1 -X").append(NEW_LINE).
                append("2 --").append(NEW_LINE).
                append("  ab").append(NEW_LINE).
                toString();
        Assert.assertEquals(expected, b.toString());

    }

    @Test
    public void testAllClosestPiecesOfSamePlayer() {
        Board b = new Board(8, 8);
        b.set(3, 3, PlayerSymbol.X);
        b.set(4, 2, PlayerSymbol.X);
        b.set(5, 1, PlayerSymbol.X);
        b.set(0, 6, PlayerSymbol.X);
        b.set(1, 5, PlayerSymbol.X);
        b.set(5, 5, PlayerSymbol.X);
        b.set(3, 2, PlayerSymbol.X);
        b.set(3, 7, PlayerSymbol.X);
        b.set(3, 4, PlayerSymbol.X);

        b.set(1, 3, PlayerSymbol.X);
        b.set(2, 3, PlayerSymbol.X);
        b.set(4, 6, PlayerSymbol.X);
        b.set(5, 6, PlayerSymbol.X);
        b.set(2, 2, PlayerSymbol.X);
        b.set(2, 1, PlayerSymbol.X);

        Set<Coordinate> closeetPieces = b.findClosestPiecesOfSamePlayerInAllDirections(PlayerSymbol.Neither.X, 3, 3);

        Set<Coordinate> expected = new HashSet<Coordinate>();
        expected.add(new Coordinate(5, 5));
        expected.add(new Coordinate(3, 2));

        expected.add(new Coordinate(2, 2));
        expected.add(new Coordinate(1, 5));
        expected.add(new Coordinate(4, 2));
        expected.add(new Coordinate(2, 3));
        expected.add(new Coordinate(3, 4));

        Assert.assertEquals(expected, closeetPieces);

    }

    @Test
    public void testFindAllCapturablePiecesx() {
        Board b = new Board(8, 8);

        b.set(3, 3, PlayerSymbol.X);
        b.set(4, 4, PlayerSymbol.O);
        b.set(5, 5, PlayerSymbol.O);
        b.set(2, 4, PlayerSymbol.O);
        b.set(0, 6, PlayerSymbol.O);
        b.set(5, 1, PlayerSymbol.O);
        b.set(2, 3, PlayerSymbol.O);
        b.set(4, 3, PlayerSymbol.O);
        b.set(3, 2, PlayerSymbol.O);
        b.set(3, 1, PlayerSymbol.X);
        b.set(3, 4, PlayerSymbol.O);
        b.set(3, 5, PlayerSymbol.O);
        Set<Coordinate> setA = b.findCapturableCoordinatesInBetween(new Coordinate(3, 3), new Coordinate(4, 4), PlayerSymbol.X);
        Assert.assertEquals(setA, new HashSet<Coordinate>());

        Set<Coordinate> setB = b.findCapturableCoordinatesInBetween(new Coordinate(3, 3), new Coordinate(5, 5), PlayerSymbol.X);
        Set<Coordinate> expectedSetB = new HashSet<Coordinate>();
        expectedSetB.add(new Coordinate(4, 4));
        Assert.assertEquals(expectedSetB, setB);

        Set<Coordinate> setC = b.findCapturableCoordinatesInBetween(new Coordinate(3, 3), new Coordinate(3, 1), PlayerSymbol.X);
        Set<Coordinate> expectedSetC = new HashSet<Coordinate>();
        expectedSetC.add(new Coordinate(3, 2));
        Assert.assertEquals(expectedSetC, setC);

        Set<Coordinate> setD = b.findCapturableCoordinatesInBetween(new Coordinate(3, 3), new Coordinate(3, 0), PlayerSymbol.X);
        Set<Coordinate> expectedSetD = new HashSet<Coordinate>();
        Assert.assertEquals(expectedSetD, setD);

        Set<Coordinate> setE= b.findCapturableCoordinatesInBetween(new Coordinate(3, 3), new Coordinate(5, 0), PlayerSymbol.X);
        Set<Coordinate> expectedSetE = new HashSet<Coordinate>();
        Assert.assertEquals(expectedSetE, setE);


        //b.set(5, 1, PlayerSymbol.O);

    }

    @Test
    public void testParsingLabelIntoCoordinate() {
        Assert.assertEquals(Optional.of(new Coordinate(0, 0)), Board.parseCoordinate("1a"));
        Assert.assertEquals(Optional.of(new Coordinate(0, 6)), Board.parseCoordinate("1g"));
        Assert.assertEquals(Optional.of(new Coordinate(0, 7)), Board.parseCoordinate("1h"));
        Assert.assertEquals(Optional.of(new Coordinate(4, 6)), Board.parseCoordinate("5g"));
        Assert.assertEquals(Optional.of(new Coordinate(7, 0)), Board.parseCoordinate("8a"));
        Assert.assertEquals(Optional.empty(), Board.parseCoordinate("9g"));
        Assert.assertEquals(Optional.empty(), Board.parseCoordinate("8i"));

    }


    @Test
    public void testIsValidMove() {
        Board b = new Board(8, 8);

        b.set(3, 3, PlayerSymbol.X);
        b.set(4, 4, PlayerSymbol.O);
        b.set(5, 5, PlayerSymbol.O);
        b.set(2, 4, PlayerSymbol.O);
        b.set(0, 6, PlayerSymbol.O);
        b.set(5, 1, PlayerSymbol.O);
        b.set(2, 3, PlayerSymbol.O);
        b.set(4, 3, PlayerSymbol.O);
        b.set(3, 2, PlayerSymbol.O);
        b.set(3, 1, PlayerSymbol.X);
        b.set(3, 4, PlayerSymbol.O);
        b.set(3, 5, PlayerSymbol.O);

        Assert.assertEquals(true, b.isValidMove(new Coordinate(6, 6), PlayerSymbol.X));
        Assert.assertEquals(false, b.isValidMove(new Coordinate(6, 6), PlayerSymbol.O));

        Assert.assertEquals(false, b.isValidMove(new Coordinate(5, 5), PlayerSymbol.X));
        Assert.assertEquals(false, b.isValidMove(new Coordinate(5, 5), PlayerSymbol.O));

        Assert.assertEquals(true, b.isValidMove(new Coordinate(1, 5), PlayerSymbol.X));
        Assert.assertEquals(false, b.isValidMove(new Coordinate(1, 5), PlayerSymbol.O));

    }


    @Test
    public void testMoves() {
        Board b = new Board(8, 8);

        b.set(3, 3, PlayerSymbol.X);
        b.set(4, 4, PlayerSymbol.O);
        b.set(5, 5, PlayerSymbol.O);
        b.set(2, 4, PlayerSymbol.O);
        b.set(0, 6, PlayerSymbol.O);
        b.set(5, 1, PlayerSymbol.O);
        b.set(2, 3, PlayerSymbol.O);
        b.set(4, 3, PlayerSymbol.O);
        b.set(3, 2, PlayerSymbol.O);
        b.set(3, 1, PlayerSymbol.X);
        b.set(3, 4, PlayerSymbol.O);
        b.set(3, 5, PlayerSymbol.O);


        Assert.assertEquals(true, b.isValidMove(new Coordinate(6, 6), PlayerSymbol.X));
        Assert.assertEquals(false, b.isValidMove(new Coordinate(6, 6), PlayerSymbol.O));

        Assert.assertEquals(false, b.isValidMove(new Coordinate(5, 5), PlayerSymbol.X));
        Assert.assertEquals(false, b.isValidMove(new Coordinate(5, 5), PlayerSymbol.O));

        Assert.assertEquals(true, b.isValidMove(new Coordinate(1, 5), PlayerSymbol.X));
        Assert.assertEquals(false, b.isValidMove(new Coordinate(1, 5), PlayerSymbol.O));

    }
    @Test
    public void testPiecesCounting() {
        Board b = new Board(8, 8);

        b.set(3, 3, PlayerSymbol.X);
        b.set(4, 4, PlayerSymbol.O);
        b.set(5, 5, PlayerSymbol.O);
        b.set(2, 4, PlayerSymbol.O);
        b.set(0, 6, PlayerSymbol.O);
        b.set(5, 1, PlayerSymbol.X);
        b.set(2, 3, PlayerSymbol.O);
        b.set(4, 3, PlayerSymbol.O);
        b.set(3, 2, PlayerSymbol.O);
        b.set(3, 1, PlayerSymbol.X);
        b.set(3, 4, PlayerSymbol.O);
        b.set(3, 5, PlayerSymbol.O);


        Assert.assertEquals(3, b.countPieces(PlayerSymbol.X));
        Assert.assertEquals(9, b.countPieces(PlayerSymbol.O));
    }





}
