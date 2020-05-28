package chessknights.state;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

/**
 * Class representing the state of the game.
 */

@Data
@Slf4j
public class ChessKnightsState implements Cloneable{
    @Setter(AccessLevel.NONE)
    private int X[] = { 2, 1, -1, -2, -2, -1, 1, 2};
    @Setter(AccessLevel.NONE)
    private int Y[] = { 1, 2, 2, 1, -1, -2, -2, -1};
    @Setter(AccessLevel.NONE)
    private int whiteRow;
    @Setter(AccessLevel.NONE)
    private int whiteCol;
    @Setter(AccessLevel.NONE)
    private int blackRow;
    @Setter(AccessLevel.NONE)
    private int blackCol;

    public static final int INITIAL[][]  = {
            {0,0,0,0,0,0,0,2},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {1,0,0,0,0,0,0,0}
    };

    /**
     * The array storing the current configuration of the tray.
     */
    @Setter(AccessLevel.NONE)
    private Knights [][] tray;

    /**
     * Creates a {@code ChessKnightState} object representing the (original)
     * initial state of the puzzle.
     */
    public ChessKnightsState() {
        this(INITIAL);
    }

    /**
     * Creates a {@code ChessKnightState} object that is initialized it with
     * the specified array.
     *
     * @param a an array of size 8&#xd7;8 representing the initial configuration
     *          of the tray
     * @throws IllegalArgumentException if the array does not represent a valid
     *                                  configuration of the tray
     */
    public ChessKnightsState(int[][] a) throws IllegalArgumentException {
        if (!isValidTray(a)) {
            throw new IllegalArgumentException();
        }
        initTray(a);
    }

    private boolean isValidTray(int[][] a) {
        if (a == null || a.length != 8) {
            return false;
        }
        boolean foundEmpty = false;
        for (int[] row : a) {
            if (row == null || row.length != 8) {
                return false;
            }
            for (int space : row) {
                if (space < 0 || space >= Knights.values().length) {
                    return false;
                }
                foundEmpty = true;
            }
        }
        return foundEmpty;
    }

    private void initTray(int[][] a) {
        this.tray = new Knights[8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if ((this.tray[i][j] = Knights.of(a[i][j])) == Knights.WHITE) {
                    whiteRow = i;
                    whiteCol = j;
                }else if ((this.tray[i][j] = Knights.of(a[i][j])) == Knights.BLACK) {
                    blackRow = i;
                    blackCol = j;
                }
            }
        }
    }

    /**
     * Checks whether the game is finished.
     *
     * @return {@code true} if player don't have any moves left, {@code false} otherwise
     */
    public boolean isFinished(int player){
        if (player==1)
            return movesLeft(whiteRow,whiteCol)==0;
        else{
            return movesLeft(blackRow,blackCol)==0;
        }
    }

    /**
     * Calculates possible moves from direction received.
     * Used to help to find endgame condition
     *
     * @return {@code 0} if no moves left, {@code 1-8} depends on possible moves left.
     */
    public int movesLeft(int row, int col)
    {
        int count = 0;
        for (int i = 0; i < 8; i++) {
            int x = row + X[i];
            int y = col + Y[i];
            if (x >= 0 && y >= 0 && x < 8 && y < 8 && this.tray[x][y] == Knights.EMPTY)
                count++;
        }
        return count;
    }

    /**
     Checks if player can move to coordinates provided by dx,dy.
     *
     * @param dx the row of destination point.
     * @param dy the column of destination point.
     * @return {@code true} if legal and coordinates are reachable,{code false} otherwise.
     */
    public boolean isValidMove(int dx,int dy,int player) {
        if (dx >= 0 && dx <= 8 && dy >= 0 && dy <= 8 && this.tray[dx][dy]== Knights.EMPTY || this.tray[dx][dy]== Knights.USED) {
            int diffX;
            int diffY;
            if (player==1){
                diffX = dx - whiteRow;
                diffY = dy - whiteCol;
            }else {
                diffX = dx - blackRow;
                diffY = dy - blackCol;
            }
            for (int i = 0; i < 8; i++) {
                if (diffX == X[i] && diffY == Y[i]){
                    if (this.tray[dx][dy]==Knights.USED){
                        log.warn("Cell at ({},{}) is already used!",dx,dy);
                        return false;
                    }
                    return true;
                }

            }
        }
        log.warn("Can't move to ({},{})!",dx,dy);
        return false;
    }

    /**
     * Moves Knight of a provide player to destination point.
     * @param dx the row of destination point.
     * @param dy the column of destination point.
     * @param player provides value depends on turn.
     */
    public void moveTo(int dx,int dy,int player){
        int row;
        int col;
        if (player == 1){
            row = whiteRow;
            col = whiteCol;
            whiteRow = dx;
            whiteCol = dy;
        }else{
            row = blackRow;
            col = blackCol;
            blackRow = dx;
            blackCol = dy;
        }
        this.tray[dx][dy]=Knights.of(player);
        this.tray[row][col]=Knights.USED;
        log.info("{} Knight at ({},{}) moved to ({},{})",Knights.of(player),row,col,dx,dy);
    }


    public ChessKnightsState clone() {
        ChessKnightsState copy = null;
        try {
            copy = (ChessKnightsState) super.clone();
        } catch (CloneNotSupportedException e) {
        }
        copy.tray = new Knights[tray.length][];
        for (int i = 0; i < tray.length; ++i) {
            copy.tray[i] = tray[i].clone();
        }
        return copy;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Knights[] row : tray) {
            for (Knights cell : row) {
                sb.append(cell).append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    /**
     * Created to test game logic.
     * @param state
     */
    private void chessKnightsGame(ChessKnightsState state){
        Scanner scanner = new Scanner(System.in);
        String player1 = scanner.next();
        String player2 = scanner.next();
        int player = 1;
        int dx;
        int dy;
        System.out.println(state);
        while (true){
            if (isFinished(player))
                break;
            String turn = (player == 1 ? player1 : player2);
            log.info("Your turn, {}", turn);
            dx = scanner.nextInt();
            dy = scanner.nextInt();
            while (!isValidMove(dx, dy, player)) {
                dx = scanner.nextInt();
                dy = scanner.nextInt();
            }
            moveTo(dx, dy, player);
            System.out.println(state);
            if (player == 1) {
                player = 2;
            } else {
                player = 1;
            }
        }
        if (player==1){
            log.info("{} won the match",player2);
        }else
            log.info("{} won the match",player1);
    }



    public static void main(String[] args) {
        ChessKnightsState state = new ChessKnightsState();
        state.chessKnightsGame(state);
    }
}