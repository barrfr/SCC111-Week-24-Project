public class Controller{
    private Model model;
    private GUI gui;

    //click handling variables
    private int selected_col = -1;
    private int selected_row = -1;
    private int selected_piece = -1;
    private int clicked_col = -1;
    private int clicked_row = -1;
    private int dir = -1;

    public Controller(Model model, GUI gui){
        this.model = model;
        this.gui = gui;

    }

    //click handler called by the GUI listener
    public void handleTileClicked(int row, int col){
        String[][] board = model.getBoard();
        String tile = board[row][col];
        clicked_row = row;
        clicked_col = col;

        //check if a click has already been conducted & if the click is made on a moveable tile
        if(selected_piece == -1 && (tile.equals("b") || tile.equals("m") || tile.equals("s"))){

            //adjust click handling variables
            selected_row = row;
            selected_col = col;
            selected_piece = 1;
        }

        //if a piece has already been selected & the player isnt attempting to deselect their current piece
        else if(selected_piece == 1 && !(clicked_row == selected_row && clicked_col == selected_col)){

            //calculate desired direction of piece
            if (clicked_row < selected_row && clicked_col == selected_col){
                dir = 1;
                }

            else if (clicked_row > selected_row && clicked_col == selected_col){
                dir = 3;
                }

            else if (clicked_row == selected_row && clicked_col > selected_col){
                dir = 2;
                }

            else if (clicked_row == selected_row && clicked_col < selected_col){
                dir = 4;
                }

            //create temporary board
            String [][] temp_board;
            if (model.game_over == false){
                
                //check if stack is being attempted before moving the piece
                if((board[clicked_row][clicked_col].equals("HR") || board[clicked_row][clicked_col].equals("HY") || board[clicked_row][clicked_col].equals("HB")) && (board[selected_row][selected_col].equals("s"))){
                    temp_board = model.StackCheckHead(board, clicked_row, selected_row, clicked_col, selected_col);}

                else{temp_board = model.MovePiece(board, selected_row, selected_col, dir);}
                
                //deselect piece
                selected_piece = -1;              
                
                gui.renderBoard(model.getBoard());

                if(model.GameWin()){
                    model.FillBoard(model.board_state);
                    gui.renderBoard(model.getBoard());
                }

            }

        }
        //allow player to deselect a piece
        else if(clicked_row == selected_row && clicked_col == selected_col){
            selected_piece = -1;
        }

    }
}
