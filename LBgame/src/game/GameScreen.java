package game;

import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;
import java.awt.event.*;


public class GameScreen extends Canvas {
    private int x,y;
    int phase;
    Cursor[] cursors;
    Toolkit tool;

    public GameScreen(int width, int height) {
        super();

        setBackground(Color.cyan);
        setSize(width, height);
        phase=0;
        x=0;
        y=0;
        //stworzenie ikonek kursorów
        tool=Toolkit.getDefaultToolkit();
        cursors=new Cursor[8];
        for(int i=0;i<8;i++){
            cursors[i]=tool.createCustomCursor(launcher.products[i],getMousePosition(),"k"+i);
        }

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                        switch (phase) {
                            case 0:
                                if (me.getX() > 1200) {
                                    x = -launcher.canvasShift;
                                    phase = 1;
                                }
                                break;
                            case 1:
                                if (me.getX() < 80) {
                                    x = 0;
                                    phase = 0;
                                }
                                else if (me.getX()>launcher.windowWidth-launcher.canvasShift){
                                    for(int j=2;j<=0;j--){
                                        if (me.getX()<launcher.windowWidth-j*100){
                                            for (int i =1; i<9;i++){
                                                if (me.getY()<i*100){
                                                    setCursor(cursors[i-1+(8*j)]);//jak j jest 1 a i jest 5 to bierzemy kursor 12 z tabeli kursorow 3x8 indeksowane od lewej w dół
                                                    x=0;
                                                    phase=3;
                                                }
                                            }

                                        }
                                    }

                                }
                                break;
                        }
                repaint();
            }
        });
    }

        public void paint (Graphics g){
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(launcher.background1,x,y,null);
                switch (phase){
                    case 0:

                        break;
                    case 1:

                        break;
                }

            }
        }





