package game;

import javax.swing.*;
import java.awt.*;

public class launcher {
    //images
    public static Image background1;
    public static Image background2;
    public static Image menuicon;
    public static Image mikser;
    public static Image cake;
    public static Image bowl;
    public static Image[] products;
    public static Image[] decorations;
    // ***
    //params
    static int windowHeight=900;
    static int windowWidth=1280;


    public static void main(String[] args) {
        loadImages();
        int x =(Toolkit.getDefaultToolkit().getScreenSize().width-windowWidth)/2;
        int y =(Toolkit.getDefaultToolkit().getScreenSize().height-windowHeight)/2;
        window gameWindow = new window(windowWidth,windowHeight,x,y);

    }
    public static void loadImages(){
        background1=new ImageIcon("images/bg1.jpeg").getImage();
        background2=new ImageIcon("file Name").getImage();
        menuicon=new ImageIcon("file Name").getImage();
        mikser=new ImageIcon("file Name").getImage();
        cake=new ImageIcon("file Name").getImage();
        bowl=new ImageIcon("file Name").getImage();
        products=new Image[10];
        products[0]=new ImageIcon("file Name").getImage();
        decorations=new Image[4];
        decorations[0]=new ImageIcon("file Name").getImage();

    }

}

