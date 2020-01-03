package game;

import javax.swing.*;
import java.awt.*;


/**opisuje wszystkie produkty wystepujace w pierwszym zadaniu*/
public class Product extends Entity {
    /**kursor jaki ukarze sie po wybraniu produtku*/
    Cursor c;
    /**nazwa produktu*/
    String name;
    /**obiekt gry w ktorym sa umieszczone produkty*/
    Game game;
    /** konstruktor ustawia podstawowe parametry
     * @param n nazwa
     * @param image  obraz konkretnego produktu
     * @param x wspolrzedna
     * @param y wspolrzedna
     * @param game obiekt gry */
    public Product(String n,ImageIcon image, int x, int y, Game game) {
        super(image, x, y);
        this.name=n;
        this.game=game;
        c=Toolkit.getDefaultToolkit().createCustomCursor(this.image.getImage().getScaledInstance(32, 32,  java.awt.Image.SCALE_SMOOTH),new Point(15,20) , name);
        this.game.products.add(this);
    }
    /**ustawia produkt w odpowiednim miejscu w jednej z dwuch kolumn, podstawa do okreslenia pozycji jest indeks w tablicy produktow obiektu game*/
    public void setposition(){
        int i = game.products.indexOf(this);
        if (i<5){
            x=1285;
            y=20+(140*i);
        }
        else {
            x=1285+151;
            y=20+(140*(i-5));
        }


    }
}
