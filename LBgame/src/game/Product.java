package game;

import javax.swing.*;
import java.awt.*;


/**opisuje wszystkie produkty wystepujace w pierwszym zadaniu*/
public class Product extends Decoration {
    /**kursor jaki ukarze sie po wybraniu produtku*/
    public Cursor c;
    /**nazwa produktu*/
    /**obiekt gry w ktorym sa umieszczone produkty*/
    private Game game;
    /** konstruktor ustawia podstawowe parametry
     * @param n nazwa
     * @param image  obraz konkretnego produktu
     * @param x wspolrzedna
     * @param y wspolrzedna
     * @param game obiekt gry */
    public Product(String n,ImageIcon image, int x, int y, Game game) {
        super(n,image, x, y);
        this.game=game;
        c=Toolkit.getDefaultToolkit().createCustomCursor(this.image.getImage().getScaledInstance(32, 32,  java.awt.Image.SCALE_SMOOTH),new Point(15,20) , name);
        this.game.products.add(this);
    }
    /**ustawia produkt w odpowiednim miejscu w jednej z dwuch kolumn, podstawa do okreslenia pozycji jest indeks w tablicy produktow obiektu game*/
    public void setposition(){
        int i = game.products.indexOf(this);
        if (i<5){
            x=1265;
            y=10+(140*i);
        }
        else {
            x=1265+150;
            y=10+(140*(i-5));
        }


    }
}
