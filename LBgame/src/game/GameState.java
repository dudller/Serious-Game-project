package game;

import java.awt.*;
import java.io.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.imageio.IIOException;
import javax.swing.*;
import java.util.Stack;
/** odpowiada za zapis oraz interpretacje wynikow gracza */
public class GameState {
    /**szybkosc poruszania sie wskaznika temperatury*/
    public int termospeed;
    /**poziom trudnosci*/
    public int level;
    /**aktualnie wykonywane zadanie, numery nieparzyste to aktywne zadania a parzyste to "zadania" informujace o ukonczeniu zadania*/
    public int task;
    /**poprzednio wykonywane zadanie*/
    public int prewiousTask;
    /**pieniadze zarabiane w grze reprezentujace ogolna forme wyniku w grze*/
    public int money;
    /**czas rozpoczecia zadania*/
    public double startTime;
    /**czas zakonczenia zadaia*/
    public double endtime;
    /**stos przechowujacy wyniki czasowe*/
    private Stack<Double> scores;
    /**stos przechowujacy wyniki precyzji wykonania*/
    private Stack<Double> precision;
    /** wartosc srednia wyniku */
    public double avgScore,avgPrecision;
    /** wykres wynikow */
    public JFreeChart chart1,chart2;
    public GameState(){
        money=0;
        level=1;
        task=1;
        prewiousTask=0;
        termospeed=25;
        scores=new Stack();
        precision=new Stack();

    }
    /** zwieksza zmienna task o 1 oraz zapisuje poprzednio wykonywane zadanie*/
    public void setTask(){
        prewiousTask=task;
        task++;
    }
    /** ustawia zmienna task na konkretna cyfre oraz zapisuje poprzednio wykonywane zadanie
     * @param t docelowa wartosc zmiennej task*/
    public void setTask(int t){
        prewiousTask=task;
        task=t;
    }
    /** przywraca poprzednia wartosc zmiennej task*/
    public void goToPrewTask(){
        task=prewiousTask;
    }
    /** przywraca wszystkie wartosci opisujace postep gracza do stanu poczatkowego*/
    public void reset(){
        System.out.println("reset");
        scores.clear();
        precision.clear();
        level=1;
        money=0;
        task=1;
        prewiousTask=1;
        startTime= System.currentTimeMillis();

    }
    /** zapisuje do 10 najswierzszych rekordow postepu gracza do plikow txt*/
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
                br3.write(Integer.toString(money)+"\n");
                br3.close();
                fout3.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    /** wczytuje dane o postepie gracza z plikow o lie one istnieja*/
    public void load(Recipe r){
        System.out.println("Lodading");
        boolean errorOccured=false; //jesli wystapil blad przy pobieraniu danych zostaje ustawiona na false i powoduje wywolanie funkcji reset
        Stack<String> buffer=new Stack<>();//poniewaz wyniki sa zapisywane na stosach i ze stosow sa wpisywane do pliku konieczne jest uzycie stosu buforujacego aby zachowac odpowiednia kolejnosc wczytywanych danych
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
        }catch(Exception e){System.out.println(e); errorOccured=true;}
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
        }catch(Exception e){System.out.println(e);errorOccured=true;}
        try{
            FileReader fin3=new FileReader("src/state.txt");
            BufferedReader br3=new BufferedReader(fin3);
            level=Integer.parseInt(br3.readLine());
            task=Integer.parseInt(br3.readLine());
            money=Integer.parseInt(br3.readLine());
            prewiousTask=task;
            br3.close();
            fin3.close();
        }catch(Exception e){System.out.println(e);errorOccured=true;}
        //przywrócenie postępu widocznego na przepisie
        if (!errorOccured){
            int remake=task;
            while (remake!=0) {
                switch (remake) {
                    case 2:
                        for (String i : r.products.keySet()) {
                            System.out.println("done1");
                            r.products.put(i, true);
                        }
                        remake--;
                        break;
                    case 4:
                        for (String j : r.mixerSpeed.keySet()) {
                            System.out.println("done2");
                            r.mixerSpeed.put(j, true);
                        }
                        remake--;
                        break;
                    case 6:
                        for (String k : r.decorations.keySet()) {
                            r.decorations.put(k, true);
                        }
                        remake--;
                        break;
                    case 1:
                    case 3:
                    case 5:
                        remake--;
                        break;

                }
                startTime = System.currentTimeMillis();
                endtime = 0;
                doStatistics();
            }
        }
        else reset();


    }
    /**zwiekszenie poziomu i modyfikacja parametrow z nim zwiazanych*/
    public void levelUp(){
        level++;
        termospeed+=termospeed*level/5;
        task=1;
        prewiousTask=1;
    }
    /**dodanie wynikow czasowych do stosu oraz przeliczenie ich na wartosc zarobionych pieniedzy*/
    public void updateScores(){
        scores.push(endtime-startTime);
        money+=1/(endtime-startTime)*100000/level;
        doStatistics();
    }
    public Stack pintscores(){
        return scores;
    }
    public Stack printprec(){return precision;}

    /**dodanie wynikow z zakresu precyzji do stosu oraz przeliczenie ich na wartosc zarobionych pieniedzy*/
    public void updatePrecision(double target, double score){
        double prec = Math.abs(target-score);
        precision.push(prec);
        prec*=level;
        if(prec>0){
            prec=1/prec;
        }
        else {
            prec=1.0;
        }

        money+=prec*10/level;
        doStatistics();
    }
    /**wykonuje obliczenia prowadzace do okreslenia srednich wynikow na podstawie 10 ostatnich wynikow oraz tworzy wykresy ostatnich osiagniec obu dziedzinach*/
    public void doStatistics()
    {
        Stack<Double> buffstack=(Stack)scores.clone();
        int counter=0;//licznik ostatnich wyników użytych do obliczenia średniej
        avgScore=0;//resetowanie średnich
        avgPrecision=0;
        var series1 = new XYSeries("response time");//zmienne serii danych dla wykresów
        var series2 = new XYSeries("precision");
        var dataset1 = new XYSeriesCollection();//zbiory danych niezbędne do wykresów
        var dataset2 = new XYSeriesCollection();
        while (!buffstack.isEmpty())//pętla dodawania wyników
        {
            double buf=buffstack.pop();
            avgScore+=buf;
            series1.add(counter++,buf);
            if (counter>10)break;
        }
        avgScore/=counter;//obliczenie średniej
        buffstack=(Stack)precision.clone();
        counter=0;
        while (!buffstack.isEmpty()){
            double buf=buffstack.pop();
            avgPrecision+=buf;
            counter++;
            series2.add(counter,buf);//dodanie punktu serii wykresu
            if (counter>10)break;
        }
        avgPrecision/=counter;
        //dodanie obu serii do zbiorów danych wykresu
        dataset1.addSeries(series1);
        dataset2.addSeries(series2);

        chart1 = ChartFactory.createXYLineChart(//stworzenie wykresu o określonych parametrach
                "Your time scores",
                "",
                "Time [ms]",
                dataset1,
                PlotOrientation.VERTICAL,
                false,
                false,
                false
        );
        chart2 = ChartFactory.createXYLineChart(
                "Your precision scores",
                "",
                "distance from target [px]",
                dataset2,
                PlotOrientation.VERTICAL,
                false,
                false,
                false
        );

        XYPlot plot1 = chart1.getXYPlot();
        XYPlot plot2 = chart2.getXYPlot();

        var renderer = new XYLineAndShapeRenderer();//obiekt renderujący wykres

        renderer.setSeriesPaint(0, Color.MAGENTA);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        //ustawienie parametrów renderowania
        plot1.setRenderer(renderer);
        plot1.setBackgroundPaint(Color.yellow);
        plot1.setRangeGridlinesVisible(true);
        plot1.setDomainGridlinesVisible(true);
        plot2.setRenderer(renderer);
        plot2.setBackgroundPaint(Color.yellow);
        plot2.setRangeGridlinesVisible(true);
        plot2.setDomainGridlinesVisible(true);
        //nadanie tytułu wukresom
        chart1.setTitle(new TextTitle("Your time scores",
                new Font("Helvetica", Font.BOLD, 18))
        );
        chart2.setTitle(new TextTitle("Your precision scores",
                new Font("Helvetica", Font.BOLD, 18))
        );
        //zapisanie wykresów do plików
        File f1 = new File("src/images/chart1.jpg");
        File f2 = new File("src/images/chart2.jpg");
        try {
            ChartUtilities.saveChartAsJPEG(f1, chart1, 500, 300);
            ChartUtilities.saveChartAsJPEG(f2, chart2, 500, 300);

        }
        catch (IOException e){};
    }

}
