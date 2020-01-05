package game;
import java.io.*;
import javax.sound.sampled.*;
/** opisuje obiekty ktore moga odtwarzac dzwiek */
public class Sound {
    /** plpik dzwiekowy */
    private File file;
    /** klip dzwieku */
    private Clip clip;
    /** tworzy obiekt z plikiem o podanej sciezce
     * @param filename sciezka pliku dzwiekowego*/
    public Sound(String filename){
        file=new File(filename);

    }
    /** odtwarza dzwiek */
    public void play() {
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(file);
            //ustalenie odpowiedniego formatu pliku dzwiekowego
            AudioFormat format = stream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            //odpowada za zamkniecie cliku dzwiekowego po jego zakonczeniu
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP)
                    clip.close();
            });
            clip.open(stream);
            clip.start();
        } catch (Exception e) {

        }
    }
}
