package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;
/** odpowiada za generowanie i wyswietlanie wzoru/ ukladu dekoracji oraz wyswietlanie polozonych juz dekoracji a takze obliczanie wstepnego wyniku na podstawie odleglosci miedzy polozonymi a wygenerowanymi punktami */
public class Pattern extends Entity {
    /** przechowuje punktu klikniec myszy podczasz ukladania dekoracji przez gracza */
    public Map<String,ArrayList<Point>> points;
    /** przechowuje ilosc dostepnych prob dla kazdej dekoracji*/
    public Map<String,Integer> decorCounter;
    /** przechowuje punktu docelowe polozenia dekoracji*/
    public Map<String,ArrayList<Point>> targets;
    /** obiekt game */
    public Game game;
    /** licznik dekoracji*/
    public int numberOfDecor;
    /** przechowuje obrazek wzornika dla kazdej dekoracji */
    private Map<String,Image> images;
    /** parametry pola rysowania wzoru */
    private int heigth,weight;
    /** przypisanie podstawowych parametrow
     * @param g obiekt gry
     * @param y wspolrzedna y lewego gornego rogu obszaru wzoru
     * @param x wspolrzedna x lewego gornego rogu obszaru wzoru
     * @param h wysokosc obszaru wzoru
     * @param w szerokosc obszaru wzoru
     * @param i1 obraz wzornika dekoracji strawberry
     * @param i2 obraz wzornika dekoracji blueberry
     * @param i3 obraz wzornika dekoracji star*/
    public Pattern(ImageIcon i1,ImageIcon i2,ImageIcon i3, int x, int y, int h, int w, Game g){
        super(i1, x,y);
        heigth=h;
        weight=w;
        game=g;
        points=new HashMap<>();
        decorCounter=new HashMap<>();
        targets =new HashMap<>();
        images=new HashMap<>();
        images.put("strawberry",i1.getImage());
        images.put("blueberry",i2.getImage());
        images.put("star",i3.getImage());

        generate();

    }
    /** generuje losowy uklad punktow dla kazdej z dekoracji bazujac na przepisie gry */
    public void generate(){
        numberOfDecor=1+game.GS.level/2;
        if (numberOfDecor>5) numberOfDecor=5;
        Random rand = new Random();
        //dla kazdej dekoracji w przepisie tworzymy tablice w ktorej nastepnie tworzymy numberOfDecor losowych punktow
        for (String k : game.recipe.decorations.keySet()){

            decorCounter.put(k,numberOfDecor);
            targets.put(k,new ArrayList<>());
            for (int i=0; i<numberOfDecor; i++){
                targets.get(k).add(new Point());
                targets.get(k).get(i).setLocation(x+weight/10*rand.nextInt(10),y+heigth/10*rand.nextInt(10)); //losowe punkty co 1/10 szerokosci lub wysokosci to zmniejsza ryzyko nakladania sie wyswietlanych wzornikow
            }
            points.put(k,new ArrayList<>());//tworzenie pustej jeszcze tablicy dla puntkow wprowadzonych przez gracza
        }
    }
    /** wyswietla wzor oraz dekoracje polozone przez gracza */
    public void drawPattern(Graphics g){
        //dla kazdej dekoracji z przepisu wybieramy liste i dla kazdego punktu z listy rysujemy
        for (String k : game.recipe.decorations.keySet()){
            ArrayList<Point> targetpoints = targets.get(k);
            ArrayList<Point> playerpoints = points.get(k);
            for (Point p : targetpoints){
                g.drawImage(images.get(k),p.x,p.y,null);
            }
            for (Point p :playerpoints){
                g.drawImage(launcher.decorations.get(k).getImage(),p.x,p.y,null);
            }

        }
    }
    /** informuje czy sa jeszcze dostepne proby polozenia dekoracji
     * @return true jesli sa dostepne, false jesli nie*/
    public boolean isDecorAvaible(String n){
        try {
            return decorCounter.get(n) !=0;
        }
        catch (NullPointerException e){
            return false;
        }
    }
    /** zmiejsza ilosc prob polozenia dekoracji o 1*/
    public void countDown(String n){
        decorCounter.put(n,decorCounter.get(n)-1);
    }
    /** dla klikniecia graca dodaje punkt klikniecia do odpowiedniej listy bazujac na podanej nazwie dekoracji */
    public void addPoint(MouseEvent e,String n){
        for (String k : game.recipe.decorations.keySet()){
            if (k.equals(n))points.get(k).add(e.getPoint());
        }
        countDown(n);
        for (String k : game.recipe.decorations.keySet()){
            if (!isDecorAvaible(k)) game.recipe.decorations.put(k,true);
        }
    }
    /** oblicza wstepny wynik bazujac na odleglosci pomiedzy punktami
     * @return wynik sredniej odleglosci miedzy wzorem a ukladem gracza */
    public double givePrec(){
        double dist=0;
        ArrayList<Double> prec= new ArrayList();
        for (String k : targets.keySet()){
            for (Point p : targets.get(k)){
                for (String k2 : points.keySet()){

                    ArrayList<Double> distance=new ArrayList<>();
                    for (Point p2 : points.get(k2)){
                        double xdist= Math.abs(p.x-p2.x);
                        double ydist=Math.abs(p.y-p2.y);
                        distance.add(Math.sqrt(Math.pow(xdist,2)+Math.pow(ydist,2)));
                    }
                    Collections.sort(distance);
                    dist=distance.get(0);
                }
                prec.add(dist);
            }
        }
        double avg =0;
        for (double d: prec){
            avg+=d;
        }
        return avg/ prec.size();
    }
}
