import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {
    JPanel grid;

    //player-related variables
    private final Icon[] players = {new ImageIcon(getClass().getResource("X.png")),new ImageIcon(getClass().getResource("O.png"))};
    private int index = 0;
    //END

    //button-related variables
    private final int ROWS = 3, COLS = 3;
    private final JButton[][] buttonTable = new JButton[ROWS][COLS];
    private int buttonsFilled;
    //END

    public GameFrame(){
        super("Tic-Tac-Toe");
        grid = new JPanel(new GridLayout(ROWS, COLS));



        //adding buttons to buttonTable;
        buttonsFilled = 0;
        for(int i = 0; i < ROWS; i++)
            for(int j = 0; j < COLS; j++){
                buttonTable[i][j] = new JButton();
                buttonTable[i][j].putClientProperty("X",j);
                buttonTable[i][j].putClientProperty("Y",i);
                buttonTable[i][j].addActionListener(new ButtonHandler());
                grid.add(buttonTable[i][j]);
            }
        //END

        //frame properties
        add(grid);
        setSize(400,400);
        setLocationRelativeTo(null);
        setResizable(false);
        //END
    }

    private class ButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent event){
            JButton btn = (JButton) event.getSource();

            if(btn.getIcon() == null){
                btn.setIcon(players[index]);
                buttonsFilled++;
                //win-condition
                if(isHorizontalSet(btn) || isVerticalSet(btn) || isDiagonal() ) {
                    JOptionPane.showMessageDialog(grid,String.format("Player %d Won!!", index + 1),"WIN",JOptionPane.PLAIN_MESSAGE);
                    resetGame();

                }
                //END

                //draw-condition
                else if(buttonsFilled == ROWS * COLS){
                    JOptionPane.showMessageDialog(grid, "Draw!", "DRAW",JOptionPane.PLAIN_MESSAGE);
                    resetGame();
                }
                //END

                //continue game
                index = (index + 1) % players.length;
            }
        }
    }

    private boolean isVerticalSet(JButton btn){
        int col = Integer.parseInt(btn.getClientProperty("X").toString()); //Object -> String -> int
        for(int i = 0; i < ROWS - 1; i++)
            if(buttonTable[i][col].getIcon() != buttonTable [i+1][col].getIcon())
                return false;
        return true;
    }

    private boolean isHorizontalSet(JButton btn){
        int row = Integer.parseInt(btn.getClientProperty("Y").toString()); //Object -> String -> int
        for(int i = 0; i < ROWS - 1; i++)
            if(buttonTable[row][i].getIcon() != (buttonTable [row][i+1].getIcon()))
                return false;
        return true;
    }

    private boolean isDiagonal(){
        //left-diagonal condition
        if(buttonTable[0][0].getIcon() == players[index] && buttonTable[0][0].getIcon() == buttonTable[1][1].getIcon() && buttonTable[1][1].getIcon() == buttonTable[2][2].getIcon())
            return true;
        //END

        //right-diagonal condition
        else if(buttonTable[0][2].getIcon() == players[index] && buttonTable[0][2].getIcon() == buttonTable[1][1].getIcon() && buttonTable[1][1].getIcon() == buttonTable[2][0].getIcon())
            return true;
        //END

        return false;
    }

    public void resetGame(){
        buttonsFilled = 0;
        for(JButton[] row : buttonTable)
            for(JButton btn : row)
                btn.setIcon(null);
    }
}