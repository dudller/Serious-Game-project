package game;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Hashtable;
import java.util.Map;

public class Recipe {
    public Hashtable<String,Boolean> products;
    public Hashtable<String,Boolean> mixerSpeed;
    public String temperature;
    public Hashtable<String,Boolean> decorations;
    public Recipe(){
        products=new Hashtable<String,Boolean>();
        mixerSpeed=new Hashtable<String,Boolean>();
        decorations=new Hashtable<String,Boolean>();
        try{
            FileReader fin=new FileReader("src/recipe.txt");
            String s = "";
            BufferedReader br=new BufferedReader(fin);
            int  i =1;
            Hashtable current=products;
            while((s=br.readLine())!= null){
                if (current!=null) {
                    if (s.equals("******")) current = setTable(i++);
                    else current.put(s, false);
                }
                else temperature=s;
            }
            br.close();
            fin.close();
        }catch(Exception e){System.out.println(e);}
    }
    private Hashtable setTable(int i){
        if (i==1){
            return mixerSpeed;
        }
        if (i==2){
            return decorations;
        }
        else return null;
    }

    public void display(Graphics g,int fontSize ,int x,int y){
        int n=0;
        g.setFont(new Font("Helvetica", Font.PLAIN, fontSize));
        g.drawString("Ingredients:",x,y+fontSize*n++ );
        String text;
        for( String k : products.keySet()){
            text=k;
            if (products.get(k)) text+=" done \n";
            g.drawString(text,x,y+fontSize*n++ );
        }
        g.drawString("Set mixer speed to:",x,y+fontSize*n++ );
        for (String i : mixerSpeed.keySet()){
            text=i;
            if (mixerSpeed.get(i)) text+=" done \n";
            g.drawString(text,x,y+fontSize*n++ );
        }
        g.drawString("Set oven temperature to: ",x,y+fontSize*n++ );
        g.drawString(temperature,x,y+fontSize*n++ );
        g.drawString("Place decorations:" ,x,y+fontSize*n++ );
        for (String d: decorations.keySet()){
            text=d;
            if (decorations.get(d)) text+=" done \n";
            g.drawString(text,x,y+fontSize*n++ );
        }

    }

    
}
