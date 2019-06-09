import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Grafic extends Frame{

    private static double[] X, Y;
    private static Date[] time;
    private double min, max;

    Grafic(Date[] _time, double[] _X, double[] _Y){
        setVisible(true);
        setSize(1000,1000);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        time = _time.clone();
        X = _X.clone();
        Y = _Y.clone();
    }

    @Override
    public void paint(Graphics g) {

        min = X[0];
        max = X[0];
        for (int i = 0; i < X.length; i++) {
            if(min > X[i]){
                min = X[i];
            }
            if(min > Y[i]){
                min = Y[i];
            }
            if(max < X[i]){
                max = X[i];
            }
            if(max < Y[i]){
                max = Y[i];
            }
        }
        drawSystem(g);
        g.setColor(Color.RED);
        int oldXpos = 100;
        int oldYpos = (int)(900 - (((X[0]-min)/(max-min)) * 800));
        for (int i = 0; i < X.length; i++) {
            int xpos = (int)(100 + (800/X.length * i));
            int ypos = (int)(900 - (((X[i]-min)/(max-min)) * 800));
            g.fillRect(xpos, ypos, 3, 3);
            g.drawLine(oldXpos, oldYpos, xpos, ypos);
            oldXpos = xpos;
            oldYpos = ypos;
        }
        g.setColor(Color.BLUE);
        oldXpos = 100;
        oldYpos = (int)(900 - (((Y[0]-min)/(max-min)) * 800));
        for (int i = 0; i < Y.length; i++) {
            int xpos = (int)(100 + (800/Y.length * i));
            int ypos = (int)(900 - (((Y[i]-min)/(max-min)) * 800));
            g.fillRect(xpos, ypos, 3, 3);
            g.drawLine(oldXpos, oldYpos, xpos, ypos);
            oldXpos = xpos;
            oldYpos = ypos;
        }
        g.fillRect(800, 95, 10, 10);
        g.setColor(Color.RED);
        g.fillRect(800, 135, 10, 10);
        g.setColor(Color.BLACK);
        g.drawString(" - тестовая выборка", 820, 100);
        g.drawString(" - предиктивная модель", 820, 140);
    }

    private void drawSystem(Graphics g){
        g.drawLine(100, 100, 100, 900);
        g.drawLine(100, 900, 900, 900);
        g.drawString(String.valueOf((int) min) , 60, 900);
        //g.drawString(String.valueOf((int) max) , 60, 100);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMM/yy");
        for (int i = 1; i < 6; i++) {
            g.drawString(String.valueOf((int)  (min +(max-min)/5 * i)) , 60, 900 - (800/5) * i);
            g.drawString(simpleDateFormat.format(time[(time.length-1)/5*i]) , 100 + ((i-1) * 800/5), 920);
        }
    }
}