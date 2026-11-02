import java.util.Scanner;

public class Main {
    //board size constants and board itslf
    //outisde main so every method can use them
    private static final int ROWS = 3;
    private static final int COLS = 3;
    private static String[][] board = new String[ROWS][COLS];

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String player = "X";        //current player, x alawys goes first
        int row = 0;                //row the player picks
        int col = 0;                //column the player picks
        int moveCount = 0;          //how many moves have been made
        boolean gameOver = false;
        boolean playAgain = false;

        //outer loop: play a new game until the players say N
        do {
            //start new game, clear board, x goes first
            clearBoard();
            player = "X";
            moveCount = 0;
            gameOver = false;

            do {
                display();

                //get a move, keep asking until the square is empty
                do {
                    //players enter 1-3, then subtract 1 to get the array index 0-2
                    row = SafeInput.getRangedInt(input, "Player " + player + ", enter your row", 1, 3) - 1;
                    col = SafeInput.getRangedInt(input, "Player " + player + ", enter your column", 1, 3) - 1;

                    if (!isValidMove(row, col)) {
                        System.out.println("That square is already taken, pick another one.");
                    }

                }
                while (!isValidMove(row, col));


                //put the players mark on the board
                board[row][col] = player;
                moveCount++;

                //a win is only possible after at least 5 moves
                if (moveCount >= 5 && isWin(player)) {
                    display();
                    System.out.println("Player " + player + " wins!");
                    gameOver = true;
                }

                //a tie is only possible after at least 7 moves
                else if (moveCount >= 7 && isTie()) {
                    display();
                    System.out.println("It's a tie!");
                    gameOver = true;
                } else            //switch players: x becomes o, o becomes x
                {
                    if (player.equals("X")) {
                        player = "O";
                    } else {
                        player = "X";
                    }
                }

            }
            while (!gameOver);

            playAgain = SafeInput.getYNConfirm(input, "Do you want to play again?");

        }
        while (playAgain);

        System.out.println("\nThanks for playing!");
    }


    //sets every square on the board to a blank space
    private static void clearBoard() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                board[r][c] = " ";
            }
        }
    }

    //prints the board w row and column numbers
    private static void display() {
        System.out.println("\n    1    2    3");
        for (int r = 0; r < ROWS; r++) {
            System.out.printf("%d    %s | %s |%s%n", r + 1, board[r][0], board[r][1], board[r][2]);
            if (r < ROWS - 1) {
                System.out.println("   ---+---+---");
            }
        }
    }

    //returns true if the sqaure is empty (a legal move)
    private static boolean isValidMove(int row, int col) {
        return board[row][col].equals(" ");
    }

    //returns true if the player has any kind of win
    private static boolean isWin(String player) {
        if (isColWin(player) || isRowWin(player) || isDiagWin(player)) {
            return true;
        }
        return false;
    }


    //checks each column for 3 of the players marks
    private static boolean isColWin(String player) {
        for (int c = 0; c < COLS; c++) {
            if (board[0][c].equals(player) && board[1][c].equals(player) && board[2][c].equals(player)) {
                return true;
            }
        }
        return false;
    }


    //checks each row for 3 of the players marks
    private static boolean isRowWin(String player) {
        for (int r = 0; r < ROWS; r++) {
            if (board[r][0].equals(player) && board[r][1].equals(player) && board[r][2].equals(player)) {
                return true;
            }
        }
        return false;
    }

    //checks both diagonals for 3 of the players marks
    private static boolean isDiagWin(String player) {
        //top left to bottom
        if (board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player)) {
            return true;
        }

        //top right to bottom left
        if (board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player)) {
            return true;
        }
        return false;
    }

    //returns true if nobody can win anymore
//every row, column, and diagonal has both an x and o in it
    private static boolean isTie() {
        boolean hasX = false;
        boolean hasO = false;

        //check the 3 rows
        for (int r = 0; r < ROWS; r++) {
            hasX = board[r][0].equals("X") || board[r][1].equals("X") || board[r][2].equals("X");
            hasO = board[r][0].equals("O") || board[r][1].equals("O") || board[r][2].equals("O");
            if (!hasX || !hasO) {
                return false;       //this row could still be won
            }
        }

        //check the 3 columns
        for (int c = 0; c < COLS; c++) {
            hasX = board[0][c].equals("X") || board[1][c].equals("X") || board[2][c].equals("X");
            hasO = board[0][c].equals("O") || board[1][c].equals("O") || board[2][c].equals("O");
            if (!hasX || !hasO) {
                return false;           //this column could still be won
            }
        }

        //check the diagonal from top left to bottom right
        hasX = board[0][0].equals("X") || board[1][1].equals("X") || board[2][2].equals("X");
        hasO = board[0][0].equals("O") || board[1][1].equals("O") || board[2][2].equals("O");
        if (!hasX || !hasO) {
            return false;
        }

        //check the diagonal from top right to bottom left
        hasX = board[0][2].equals("X") || board[1][1].equals("X") || board[2][0].equals("X");
        hasO = board[0][2].equals("O") || board[1][1].equals("O") || board[2][0].equals("O");
        if (!hasX || !hasO) {

            return false;
        }

        //evry line has both an x and an o, so nobody can win
        return true;


    }
}