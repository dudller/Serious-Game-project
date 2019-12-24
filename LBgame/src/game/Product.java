package game;

import javax.swing.*;
import java.awt.*;


public class Product extends Entity {
    Cursor c; //kursor jaki ukarze się po wybraniu produtku
    String name;//nazwa po której rozpoznajemy produkt
    Game game;
    Toolkit tool = Toolkit.getDefaultToolkit();
    public Product(String n,ImageIcon image, int x, int y, Game game) {
        super(image, x, y);
        this.name=n;
        this.game=game;
        c=tool.createCustomCursor(this.image.getImage().getScaledInstance(32, 32,  java.awt.Image.SCALE_SMOOTH),new Point(15,20) , name);
        this.game.products.add(this);
    }
    public void setposition(){//na podstawie pozycji w liście ustawia współrzędne produktu
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
