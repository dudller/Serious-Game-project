package game;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Hashtable;

public class Recipe {
    public Hashtable<String,Boolean> products; //tablica zwierająca wszystkie produkty które trzeba umieścić w miesce zadanie 1
    public Hashtable<String,Boolean> mixerSpeed;//tablica zawierająca prędkości miksera jakie muszą zostać użyte
    public String temperature;//temperatura docelowa piekarnika
    public Hashtable<String,Boolean> decorations;//tablica zawierająca wszystkie dekoracje które trzeba umieścić w zadaniu 5
    public Recipe(){
        products=new Hashtable<String,Boolean>();
        mixerSpeed=new Hashtable<String,Boolean>();
        decorations=new Hashtable<String,Boolean>();
        //pobranie przepisu z pliku
        try{
            FileReader fin=new FileReader("src/recipe.txt");
            String s = "";
            BufferedReader br=new BufferedReader(fin);
            int  i =1;
            Hashtable current=products; //aktualna tablica do której wpisujemy dane z pliku txt
            while((s=br.readLine())!= null){
                if (current!=null) {
                    if (s.equals("******")) current = setTable(i++); //wystąpienie w pliku linii ****** powoduje przełączenie tabeli wpisywania
                    else current.put(s, false);//przypisanie do tabeli danych dopuki tabele istnieją
                }
                else temperature=s;
            }
            br.close();
            fin.close();
        }catch(Exception e){System.out.println(e);}
    }
    //zmiana aktywnej tabeli do której przypisywane są dane
    private Hashtable setTable(int i){
        if (i==1){
            return mixerSpeed;
        }
        if (i==2){
            return decorations;
        }
        else return null;
    }
    //wyświetlane przepisu według określonego układu
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
