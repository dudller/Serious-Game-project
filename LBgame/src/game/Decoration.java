package game;

import javax.swing.*;
/** klasa obiektow ktore poza wspolrzednymi musza posiadac takze nazwe */
public class Decoration extends Entity {
    public String name;
    public Decoration(String name,ImageIcon image, int x, int y) {
        super(image, x, y);
        this.name=name;
    }
}
