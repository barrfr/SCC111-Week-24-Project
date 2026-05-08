import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    private final int ROWS = 4;
    private final int COLS = 5;
    private final int TILE_SIZE = 100;

    private Model model;
    private Controller controller;

    private JPanel boardPanel;

    private ImageIcon emptyIcon;
    private ImageIcon treeIcon;
    private ImageIcon BheadIcon;
    private ImageIcon bodyIcon;
    private ImageIcon middleIcon;
    private ImageIcon BmanIcon;

    public GUI(Model model){
        this.model = model;
        
        setTitle("Snowman Puzzle Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        loadImages();

        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(ROWS, COLS));

        add(boardPanel);

        renderBoard(this.model.getBoard());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void setController(Controller controller){
        this.controller = controller;
    }

    private void loadImages(){
        emptyIcon = resizeIcon("hole.png");
        treeIcon = resizeIcon("tree.png");
        BheadIcon = resizeIcon("head_blue.png");
        bodyIcon = resizeIcon("snowball_large.png");
        middleIcon = resizeIcon("snowball_small.png");
        BmanIcon = resizeIcon("snowman_blue.png");


    }

    private ImageIcon resizeIcon(String filename){
        ImageIcon originalIcon = new ImageIcon(filename);

        Image scaledImage = originalIcon.getImage().getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_SMOOTH);

        return new ImageIcon(scaledImage);
    }

    public void renderGameFinish(boolean game_won){

    }

    public void renderBoard(String[][] board){
        boardPanel.removeAll();

        for (int row = 0; row < ROWS; row++){
            for (int col = 0; col < COLS; col++){

                String tile = board[row][col];
                if(tile.equals("b")){
                    JButton button = createTileButton(bodyIcon, row, col);
                    boardPanel.add(button);
                }

                else if(tile.equals("m")){
                    JButton button = createTileButton(middleIcon, row, col);
                    boardPanel.add(button);
                }

                else{
                    JButton label = createTileButton(getIconForTile(tile), row, col);
                    boardPanel.add(label);
                }
            }
        }

        boardPanel.revalidate();
        boardPanel.repaint();
        System.out.println("Board Rendered");
    }

    private JButton createTileButton(ImageIcon icon, int row, int col){
        JButton button = new JButton(icon);

        button.setPreferredSize(new Dimension(TILE_SIZE, TILE_SIZE));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);

        button.addActionListener(e -> {
            onTileClicked(row, col);
        });

        return button;
    }

    private ImageIcon getIconForTile(String tile){
        if(tile.equals("t")){
            return treeIcon;
        }

        if(tile.equals("HB")){
            return BheadIcon;
        }

        if(tile.equals("SB")){
            return BmanIcon;
        }

        return emptyIcon;
    }

    private void onTileClicked(int row, int col){
        System.out.println("Click");
        if (controller != null){
            controller.handleTileClicked(row, col);
        }
    }
}
