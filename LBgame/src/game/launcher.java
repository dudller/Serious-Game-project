package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class launcher {
    //images
    public static Image background1;
    public static Image menubackground;
    public static Image background2;
    public static Image menuicon;
    public static Image mikser;
    public static Image cake;
    public static Image bowl;
    public static ImageIcon[] products;
    public static ImageIcon[] decorations;

    // ***
    //params
    static int WINDOWHEIGHT=800;
    static int WINDOWWIDTH=1280;
    static int PANELSHIFT=300;//jak dużo szerszy jest panel względem okna
    static int DELAY=50;//opóźnienie renderowania
    //***
    //components
    public Toolkit tool;
    public Recipe recipe;
    public Game game;




    public static void main(String[] args) {
        launcher l=new launcher();
        l.recipe=new Recipe();
        l.loadImages();
        l.tool=Toolkit.getDefaultToolkit();
        int x =(l.tool.getScreenSize().width-WINDOWWIDTH)/2;
        int y =(l.tool.getScreenSize().height-WINDOWHEIGHT)/2;
        window gameWindow = new window(WINDOWWIDTH,WINDOWHEIGHT,x,y);
        l.game=new Game(WINDOWWIDTH+PANELSHIFT,WINDOWHEIGHT,l.recipe);
        gameWindow.add(l.game);
        gameWindow.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        gameWindow.addKeyListener(l.game);
        l.game.start();







    }
    //wczytanie obrazów
    public void loadImages(){

        background1=new ImageIcon("src/images/bg1.png").getImage();
        menubackground=new ImageIcon("src/images/menu.jpg").getImage();
        background2=new ImageIcon("").getImage();
        mikser=new ImageIcon("src/images/mikser.png").getImage();
        cake=new ImageIcon("").getImage();
        bowl=new ImageIcon("").getImage();
        products=new ImageIcon[10];
        products[0]=new ImageIcon("src/images/butter.png");
        products[1]=new ImageIcon("src/images/coca.png");
        products[2]=new ImageIcon("src/images/eggs.png");
        products[3]=new ImageIcon("src/images/flour.png");
        products[4]=new ImageIcon("src/images/honey.png");
        products[5]=new ImageIcon("src/images/milk.png");
        products[6]=new ImageIcon("src/images/powder.png");
        products[7]=new ImageIcon("src/images/salt.png");
        products[8]=new ImageIcon("src/images/sugar.png");
        products[9]=new ImageIcon("src/images/water.png");
        decorations=new ImageIcon[4];
        decorations[0]=new ImageIcon("");


    }

}

