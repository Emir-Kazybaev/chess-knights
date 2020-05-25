package chessknights.state;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.Scanner;

@Data
public class ChessKnightsState {
    @Setter(AccessLevel.NONE)
    private int X[] = { 2, 1, -1, -2, -2, -1, 1, 2};
    @Setter(AccessLevel.NONE)
    public int Y[] = { 1, 2, 2, 1, -1, -2, -2, -1};
    @Setter(AccessLevel.NONE)
    public int whiteRow;
    @Setter(AccessLevel.NONE)
    public int whiteCol;
    @Setter(AccessLevel.NONE)
    public int blackRow;
    @Setter(AccessLevel.NONE)
    public int blackCol;

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


    public int[][] ChessKnightsState() {
        return INITIAL;
    }

    public void updatePosition(int board[][],int player){
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (player==1) {
                    if (board[i][j] == 1) {
                        whiteRow = i;
                        whiteCol = j;
                    }
                }else{
                    if (board[i][j] == 2) {
                        blackRow = i;
                        blackCol = j;
                    }
                }
            }
        }
    }

    public int movesLeft(int board[][], int row, int col)
    {
        int count = 0;
        for (int i = 0; i < 8; i++) {
            int x = row + X[i];
            int y = col + Y[i];
            if (x >= 0 && y >= 0 && x < 8 && y < 8 && board[x][y] == 0)
                count++;
        }
        return count;
    }

    public boolean isValidMove(int board[][],int row,int col,int dx,int dy) {
        if (dx >= 0 && dx <= 8 && dy >= 0 && dy <= 8 && board[dx][dy]==0) {
            int diffX = dx - row;
            int diffY = dy - col;
            for (int i = 0; i < 8; i++) {
                if (diffX == X[i] && diffY == Y[i])
                    return true;
            }
        }
        return false;
    }

    public void moveTo(int board[][],int row,int col,int dx,int dy,int player){
        board[dx][dy]=player;
        board[row][col]=3;
    }

    public void display(int [][]a){
        for (int i = 0;i<8;i++){
            for (int j = 0; j < 8; ++j) {
                System.out.print(a[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    private void chessKnightsGame(){
        int player = 1;
        int dx;
        int dy;
        int board[][] = INITIAL;
        display(board);
        updatePosition(board,1);
        updatePosition(board,2);
        int row = whiteRow;
        int col = whiteCol;
        while (true){
            String turn = (player == 1? "White": "Black");
            System.out.println(turn + " Knight Turn");
            Scanner scanner = new Scanner(System.in);
            dx = scanner.nextInt();
            dy = scanner.nextInt();
            if (movesLeft(board,row,col)==0)
                break;
            while(!isValidMove(board,row,col,dx,dy)) {
                System.out.println("Wrong move, try again!");
                dx = scanner.nextInt();
                dy = scanner.nextInt();
            }
            moveTo(board,row,col, dx, dy,player);
            updatePosition(board,player);
            if (player == 1){
                row = blackRow;
                col = blackCol;
                player = 2;
            }
            else {
                row = whiteRow;
                col = whiteCol;
                player = 1;
            }
            display(board);
        }
        if (player==1){
            System.out.println("Player 2 won");
        }else
            System.out.println("Player 1 won");
    }

    public static void main(String[] args) {
        ChessKnightsState state = new ChessKnightsState();
        state.chessKnightsGame();
    }
}
