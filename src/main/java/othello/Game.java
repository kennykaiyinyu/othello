package othello;

import java.util.Optional;
import java.util.Scanner;

/**
 * Created by kennyyu on 4/15/2018.
 */
public class Game {
    private Board board;

    private PlayerSymbol playerOfThisRound;

    public Game() {
        this.playerOfThisRound = PlayerSymbol.X;
        board = new Board(8, 8);
        // initialize the board
        board.set(3, 3, PlayerSymbol.O);
        board.set(4, 4, PlayerSymbol.O);
        board.set(3, 4, PlayerSymbol.X);
        board.set(4, 3, PlayerSymbol.X);

    }

    public void startGame() {


        int passCounter = 0;    // if there are 2 consective round-pass, the game ends --- no one can place any valid move.


        while (!board.isAllCellsFilled() && passCounter < 2) {

            if (!board.isStillHavingMove(playerOfThisRound)) {
                passCounter += 1;
                playerOfThisRound = playerOfThisRound.oppositePlayer();
                continue;   // next round
            }
            passCounter = 0;

            board.draw(System.out);
            System.out.println();
            System.out.println();
            System.out.print("Player '" + playerOfThisRound + "' move: ");
            Scanner sc=new Scanner(System.in);

            String nextMove = sc.nextLine();
            Optional<Coordinate> coordOption =  Board.parseCoordinate(nextMove);
            boolean isValid = false;

            if (coordOption.isPresent()) {
                Coordinate c = coordOption.get();
                if (board.get(c.row, c.column).equals( PlayerSymbol.Neither) && board.isValidMove(c, playerOfThisRound)) {
                    board.move(c, playerOfThisRound);
                    playerOfThisRound = playerOfThisRound.oppositePlayer();
                    isValid = true;
                }




            }

            if (!isValid) {

                System.out.println("Invalid move. Please try again.");
                System.out.println();
                System.out.println();
            }



        }

        // end game.
        board.draw(System.out);

        System.out.println("No further moves available");
        int xCount = board.countPieces(PlayerSymbol.X) ;
        int oCount = board.countPieces(PlayerSymbol.O) ;
        if (xCount > oCount) {
            System.out.println("Player ‘X’ wins ( "+xCount+" vs "+oCount+" )");
        } else if (oCount > xCount) {
            System.out.println("Player ‘O’ wins ( "+oCount+" vs "+xCount+" )");
        } else {
            System.out.println("Draw ( Player X : "+xCount+" vs Player Y : "+oCount+" )");
        }


    }








}
