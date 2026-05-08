public class Model{
    
    //2d array board representation
    private String[][] board = {
        {"", "", "", "", ""}, 
        {"", "", "", "", ""}, 
        {"", "", "", "", ""}, 
        {"", "", "", "", ""}, 
    };

    public int board_state = 0;

    private final int ROWS = 4;
    private final int COLS = 5;

    boolean game_over = false;

    class Piece {
        String type;
        int row;
        int col;

        Piece(String type, int row, int col){
            this.type = type;
            this.row = row;
            this.col = col;
        }

        String getType(){
            return type;
        }

        int getCol(){
            return col;
        }

        int getRow(){
            return row;
        }
    }

    //2d array that stores all board states for scalable board state aquisition
    private Piece[][] boardStates = {
    { //example state
        new Piece("m", 0, 1), 
        new Piece("HB", 3, 0), 
        new Piece("b", 3, 4)
    }
    };



    public Model(){
        FillBoard(board_state);
        
    }

    void FillBoard(int state) {
        clearBoard();
        for(int i=0; i < boardStates[state].length; i++){
            board[boardStates[state][i].getRow()][boardStates[state][i].getCol()] = boardStates[state][i].getType();

        }
    }

    void clearBoard(){
        for(int row = 0; row < ROWS; row++){
            for(int col = 0; col < COLS; col++){
                board[row][col] = "";
            }
        }
    }

    //getter and setter methods
    public String[][] getBoard(){
        return board;
    }

    public void setBoard(String[][] new_board){
        board = new_board;
        System.out.println("Board set");
    }

    //direction: north=1 - east=2 - south=3 - west=4
    int IsMoveLegal(String[][] board, int row, int col, int direction){

        /* LOOP STRUCTURE:
         * - check if dir is north/east/south/west
         * - iterate through relevant direction
         * - check if any pieces in relevant direction
         * - return the location next to the piece thats there
         * 
         * - return -1 if nothing is there
         */

        if(direction == 1 && row != 0){
            System.out.println("1");
            for(int i=row-1; i>=0; i--){
                if(!board[i][col].equals("")){
                    System.out.println("Move Made 1 " + i);
                    return i+1;
                }
            }
        }
        else if(direction == 2 && col !=4){
            System.out.println("2");
            for(int i=col+1; i<=4; i++){
                if(!board[row][i].equals("")){
                    System.out.println("Move Made 2 " + i);
                    return i-1;
                }
            }
        }
        
        else if(direction == 3 && row != 3){
            System.out.println("3");
            for(int i=row+1; i<=3; i++){
                if(!board[i][col].equals("")){
                    System.out.println("Move Made 3 " + i);
                    return i-1;
                }
            }
        }
        else if(direction == 4 && col !=0){
            System.out.println("4");
            for(int i=col-1; i >= 0; i--){
                System.out.println("5");
                if(!board[row][i].equals("")){
                    System.out.println("Move Made 4 " + i);
                    return i+1;
                }
            }
        }
        System.out.println("returning -1");
        return -1;
    }


    String[][] MovePiece(String[][] board, int row, int col, int direction){
        System.out.println("movin piece");
        //find destination coordinate:
        int destination = IsMoveLegal(board, row, col, direction);
        if(destination != -1){
            System.out.println(destination);
            for (int i = 0; i<4; i++) {
                for (int j = 0; j<5; j++) {
                    if(board[i][j] == ""){
                        System.out.print(".");
                    }
                    System.out.print(board[i][j]);
                }
            }
            System.out.println("col" + col);
            System.out.println(destination);
            System.out.println(row);            
            //simple move piece logic
            if (direction == 1 || direction == 3){

                System.out.println("Z1");
                System.out.println("dest - row - col " + destination + row + col);
                board[destination][col] = board[row][col];
                board[row][col] = "";
                }

        
            else if (direction == 2 || direction == 4){
                System.out.println("Z2");
                    board[row][destination] = board[row][col];
                    board[row][col] = "";
                }
            
            return board;}
        
        
        GameOver();
        board[row][col] = "";
        return board;
            }

    boolean isInBounds(int row, int col){
        return row >= 0 && row < 4 && col >= 0 && col < 5;
    }


    

    boolean GameWin(){
        for (int i = 0; i<4; i++) {
                for (int j = 0; j<5; j++) {
                    if(board[i][j].equals("b") || board[i][j].equals("m") || board[i][j].equals("s") || board[i][j].equals("HB")){
                        System.out.println("game not over");
                        return false;
                    }
                }
            }
        System.out.println("game won");
        GameOver();
        return true;
    }

    void GameOver(){
        System.out.println("GAME OVER");
        game_over = true;
    }

}
