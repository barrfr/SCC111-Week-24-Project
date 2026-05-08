import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    //declaring constants
    private final int ROWS = 4;
    private final int COLS = 5;
    private final int TILE_SIZE = 100;

    private Model model;
    private Controller controller;

    //setting up images and game panel
    private JPanel boardPanel;
    private ImageIcon emptyIcon;
    private ImageIcon treeIcon;
    private ImageIcon BheadIcon;
    private ImageIcon RheadIcon;
    private ImageIcon YheadIcon;
    private ImageIcon bodyIcon;
    private ImageIcon middleIcon;
    private ImageIcon stackIcon;
    private ImageIcon RmanIcon;
    private ImageIcon BmanIcon;
    private ImageIcon YmanIcon;

    public GUI(Model model){
        this.model = model;
        
        //instantiate panel and set up game space
        setTitle("Snowman Puzzle Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        loadImages();
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(ROWS, COLS));

        add(boardPanel);

        renderBoard(this.model.getBoard());

        //auto adjusts size and location of panel to fit board & be centre screen
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    //kickstart user input
    public void setController(Controller controller){
        this.controller = controller;
    }

    //made a function to reduce clutter in constructor
    private void loadImages(){
        emptyIcon = resizeIcon("hole.png");
        treeIcon = resizeIcon("tree.png");
        BheadIcon = resizeIcon("head_blue.png");
        RheadIcon = resizeIcon("head_red.png");
        YheadIcon = resizeIcon("head_yellow.png");
        bodyIcon = resizeIcon("snowball_large.png");
        middleIcon = resizeIcon("snowball_small.png");
        stackIcon = resizeIcon("snowman_stack.png");
        RmanIcon = resizeIcon("snowman_red.png");
        BmanIcon = resizeIcon("snowman_blue.png");
        YmanIcon = resizeIcon("snowman_yellow.png");

    }

    //make icon fit the game board
    private ImageIcon resizeIcon(String filename){
        ImageIcon originalIcon = new ImageIcon(filename);

        Image scaledImage = originalIcon.getImage().getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_SMOOTH);

        return new ImageIcon(scaledImage);
    }

    public void renderBoard(String[][] board){
        //reset board render
        boardPanel.removeAll();

        //iterate through the model board, & if detecting a movable piece, create an interactable button
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

                else if(tile.equals("s")){
                    JButton button = createTileButton(stackIcon, row, col);
                    boardPanel.add(button);
                }

                //if no movable piece, create a label
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

        //adjusts button parameters
        button.setPreferredSize(new Dimension(TILE_SIZE, TILE_SIZE));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);

        //detects when the button is clicked and calls onTileClicked()
        button.addActionListener(e -> {
            onTileClicked(row, col);
        });

        return button;
    }

    //used to detect what non-interactable object is currently trying to be rendered
    private ImageIcon getIconForTile(String tile){
        if(tile.equals("t")){
            return treeIcon;
        }

        if(tile.equals("HB")){
            return BheadIcon;
        }

        if(tile.equals("HR")){
            return RheadIcon;
        }

        if(tile.equals("HY")){
            return YheadIcon;
        }

        if(tile.equals("SR")){
            return RmanIcon;
        }

        if(tile.equals("SB")){
            return BmanIcon;
        }

        if(tile.equals("SY")){
            return YmanIcon;
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
