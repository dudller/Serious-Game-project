package game;
import javax.swing.*;
import java.awt.*;
import  java.awt.event.*;
import java.io.Console;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Game extends JPanel implements KeyListener, MouseListener, Runnable {
    private boolean running=false,produckPicked=false;
    private Product productInHand;
    private Thread thread;
    private int x,y,toX,toY;
    public ArrayList<Product> products;
    private int width,height;
    public Game(int width,int height){
        this.width=width;
        this.height=height;
        setSize(width,height);
        setVisible(true);
        toY=0;
        toX=0;
        products=new ArrayList<Product>();
        initComponents();
        addMouseListener(this);
        addKeyListener(this);
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {


    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getX()>1200){
            toX=-300;
            System.out.println("click1" + toX +"   "+ x) ;
            //ustawienie produktow
            Collections.shuffle(products);
            for (Product p :products){
                p.setposition();
            }

        }
        if (e.getX()<400){
            toX=0;
            System.out.println("click2");
        }
        if (e.getX()>1280&&e.getX()<1430){
            for (int i=1;i<6;i++)
            {
                if (e.getY()<i*133){
                    pickProduct(i);
                }
            }
        }
        if (produckPicked&&e.getX()>400&&e.getX()<1000&&e.getY()>400&&e.getX()<600){
            placeProduct();

        }


    }



    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(launcher.background1,0,0,null);
        for (int i=0; i<10;i++){

                g.drawImage(products.get(i).image.getImage(),products.get(i).x,products.get(i).y,null);

        }

    }

    @Override
    public void run() {
        while (running){
            long time = System.currentTimeMillis();
            //render();
            update();
            time=System.currentTimeMillis()-time;
            try{
                if (time<launcher.DELAY){
                    Thread.sleep(launcher.DELAY-(int)time);
                }

            }catch (Exception ex){
                System.out.println(ex.getMessage());

            }
        }


    }
    public void start(){
        running=true;
        thread=new Thread(this);
        thread.start();
    }
    public void stop(){
        running=false;
    }

    private void render() {
        repaint();

    }
    private void update(){
        x=(int)this.getLocation().getX();
        y=(int)this.getLocation().getY();
        movePanel();

    }
    private void movePanel(){

            if (x==toX){
                //System.out.println("no move in x");

            }
            else if (x>toX){
                x-=30;

            }
            else{
                x+=30;

            }

            if (y==toY){
               //System.out.println("no move in y");

            }
            else if (y>toY){
                y-=30;

            }
            else {
                y+=30;

            }
        setLocation(x,y);
    }
    private void pickProduct(int i){
        productInHand=products.get(i-1);
        setCursor(productInHand.c);
        produckPicked=true;
        toX=0;
    }
    private void placeProduct() {
        if (produckPicked){
            //////////////////////////////////////////////////////////////empty!!!!!!!!!!!!!
        }
    }
    private void initComponents(){
        Product p1=new Product("",launcher.products[0],0,0,this);
        Product p2=new Product("",launcher.products[1],0,0,this);
        Product p3=new Product("",launcher.products[2],0,0,this);
        Product p4=new Product("",launcher.products[3],0,0,this);
        Product p5=new Product("",launcher.products[4],0,0,this);
        Product p6=new Product("",launcher.products[5],0,0,this);
        Product p7=new Product("",launcher.products[6],0,0,this);
        Product p8=new Product("",launcher.products[7],0,0,this);
        Product p9=new Product("",launcher.products[8],0,0,this);
        Product p10=new Product("",launcher.products[9],0,0,this);

    }
}
