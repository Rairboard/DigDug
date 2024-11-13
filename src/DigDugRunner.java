import javax.swing.*;
import java.awt.*;
import java.util.*;
public class DigDugRunner extends JFrame {

    private static final int WIDTH = 408;
    private static final int HEIGHT = 408;

    public DigDugRunner(){
        super("Dig Dug");
        setSize(WIDTH,HEIGHT);
        getContentPane().add(new DigDug(25));
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        DigDugRunner run = new DigDugRunner();
    }


}
