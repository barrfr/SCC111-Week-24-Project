public class Controller{
    private Model model;
    private GUI gui;

    //click handler variables
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


    public void handleTileClicked(int row, int col){
        String[][] board = model.getBoard();
        String tile = board[row][col];
        clicked_row = row;
        clicked_col = col;
        if(selected_piece == -1 && (tile.equals("b") || tile.equals("m") || tile.equals("s"))){
            selected_row = row;
            selected_col = col;
            selected_piece = 1;
            System.out.println("selected piece = 1");
        }
        else if(selected_piece == 1 && !(clicked_row == selected_row && clicked_col == selected_col)){
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
            else{
                System.out.println("invalid destination selected");
            }
            System.out.println("Destination selected");
            String [][] bb;
            if (model.game_over == false){
                bb = model.MovePiece(board, selected_row, selected_col, dir);

                for (int i = 0; i<4; i++) {
                    System.out.println("");
                    for (int j = 0; j<5; j++) {
                        if(bb[i][j] == ""){
                            System.out.print(".");
                        }
                        System.out.print(model.getBoard()[i][j]);
                    }
                }        
                
                selected_piece = -1;
                System.out.println("selected piece = -1");                
                
                gui.renderBoard(model.getBoard());
                if(model.GameWin()){
                    model.GameOver();
                }

            }

        }

        else if(clicked_row == selected_row && clicked_col == selected_col){
            selected_piece = -1;
            System.out.println("selected piece = -1");    
        }

    }
}