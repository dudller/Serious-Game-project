package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/** tworzy okno oraz gre, zawiera wszystkie parametry dotyczace ilustracji oraz stalych wartosci */
public class launcher {
    /**images */
    public static Image background1;
    public static Image menubackground;
    public static Image background2;
    public static Image mikser;
    public static Image cake;
    public static Image bowl;
    public static Image oven;
    public static ImageIcon[] products;
    public static ImageIcon[] decorations;

    // ***
    //params
    /**wysokosc okna */
    static int WINDOWHEIGHT=800;
    /**szerokosc okna */
    static int WINDOWWIDTH=1280;
    /** o ile pikseli jest wiekszy panel od okna */
    static int PANELSHIFT=300;
    /** opoznienie renderowania */
    static int DELAY=50;
    //***
    /**components */
    public Toolkit tool;
    public Recipe recipe;
    public Game game;



    /** funkcja glowna odpowiada za rozruch programu tworzy wszystkie niezbedne elementy */
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
    /** wczytuje obrazu z plikow */
    public void loadImages(){

        background1=new ImageIcon("src/images/bg1.png").getImage();
        menubackground=new ImageIcon("src/images/menu.jpg").getImage();
        background2=background1.getScaledInstance(2000, 1500,  java.awt.Image.SCALE_SMOOTH);
        mikser=new ImageIcon("src/images/mikser.png").getImage();
        cake=new ImageIcon("").getImage();
        bowl=new ImageIcon("").getImage();
        oven=new ImageIcon("").getImage();
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

