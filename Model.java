public class Model{
    
    //2d array board representation
    private String[][] board = {
        {"", "", "", "", ""}, 
        {"", "", "", "", ""}, 
        {"", "", "", "", ""}, 
        {"", "", "", "", ""}, 
    };

    public int board_state = 0;

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
        for(int i=0; i < boardStates[state].length; i++){
            board[boardStates[state][i].getRow()][boardStates[state][i].getCol()] = boardStates[state][i].getType();

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


}