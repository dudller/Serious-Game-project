package game;

import javax.swing.*;


 public abstract class Entity {
    public ImageIcon image;
    public int x,y,width,height;
    public boolean visible=false;
    public Entity(ImageIcon image,int x,int y){
        this.image=image;
        this.x=x;
        this.y=y;
    }
}
