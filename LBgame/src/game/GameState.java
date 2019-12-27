package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Stack;

public class GameState {
    public int termospeed;//szybkość poruszania się wskaźnika temperatury
    public int level; //poziom trudności
    public int task=1;//aktualnie wykonywane zadanie, numery parzyste to aktywne zadania a nieparzyste to "zadania" informujące o ukończeniu zadania
    public int prewiousTask;//ostatnie poprzednio wykonywane zadanie
    public double startTime;//czas rozpoczęcia zadania
    public double endtime;//czas zakończenia zadaia
    private Stack<Double> scores;//tablica przechowująca wyniki czasowe
    private Stack<Double> precision;//tabblica przechowująca wyniki precyzji wykonania
    private ArrayList totalScore;//tablica wszystkich wyników
    public GameState(){
        level=1;
        task=0;
        prewiousTask=0;
        termospeed=20;
        scores=new Stack();
        precision=new Stack();

    }
    //ustawienie kolejnego zadania
    public void setTask(){
        prewiousTask=task;
        task++;
    }
    public void setTask(int t){
        prewiousTask=task;
        task=t;
    }

    public void goToPrewTask(){
        task=prewiousTask;
    }
    public void reset(){
        System.out.println("reset");
        scores.clear();
        precision.clear();
        task=1;
        prewiousTask=1;
        startTime= System.currentTimeMillis();

    }
    public void save(){
        if(!scores.isEmpty()) {
            System.out.println("saving");
            int scoreCounter=1;
            try {
                FileWriter fout = new FileWriter("src/scores.txt");
                BufferedWriter br = new BufferedWriter(fout);
                while (!scores.isEmpty()) {
                    if (scoreCounter>10) break;
                    br.write(Double.toString(scores.pop()) + "\n");
                    scoreCounter++;
                }
                br.close();
                fout.close();
            } catch (Exception e) {
                System.out.println(e);
            }
            try {
                FileWriter fout2 = new FileWriter("src/precision.txt");
                BufferedWriter br2 = new BufferedWriter(fout2);
                while (!precision.isEmpty()) {
                    if (scoreCounter>20) break;
                    br2.write(Double.toString(precision.pop()) + "\n");
                    scoreCounter++;
                }
                br2.close();
                fout2.close();
            } catch (Exception e) {
                System.out.println(e);
            }
            try {
                FileWriter fout3 = new FileWriter("src/state.txt");
                BufferedWriter br3 = new BufferedWriter(fout3);
                br3.write(Integer.toString(level)+"\n");
                br3.write(Integer.toString(prewiousTask)+"\n");

                br3.close();
                fout3.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    public void load(Recipe r){
        System.out.println("Lodading");
        Stack<String> buffer=new Stack<>();
        try{
            FileReader fin=new FileReader("src/scores.txt");
            BufferedReader br=new BufferedReader(fin);

            String s="";
            while((s=br.readLine())!= null){
                buffer.push(s);
            }
            br.close();
            fin.close();
            while (!buffer.isEmpty()){
                scores.push(Double.parseDouble(buffer.pop()));
            }
        }catch(Exception e){System.out.println(e);}
        try{
            FileReader fin2=new FileReader("src/precision.txt");
            BufferedReader br2=new BufferedReader(fin2);
            String s="";
            while((s=br2.readLine())!= null){
                buffer.push(s);
            }
            br2.close();
            fin2.close();
            while (!buffer.isEmpty()){
                precision.push(Double.parseDouble(buffer.pop()));
            }
        }catch(Exception e){System.out.println(e);}
        try{
            FileReader fin3=new FileReader("src/state.txt");
            BufferedReader br3=new BufferedReader(fin3);
            level=Integer.parseInt(br3.readLine());
            task=Integer.parseInt(br3.readLine());
            prewiousTask=task;
            br3.close();
            fin3.close();
        }catch(Exception e){System.out.println(e);}
        int remake=task;
        while (remake!=0){
            switch (remake){
                case 1:
                    for (String i : r.products.keySet()){
                        System.out.println("done1");
                        r.products.put(i,true);
                    }
                    remake--;
                    break;
                case 3:
                    for (String j : r.mixerSpeed.keySet()){
                        System.out.println("done2");
                        r.mixerSpeed.put(j,true);
                    }
                    remake--;
                    break;
                case 5:
                    for (String k : r.decorations.keySet()){
                        r.decorations.put(k,true);
                    }
                    remake--;
                    break;
                case 2:
                case 4:
                case 6:
                    remake--;
                    break;

            }
            startTime=System.currentTimeMillis();
            endtime=0;
        }

    }
    //powrót do poprzedniego zadania
    public void backToTask(){
        task=prewiousTask;
    }
    //zwiększenie poziomu i modyfikacja parametró z nim związanych
    public void levelUp(){
        level++;
        termospeed+=termospeed*level/5;
        task=1;
        prewiousTask=1;
    }
    //dodanie wyników czasowych do tablicy
    public void updateScores(){
        scores.push(endtime-startTime);
    }
    public Stack pintscores(){
        return scores;
    }
    public Stack printprec(){return precision;}
    //dodanie wyników z zakresy precyzji do tablicy
    public void updatePrecision(double target, double score){
        double prec = Math.abs(target-score)*level;
        if(prec>0){
            prec=1/prec;
        }
        else {
            prec=1.0;
        }
        precision.push(prec);
    }

}
