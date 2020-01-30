package game;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

import java.util.Map;

/** klasa obslugujaca obiekty przedstawiajace przepis wyswietlany i modyfikowany w trakcie gry */
public class Recipe {
    /**obiekt Map zwierajacy wszystkie produkty ktore trzeba umiescic w misce w zadaniu 1*/
    public Map<String,Boolean> products;
    /**obiekt Map zawierajacy prędkosci miksera jakie musza zostac uzyte w zadaniu 2*/
    public Map<String,Boolean> mixerSpeed;
    /** przechowuje docelowa temperature piekarnika */
    public String temperature;
    /**obiekt Map zawierajacy wszystkie dekoracje ktore trzeba umiescic w zadaniu 5*/
    public Map<String,Boolean> decorations;
    /** tworzy obiekty Map oraz pobiera dane z pliku txt jesli takowy istnieje */
    public Recipe(){
        products=new HashMap<String,Boolean>();
        mixerSpeed=new HashMap<String,Boolean>();
        decorations=new HashMap<>();
        //pobranie przepisu z pliku
        try{
            FileReader fin=new FileReader("src/recipe.txt");
            String s = "";
            BufferedReader br=new BufferedReader(fin);
            int  i =1;
            Map current=products; //aktualna tablica do której wpisujemy dane z pliku txt
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
    /**zmiana aktywnej tabeli do ktorej przypisywane sa dane
     * @param i licznik sekwencji sygnalizujacej koniecznosc zmiany tabeli danych
     * @return zwraca kolejny obiekt Map do ktorego beda wpisywane dane lub w przypadku gdy wlasnie wypelnil ostatnia tabele zwraca null*/
    private Map setTable(int i){
        if (i==1){
            return mixerSpeed;
        }
        if (i==2){
            return decorations;
        }
        else return null;
    }
    /**wyswietla przepis wedlug okreslonego ukladu
     * @param x wspolrzedna x lewego gornego rogu obszaru przepisu
     * @param y wspolrzedna y lewego gornego rogu obszaru przepisu
     * @param fontSize rozmiar czcionki wyswietlanego przepisu
     * @param g obiegk Graphics wykorzystywany do rysowania*/
    public void display(Graphics g,int fontSize ,int x,int y){
        int n=0;
        g.setFont(new Font("Helvetica", Font.PLAIN, fontSize));
        g.setColor(Color.BLACK);
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
    /** ustawia wartosc Value jako false we wszystkich obiektach Hashmap */
    public void reset(){
        for( String k : products.keySet()){
            products.put(k,false);
        }
        for (String i : mixerSpeed.keySet()){
           mixerSpeed.put(i,false);
        }
        for (String d: decorations.keySet()){
            decorations.put(d,false);
        }
    }

    
}
