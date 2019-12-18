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
    private Recipe recipe;
    public ArrayList<Product> products;
    public int task;
    public int level;
    private int width,height;
    public Game(int width,int height, Recipe recipe){
        this.width=width;
        this.height=height;
        this.recipe=recipe;
        setSize(width,height);
        setVisible(true);
        toY=0;
        toX=0;
        products=new ArrayList<Product>();
        initComponents();
        addMouseListener(this);
        addKeyListener(this);
        task=1;
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
        switch (task){
            case 1:
                MouseEvents1(e);
            break;
            case 2:
                break;


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
        for (int i=0; i<10;i++){//rysowanie produktow

                g.drawImage(products.get(i).image.getImage(),products.get(i).x,products.get(i).y,null);

        }
        recipe.display(g,27,5,200);


    }

    @Override
    public void run() {
        while (running){
            long time = System.currentTimeMillis();
            render();
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
        for (Product p :products){
            p.setposition();
        }
        movePanel();

    }
    private void movePanel(){

            if (x==toX){


            }
            else if (x>toX){
                x-=30;

            }
            else{
                x+=30;

            }

            if (y==toY){


            }
            else if (y>toY){
                y-=30;

            }
            else {
                y+=30;

            }
        setLocation(x,y);
    }
    private void pickProduct(int i)  {
        productInHand=products.get(i);
        produckPicked=true;
        try{
            setCursor(productInHand.c);
        }catch (Exception ex){
            System.out.println("cursor change error");
        }
        toX=0;
    }
    private void placeProduct() {
        if (produckPicked){
            setCursor(Cursor.getDefaultCursor());
            if(recipe.products.containsKey(productInHand.name)){
                recipe.products.put(productInHand.name,true);
                System.out.println(recipe.products.size());
            }
            else {

            }
            produckPicked=false;
            productInHand=null;
        }
    }
    private void initComponents(){
        Product p1=new Product("butter",launcher.products[0],0,0,this);
        Product p2=new Product("cocoa",launcher.products[1],0,0,this);
        Product p3=new Product("eggs",launcher.products[2],0,0,this);
        Product p4=new Product("flour",launcher.products[3],0,0,this);
        Product p5=new Product("honey",launcher.products[4],0,0,this);
        Product p6=new Product("milk",launcher.products[5],0,0,this);
        Product p7=new Product("backing powder",launcher.products[6],0,0,this);
        Product p8=new Product("salt",launcher.products[7],0,0,this);
        Product p9=new Product("sugar",launcher.products[8],0,0,this);
        Product p10=new Product("water",launcher.products[9],0,0,this);

    }
    private void MouseEvents1(MouseEvent e){
        if (e.getX()>1200&&e.getX()<1280){//obsługa przycisku przewijania w lewo
            toX=-300;
            System.out.println("click1" + toX +"   "+ x) ;
            //losowanie ustawień produktow
            Collections.shuffle(products);



        }
        if (e.getX()<400){
            toX=0;
            System.out.println("click2");
        }
        if (e.getX()>1280&&e.getX()<1430){//sprawdza który produkt w rzędzie 1 został zkiknięty
            for (int i=0;i<5;i++)
            {
                if (e.getY()<20+(i+1)*133){
                    pickProduct(i);
                    break;
                }
            }
        }
        if (e.getX()>1430&&e.getX()<1580){//w rzędzie 2
            for (int i=5;i<10;i++)//od 5 żeby w pickproduct wybrać od produktu  o indeksie 5
            {
                if (e.getY()<20+(i-4)*133){
                    pickProduct(i);
                    break;
                }
            }
        }
        if (produckPicked&&e.getX()>400&&e.getX()<1000&&e.getY()>400&&e.getY()<600){
            placeProduct();
            System.out.println("place");

        }
    }
}
