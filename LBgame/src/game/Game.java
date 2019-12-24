package game;
import javax.swing.*;
import java.awt.*;
import  java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import static java.lang.Double.parseDouble;


public class Game extends JPanel implements KeyListener, MouseListener, Runnable {
    private boolean running=false,produckPicked=false;//zmienne wskazujące na to czy gra trwa i czy wybrano produkt z półki
    private Product productInHand;//aktualnie "trzymany" produkt
    private Thread thread;
    private int x,y,toX,toY,targetKey,termoX,termoEND; //zmienne parametryczne określające położenia docelowe i aktualne różnych elementów
    private Recipe recipe;
    public ArrayList<Product> products;
    public GameState GS;
    public Game(int width,int height, Recipe recipe){
        this.recipe=recipe;
        setSize(width,height);
        setVisible(true);
        //ustawienie domyślnych wartości ważnych parametrów
        toY=0;
        toX=0;
        targetKey=65;
        termoX=100;
        termoEND=300;
        products=new ArrayList();
        initComponents();
        addMouseListener(this);
        GS=new GameState();

    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (GS.task){
            case 3:
                KeyEvent2(e,targetKey);
                break;
            case 5:
                KeyEvent3(e);
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (GS.task){
            case 1:
                MouseEvents1(e);
            break;
            case 0:
            case 2:
            case 4:
            case 6:
                MouseEventsNextTask(e);
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
        switch (GS.task){
            case 1:
                g.drawImage(launcher.background1,0,0,null);
                for (int i=0; i<10;i++){//rysowanie produktow

                    g.drawImage(products.get(i).image.getImage(),products.get(i).x,products.get(i).y,null);

                }
                break;
            case 0:
            case 2:
            case 4:
                g.drawImage(launcher.background1,0,0,null);
                g.drawRect(300,100,200,200);
                break;
            case 3:
                g.drawImage(launcher.background1,0,0,null);
                g.drawImage(launcher.mikser,325,50,null);
                g.setFont(new Font("Helvetica", Font.BOLD, 40));
                g.drawString(Character.toString((char)targetKey),400,610);
                g.drawString(Character.toString((char)targetKey+3),645,610);

                break;
            case 5:
                g.drawImage(launcher.background1,0,0,null);
                g.fillOval(termoX,500,50,50);
                break;


        }

        recipe.display(g,27,5,200);


    }

    @Override
    public void run() {
        while (running){
            long time = System.currentTimeMillis();
            render();
            update();
            //czynności mające na celu zapewnienie płynności gry przy różnych warunkach sprzętowych
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
    //funkcje stanu gry
    private void render() {
        repaint();

    }
    private void update()//aktualizuje na bierząco wszystkie ważne zmienne, wpływa na "ruch" elementów w grze
    {
        x=(int)this.getLocation().getX();
        y=(int)this.getLocation().getY();
        if (checkTaskComplete()) GS.setTask();
        switch (GS.task){
            case 1:
                for (Product p :products){
                    p.setposition();
                }

                break;
            case 2:
            case 3:
            case 4:
            case 6:
                break;
            case 5:

                termoX+=GS.termospeed;
                if (termoX==termoEND) {
                    GS.termospeed = -GS.termospeed;
                    if (GS.termospeed>0)
                        termoEND=300; //docelowy punkt x przy przemieszczeniu w prawo
                    else termoEND=0; //w lewo
                }


        }

        movePanel();

    }

    private boolean checkTaskComplete()//sprawdzenie stanu wykonania zadań oraz obsługa zapisu wyników czasowych
    {
        switch (GS.task){
            case 1:
                for (boolean b : recipe.products.values())
                {
                    if (!b) return false;
                }
                choseKeys();
                break;
            case 3:
                for (boolean b : recipe.mixerSpeed.values())
                {
                    if (!b) return false;
                }
                break;
            case 0:
            case 2:
            case 4:
            case 5:
            case 6:
                return false;
        }
        GS.endtime=System.currentTimeMillis();
        GS.updateScores();
        return true;
    }
    private void MouseEventsNextTask(MouseEvent e)//obsługa myszy dla "zadań" parzystych czyli powiadomień o ukończeniu etapu
    {
        if (e.getX()>300&& e.getX()<500&& e.getY()>100&& e.getY()<300) GS.setTask();
        GS.startTime=System.currentTimeMillis();
        System.out.println(GS.pintscores());
    }
    //funkcje zadania 1 (1) w nawiasie wartość parametru task w GameState
    private void movePanel()//wykonuje przemieszczenie obiektu Canvas do określonych współrzędnych
    {

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
    private void pickProduct(int i)//przypisanie klikniętego produktu do zmiennej i ustawienie kursora
    {
        productInHand=products.get(i);
        produckPicked=true;
        try{
            setCursor(productInHand.c);
        }catch (Exception ex){
            System.out.println("cursor change error");
        }
        toX=0;
    }
    private void placeProduct() //operacja odłożenia produktu do miski
    {
        if (produckPicked){
            setCursor(Cursor.getDefaultCursor());
            if(recipe.products.containsKey(productInHand.name)){
                recipe.products.put(productInHand.name,true);
            }
            else {

            }
            produckPicked=false;
            productInHand=null;
        }
    }
    private void initComponents()//przyporządkowanie produktów do tablicy
    {
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
    private void MouseEvents1(MouseEvent e)//obsługa myszy dla zadania 1
    {
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
        if (e.getX()>1280&&e.getX()<1430){//sprawdza który produkt w rzędzie 1 został kiknięty
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
    //funkcje zadania 2 (3)
    private void choseKeys()//losowanie przycisków obsługi miksera
    {
        Random rand = new Random();
        targetKey=65+rand.nextInt(22);//65 litera a + losowo od 0 do 22 bo losujemy pierwszą literę a za nia muszą być jeszcze 3
    }
    private void KeyEvent2(KeyEvent k, int target)//obsługa klawiatury dla zadania 2
    {
        if (k.getKeyCode()==target+1){
            if (recipe.mixerSpeed.containsKey("1")) recipe.mixerSpeed.put("1",true);
        }
        if (k.getKeyCode()==target+2){
            if (recipe.mixerSpeed.containsKey("2")) recipe.mixerSpeed.put("2",true);
        }
        if (k.getKeyCode()==target+3){
            if (recipe.mixerSpeed.containsKey("3")) recipe.mixerSpeed.put("3",true);
        }

    }
    //funkcje zadania 3 (5)
    private void KeyEvent3(KeyEvent k)//obsługa zatrzymania wskaźnika temperatury
    {
        if (k.getKeyCode()==32){
            GS.updatePrecision(parseDouble(recipe.temperature),termoX);
            GS.setTask();

        }
    }
}
