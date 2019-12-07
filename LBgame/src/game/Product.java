package game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Product extends Entity {
    Cursor c;
    String name;
    Game game;
    Toolkit tool = Toolkit.getDefaultToolkit();
    public Product(String n,ImageIcon image, int x, int y, Game game) {
        super(image, x, y);
        this.name=n;
        this.game=game;
        //c=tool.createCustomCursor(image.getImage(),new Point(1280,450) , "product");
        this.game.products.add(this);
    }
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
