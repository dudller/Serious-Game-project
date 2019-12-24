package game;

import java.util.ArrayList;

public class GameState {
    public int termospeed;//szybkość poruszania się wskaźnika temperatury
    public int level; //poziom trudności
    public int task=1;//aktualnie wykonywane zadanie, numery parzyste to aktywne zadania a nieparzyste to "zadania" informujące o ukończeniu zadania
    public int prewiousTask;//ostatnie poprzednio wykonywane zadanie
    public long startTime;//czas rozpoczęcia zadania
    public long endtime;//czas zakończenia zadaia
    private ArrayList scores;//tablica przechowująca wyniki czasowe
    private ArrayList precision;//tabblica przechowująca wyniki precyzji wykonania
    public GameState(){
        level=1;
        task=0;
        prewiousTask=1;
        termospeed=20;
        scores=new ArrayList();
        precision=new ArrayList();

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
        scores.add(endtime-startTime);
    }
    public ArrayList pintscores(){
        return scores;
    }
    public ArrayList printprec(){return precision;}
    //dodanie wyników z zakresy precyzji do tablicy
    public void updatePrecision(double target, double score){
        double prec = Math.abs(target-score)*level;
        if(prec>0){
            prec=1/prec;
        }
        else {
            prec=1.0;
        }
        precision.add(prec);
    }

}
