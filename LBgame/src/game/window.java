package game;

import javax.swing.*;
import java.awt.*;

public class window extends JFrame {
    public GridBagConstraints cont;
    public window(int width, int height, int x, int y){
        setResizable(false);

        setSize(width,height);
        setLocation(x,y);
        setVisible(true);
        setResizable(false);

    }



}
