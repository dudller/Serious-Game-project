package game;
import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.*;
import  java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import static java.lang.Double.parseDouble;


/** zarzadza gra oraz dba o obsluge zdarzen i inicjalizacje elementow gry */
public class Game extends JPanel implements KeyListener, MouseListener,MouseMotionListener, Runnable {
    /**wskazuje na to czy gra trwa */
    private boolean running;
    /** wskazuje czy wybrano produkt z szafki */
    private boolean productPicked = false;
    /** przechowuje aktualnie "trzymany" produkt */
    private Product productInHand;
    /** watek uzywany podczas gry */
    private Thread thread;
    /** wspolrzedna uzywana w grze */
    private int x, y, toX, toY;
    /** wskazuja zmienne dla zadania 2 oraz 3*/
    private  int targetKey, termoX, termoEND;
    /** czas spedzony w menu i wejscia do menu*/
    private double menuTime, menuEnter;
    /** obiekt klasy Recipe aktualnie uzywany w grze */
    public Recipe recipe;
    /** wskazuje czy wyswietlac dodatkowe szczegoly przy przejsciu do nastepnego zadania */
    public boolean showdetails = false;
    /**  lista produktow dla zadania 1 */
    public ArrayList<Product> products;
    /** lista punktow ciecia dla zadania 4 */
    public ArrayList<Point> cutPoints;
    /** obiekt klasy Gamestate nadzoruje przebieg gry i zapisuje wyniki */
    public GameState GS;
    /** informuje czy proces ciecia w zadaniu 4 zostal uczciwie rozpoczety */
    private boolean properCut=false;
    /** ustawienie domyslnych wlasciwosci parametrow, przypisanie przepisu oraz uimeszczenie panelu w oknie gry
     * @param height wysokosc panelu
     * @param width szerokosc panelu
     * @param recipe obiekt przepisu */
    public Game(int width, int height, Recipe recipe) {
        this.recipe = recipe;
        setSize(width, height);
        setVisible(true);
        //ustawienie domyślnych wartości ważnych parametrów
        running = false;
        menuTime = 0;
        menuEnter = 0;
        toY = 0;
        toX = 0;
        targetKey = 65;
        termoX = launcher.OVENSHIFT;
        termoEND = launcher.OVENSHIFT+525;
        products = new ArrayList();
        cutPoints= new ArrayList();
        initComponents();
        addMouseListener(this);//dodany dla obiektu Game poniewaz dziedziczy on z Jpanel a w zalozeniu mechaniki gry operujemy na wspolrzednych panelu a nie okna
        addMouseMotionListener(this);
        GS = new GameState();
        ChartPanel chartPanel = new ChartPanel(GS.chart1);
        add(chartPanel);

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    /** wywoluje rozne funkcje KeyEvent w zaleznosci od wartosci zmiennej task */
    public void keyPressed(KeyEvent e) {

        switch (GS.task) {
            case 3:
                KeyEvent2(e, targetKey);

                break;
            case 5:
                KeyEvent3(e);

                break;

        }
        if (e.getKeyCode() == 27) {
            menuEnter = System.currentTimeMillis();
            GS.setTask(0);
        }
        if (e.getKeyCode() == 8) {
            GS.goToPrewTask();
            menuTime += System.currentTimeMillis() - menuEnter;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    /** wtwoluje funkcje glownie nazwane MouseEvents w zaleznosi od wartosci zmiennej task*/
    public void mouseClicked(MouseEvent e) {
        switch (GS.task) {
            case 1:
                MouseEvents1(e);
                break;
            case 0:
                MenuEvents(e);
                break;
            case 2:
            case 4:
            case 6:
                MouseEventsNextTask(e);
                break;


        }
        if (GS.task!=0&&e.getX()<300 && e.getY()<130) GS.setTask(0);
    }


    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getX()<410) properCut=true;
        else properCut=false;

    }

    @Override
    /** w zadaniu 4 resetuje liste cutPoints */
    public void mouseReleased(MouseEvent e) {
        cutPoints.clear();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    /** tylko dla zadania 4 dodaje punkty pozycji myszy do listy cutPionts oraz wywoluje funcje aktualizacji wykikow i przejscia do nastepnego zadania */
    public void mouseDragged(MouseEvent e) {
        if (GS.task == 7) {
            if (properCut&&e.getX()>400&&e.getX()<1000&&e.getY()>500&&e.getY()<800){
                    cutPoints.add(e.getPoint());
            }

            if (e.getX()>999&&cutPoints.size()>10){
                double score=0;
                int counter=0;
                for (Point i :cutPoints)
                {
                    score+=i.y;
                    counter++;
                }
                GS.updatePrecision(650,score/counter);
                GS.setTask();
            }


        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    /** wyswietla obrazy w zaleznosci od wartosci zmiennej task*/
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        switch (GS.task) {
            case 0:
                g.drawImage(launcher.menubackground, 0, 0, null);
                break;
            case 1:
                g.drawImage(launcher.background1, 0, 0, null);
                g.drawImage(launcher.bowl, 325, 50, null);
                for (int i = 0; i < 10; i++) {//rysowanie produktow

                    g.drawImage(products.get(i).image.getImage(), products.get(i).x, products.get(i).y, null);

                }
                recipe.display(g, 27, 5, 200);
                break;

            case 2:
            case 4:
            case 6:
            case 8:
                g.drawImage(launcher.background1, 0, 0, null);
                g.drawImage(launcher.aftertask,300,150,null);
                recipe.display(g, 27, 5, 200);
                if(showdetails) {
                    g.setFont(new Font("Helvetica", Font.PLAIN, 36));
                    g.drawString("Average response time:" +GS.avgScore + "ms",500,410);
                    g.drawString("Averge precision: " + GS.avgPrecision,500,460);
                    //rysowanie wykresow
                    Graphics2D g2 = (Graphics2D) g;
                    GS.chart1.draw(g2, new Rectangle(310, 500, 400, 300));
                    GS.chart2.draw(g2, new Rectangle(710, 500, 400, 300));
                }


                break;
            case 3:
                g.drawImage(launcher.background1, 0, 0, null);
                g.drawImage(launcher.mikser, 325, 50, null);
                g.setFont(new Font("Helvetica", Font.BOLD, 40));
                g.drawString(Character.toString((char) targetKey), 400, 610);
                g.drawString(Character.toString((char) targetKey + 3), 645, 610);
                recipe.display(g, 27, 5, 200);

                break;
            case 5:
                g.setColor(Color.BLUE);
                g.drawImage(launcher.background1, 0, 0, null);
                g.drawImage(launcher.oven, 315, 0, null);
                g.fillOval(termoX, 250, 10, 50);
                g.setColor(Color.pink);
                g.fillOval(launcher.OVENSHIFT+(Integer.parseInt(recipe.temperature))*21/10, 250, 10, 50); //2.1 to skala  temperatury do pikselami
                //jesli mamy temperature 10 stopni to zeby dobrze ja wyswietlic mnozymy razy 2.1 px
                recipe.display(g, 27, 5, 200);
                break;
            case 7:
                g.drawImage(launcher.background1, 0, 0, null);
                g.drawImage(launcher.cake, 400, 500, null);

                Point i = new Point(400,650); //wcześniejszy punkt
                g.setColor(Color.WHITE);
                if (cutPoints!=null){
                    for (Point j : cutPoints){
                        g.drawLine(i.x,i.y,j.x,j.y);
                        i.setLocation(j.x,j.y);
                    }}
                g.setColor(Color.BLUE);
                g.drawLine(400,650,1000,650);
                recipe.display(g, 27, 5, 200);
                break;
            case 9:
                g.drawImage(launcher.background2, 0, 0, null);
                break;
        }
        g.setColor(Color.BLACK);
        g.setFont(new Font("Helvetica", Font.PLAIN, 48));
        g.drawString("Money: "+Integer.toString(GS.money),900,50);
    }

    @Override
    /** odpowiada za nieprzerwane dzialanie gry oraz dostosowuje optymalna stala liczbe odswierzen ekranu */
    public void run() {
        while (running) {
            long time = System.currentTimeMillis();
            repaint();
            update();
            //czynności mające na celu zapewnienie płynności gry przy różnych warunkach sprzętowych
            time = System.currentTimeMillis() - time;
            try {
                if (time < launcher.DELAY) {
                    Thread.sleep(launcher.DELAY - (int) time);
                }

            } catch (Exception ex) {
                System.out.println(ex.getMessage());

            }
        }


    }
    /** aktywuje funkcje run oraz tworzy watek gry */
    public void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    //funkcje stanu gry
    /** aktualizuje wszystkie wazne zmienne, wplywa na "ruch" elementow w grze*/
    private void update()
    {
        x = (int) this.getLocation().getX();
        y = (int) this.getLocation().getY();
        if (checkTaskComplete()) GS.setTask();
        switch (GS.task) {
            case 1:
                for (Product p : products) {
                    p.setposition();
                }

                break;
            case 2:
            case 3:
            case 4:
            case 6:
            case 7:
            case 8:
                break;
            case 5:

                termoX += GS.termospeed;
                if (termoX == termoEND) {
                    GS.termospeed = -GS.termospeed;
                    if (GS.termospeed > 0)
                        termoEND = 525+launcher.OVENSHIFT; //docelowy punkt x przy przemieszczeniu w prawo
                    else termoEND = launcher.OVENSHIFT; //w lewo
                }
                break;



        }

        movePanel();

    }
    /** sprawdza stanu wykonania zadan oraz obsluguje zapisu wynikow czasowych
     * @return  false jesli nie zostaly ukonczone, true gdy spelnione sa wymogi przepisu */
    private boolean checkTaskComplete(){
        switch (GS.task) {
            case 1:
                for (boolean b : recipe.products.values()) {
                    if (!b) return false;
                }
                choseKeys();
                break;
            case 3:
                for (boolean b : recipe.mixerSpeed.values()) {
                    if (!b) return false;
                }
                break;
            case 0:
            case 2:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                return false;
        }
        GS.endtime = System.currentTimeMillis() - menuTime;
        GS.updateScores();
        menuTime = 0;
        System.out.println(GS.endtime - GS.startTime);
        return true;
    }
    /**obsluga myszy dla "zadan" parzystych czyli powiadomien o ukonczeniu etapu*/
    private void MouseEventsNextTask(MouseEvent e)
    {
        if (e.getX() > 350 && e.getX() < 600 && e.getY() > 200 && e.getY() < 370) GS.setTask();
        GS.startTime = System.currentTimeMillis();
        if (e.getX() > 820 && e.getX() < 1070 && e.getY() > 200 && e.getY() < 370) showdetails=!showdetails;
    }


    //funkcje menu
    /**obsluga klikniec w menu*/
    private void MenuEvents(MouseEvent e)
    {
        int buttonh=125;
        int buttonw=120;
        int n=1;
        if (e.getX() > 420 && e.getX() < 420+buttonw && e.getY() > 30 && e.getY() < 30+buttonh*n++) {
            GS.goToPrewTask();
        }

        if (e.getX() > 420 && e.getX() < 420+buttonw && e.getY() >  20*(n-1)+30+buttonh*(n-1) && e.getY() < 20*n+30+buttonh*n++) {
            recipe.reset();
            menuTime = 0;
            GS.reset();
        }
        if (e.getX() > 420 && e.getX() < 420+buttonw && e.getY() >  20*(n-1)+30+buttonh*(n-1) && e.getY() < 20*n+30+buttonh*n++) {
            GS.load(recipe);
            menuTime = 0;
        }

        if (e.getX() > 420 && e.getX() < 420+buttonw && e.getY() >  20*(n-1)+30+buttonh*(n-1) && e.getY() < 20*n+30+buttonh*n++) {
            GS.save();
            GS.goToPrewTask();
        }

        if (e.getX() > 420 && e.getX() < 420+buttonw && e.getY() >  20*(n-1)+30+buttonh*(n-1) && e.getY() < 20*n+30+buttonh*n++)
            System.exit(0);

    }

    //funkcje zadania 1 (1) w nawiasie wartość parametru task w GameState
    /**wykonuje przemieszczenie obiektu Canvas do okreslonych wspolrzednych*/
    private void movePanel()
    {

        if (x == toX) {


        } else if (x > toX) {
            x -= 30;

        } else {
            x += 30;

        }

        if (y == toY) {


        } else if (y > toY) {
            y -= 30;

        } else {
            y += 30;

        }
        setLocation(x, y);
    }
    /**przypisuje klikniety produkt do zmiennej i ustawia kursora
     * @param i  wartosc iteratora petli for w ktorej zostala uzyta*/
    private void pickProduct(int i)
    {
        productInHand = products.get(i);
        productPicked = true;
        try {
            setCursor(productInHand.c);
        } catch (Exception ex) {
            System.out.println("cursor change error");
        }
        toX = 0;
    }
    /**operacja odlozenia produktu do miski*/
    private void placeProduct()
    {
        if (productPicked) {
            setCursor(Cursor.getDefaultCursor());
            if (recipe.products.containsKey(productInHand.name)) {
                recipe.products.put(productInHand.name, true);
            } else {

            }
            productPicked = false;
            productInHand = null;
        }
    }
    /**utworzenie i przyporzadkowanie produktow do listy*/
    private void initComponents()
    {
        Product p1 = new Product("butter", launcher.products[0], 0, 0, this);
        Product p2 = new Product("cocoa", launcher.products[1], 0, 0, this);
        Product p3 = new Product("eggs", launcher.products[2], 0, 0, this);
        Product p4 = new Product("flour", launcher.products[3], 0, 0, this);
        Product p5 = new Product("honey", launcher.products[4], 0, 0, this);
        Product p6 = new Product("milk", launcher.products[5], 0, 0, this);
        Product p7 = new Product("backing powder", launcher.products[6], 0, 0, this);
        Product p8 = new Product("salt", launcher.products[7], 0, 0, this);
        Product p9 = new Product("sugar", launcher.products[8], 0, 0, this);
        Product p10 = new Product("water", launcher.products[9], 0, 0, this);

    }
    /**0bsluga myszy dla zadania 1, tasuje liste produktow */
    private void MouseEvents1(MouseEvent e)
    {
        if (e.getX() > 1200 && e.getX() < 1280) {//obsługa przycisku przewijania w lewo
            toX = -300;
            System.out.println("click1" + toX + "   " + x);
            //losowanie ustawień produktow
            Collections.shuffle(products);

        }
        if (e.getX() < 400) {
            toX = 0;
            System.out.println("click2");
        }
        if (e.getX() > 1280 && e.getX() < 1430) {//sprawdza który produkt w rzędzie 1 został kiknięty
            for (int i = 0; i < 5; i++) {
                if (e.getY() < 20 + (i + 1) * 133) {
                    pickProduct(i);
                    break;
                }
            }
        }
        if (e.getX() > 1430 && e.getX() < 1580) {//w rzędzie 2
            for (int i = 5; i < 10; i++)//od 5 żeby w pickproduct wybrać od produktu  o indeksie 5
            {
                if (e.getY() < 20 + (i - 4) * 133) {
                    pickProduct(i);
                    break;
                }
            }
        }
        if (productPicked && e.getX() > 400 && e.getX() < 1000 && e.getY() > 400 && e.getY() < 600) {
            placeProduct();
            System.out.println("place");

        }
    }

    //funkcje zadania 2 (3)
    /**losuje przyciski obslugi miksera*/
    private void choseKeys()
    {
        Random rand = new Random();
        targetKey = 65 + rand.nextInt(22);//65 litera a + losowo od 0 do 22 bo losujemy pierwszą literę a za nia muszą być jeszcze 3
    }
    /**obsluga klawiatury dla zadania 2, gdy klawisz jest poprawny aktualizuje objekt klasy Recipe*/
    private void KeyEvent2(KeyEvent k, int target)
    {
        if (k.getKeyCode() == target + 1) {
            if (recipe.mixerSpeed.containsKey("1")) recipe.mixerSpeed.put("1", true);
        }
        if (k.getKeyCode() == target + 2) {
            if (recipe.mixerSpeed.containsKey("2")) recipe.mixerSpeed.put("2", true);
        }
        if (k.getKeyCode() == target + 3) {
            if (recipe.mixerSpeed.containsKey("3")) recipe.mixerSpeed.put("3", true);
        }

    }

    //funkcje zadania 3 (5)
    /**obsluga zatrzymania wskaznika temperatury*/
    private void KeyEvent3(KeyEvent k){
        if (k.getKeyCode() == 32) {
            GS.updatePrecision(parseDouble(recipe.temperature), (termoX-launcher.OVENSHIFT)/2.1); //21 poniewaz podzialka na piekarniku ma 21 px
            System.out.println("prec   " + GS.printprec());
            GS.setTask();

        }
    }
    //funkcje zadania 5 (9)

}


