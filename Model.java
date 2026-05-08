public class Model{
    
    //2d array board representation
    private String[][] board = {
        {"", "", "", "", ""}, 
        {"", "", "", "", ""}, 
        {"", "", "", "", ""}, 
        {"", "", "", "", ""}, 
    };

    public int board_state = 0;

    //declaring constants
    private final int ROWS = 4;
    private final int COLS = 5;

    boolean game_over = false;

    //class used to store pieces & make setting up board states more efficient
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
    /* m = middle piece
     * b = base piece
     * t = tree
     * s = stacked piece
     * HX = head piece of X colour
     * SX = snowman of X colour
     */
    private Piece[][] boardStates = {
    { //example state #1
        new Piece("m", 0, 1), 
        new Piece("HB", 3, 0), 
        new Piece("b", 3, 4)
    },{ //example state #2
        new Piece("HR", 0, 0), 
        new Piece("HY", 3, 0), 
        new Piece("t", 3, 2), 
        new Piece("t", 0, 2),
        new Piece("b", 3, 1),
        new Piece("b", 1, 0),
        new Piece("m", 2, 3),
        new Piece("m", 0, 4)
    },{ //example state #3
        new Piece("HR", 2, 0),
        new Piece("HY", 2, 2),  
        new Piece("HB", 2, 4), 
        new Piece("t", 1, 2), 
        new Piece("b", 3, 2), 
        new Piece("b", 3, 4),
        new Piece("b", 0, 2),   
        new Piece("m", 0, 4), 
        new Piece("m", 1, 4),
        new Piece("m", 3, 0)
    }
    };



    public Model(){
        FillBoard(board_state);
        
    }

    void FillBoard(int state) {
        //clear the board & copy boardStates onto the board using a for loop
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
    }

    //direction: north=1 - east=2 - south=3 - west=4
    int IsMoveLegal(String[][] board, int row, int col, int direction){

        /* LOOP STRUCTURE:
         * - check if dir is north/east/south/west
         * - iterate through relevant direction
         * - check if any pieces in relevant direction
         * - if the piece is one tile away from a base/middle piece, then stay still.
         * - return the location next to the piece thats there
         * 
         * - return -1 if nothing is there
         */

        if(direction == 1 && row != 0){
            for(int i=row-1; i>=0; i--){
                if(!board[i][col].equals("")){
                    if(row - i == 1 && ((board[i][col].equals("b") || board[i][col].equals("m")))){
                        return i;
                    }
                    return i+1;
                }
            }
        }
        else if(direction == 2 && col !=4){
            for(int i=col+1; i<=4; i++){
                if(!board[row][i].equals("")){
                    if(i - col == 1 && (board[row][i].equals("b") || board[row][i].equals("m"))){
                        return i;
                    }
                    return i-1;
                }
            }
        }
        
        else if(direction == 3 && row != 3){
            for(int i=row+1; i<=3; i++){
                if(!board[i][col].equals("")){
                    if(i - row == 1 && (board[i][col].equals("b") || board[i][col].equals("m"))){
                        return i;
                    }
                    return i-1;
                }
            }
        }
        else if(direction == 4 && col !=0){
            for(int i=col-1; i >= 0; i--){
                if(!board[row][i].equals("")){
                    if(col - i == 1 && (board[row][i].equals("b") || board[row][i].equals("m"))){
                        return i;
                    }
                    return i+1;
                }
            }
        }
        return -1;
    }


    String[][] MovePiece(String[][] board, int row, int col, int direction){
        //find destination coordinate:
        int destination = IsMoveLegal(board, row, col, direction);
        if(destination != -1){
            
            //if moving north/south
            if (direction == 1 || direction == 3){

                //checks if player is stacking pieces or not
                if(board[row][col].equals("m") && board[destination][col].equals("b")){
                    board[destination][col] = "s";
                    board[row][col] = "";
                }

                //if player attempting to stack inversely (base into middle instead of middle into base)
                else if (board[row][col].equals("b") && board[destination][col].equals("m")){
                    return board;
                }

                //allows piece to move into an empty tile
                else if (board[destination][col].equals("")){
                    board[destination][col] = board[row][col];
                    board[row][col] = "";
                }
            }

            //below acts the same but for east & west
            else if (direction == 2 || direction == 4){
                if(board[row][col].equals("m") && board[row][destination].equals("b")){
                    board[row][destination] = "s";
                    board[row][col] = "";
                }

                else if (board[row][col].equals("b") && board[row][destination].equals("m")){
                    return board;
                }

                else{
                    board[row][destination] = board[row][col];
                    board[row][col] = "";
                }
            }
            
            return board;
        
        }

        //if piece slides off then end game
        GameOver();
        board[row][col] = "";
        return board;
    }
    
    //allows player to put a head onto a stack
    String[][] StackCheckHead(String[][] board, int c_row, int s_row, int c_col, int s_col){

        if(board[c_row][c_col].equals("HR") && (board[s_row][s_col].equals("s"))){
            board[s_row][s_col] = "SR";
            board[c_row][c_col] = "";
        }
        else if(board[c_row][c_col].equals("HY") && (board[s_row][s_col].equals("s"))){
            board[s_row][s_col] = "SY";
            board[c_row][c_col] = "";
        }
        else if(board[c_row][c_col].equals("HB") && (board[s_row][s_col].equals("s"))){
            board[s_row][s_col] = "SB";
            board[c_row][c_col] = "";
        }

        return board;
    }

    boolean GameWin(){
        //to check if game has been won, iterate through board and if any tile has a piece, then game hasnt been won
        for (int i = 0; i<4; i++) {
                for (int j = 0; j<5; j++) {
                    if(board[i][j].equals("b") || board[i][j].equals("m") || board[i][j].equals("s") || board[i][j].equals("HR") || board[i][j].equals("HY") || board[i][j].equals("HB")){
                        return false;
                    }
                }
            }
        //game is won hence move onto next level
        if(board_state <= 2){
            board_state += 1;
        }
        //if on final level, stop game
        else{
            GameOver();
        }
        return true;
    }

    void GameOver(){
        game_over = true;
    }

}
