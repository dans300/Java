package Reader;

import javax.swing.*;
import java.awt.*;

public class Lepanel extends JPanel {

    private int times;

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public void paint(Graphics g)
    {

        System.out.println(times+" called");
        //soga
        g.drawLine(350, 0, 250, 0);
        g.drawLine(250, 0, 250, 400);

        switch (times)
        {

            case 0:
                //times-=1;
                break;
            case 1:
                //head
                //g.drawRoundRect(325,0,50,50,99,99);
                g.setColor(Color.cyan);
                g.fillOval(325,0,50,50);
                break;
            case 2:
                //tronco
                g.drawLine(350, 100, 350, 50);
                break;
            case 3://droite
                g.drawLine(350, 50, 380, 100);
                break;
            case 4://gauche
                g.drawLine(350, 50, 320, 100);
                break;
            case 5://right feet
                g.drawLine(350, 100, 400, 150);
                break;
            case 6:
                //patas
                g.drawLine(350, 100, 300, 150);
                g.setColor(Color.RED);
                g.fillOval(325,0,50,50);
                break;
            case 7:
                super.removeAll();super.paintComponent(g);
                g.drawLine(350, 0, 250, 0);
                g.drawLine(250, 0, 250, 400);
                setTimes(0);
                break;



        }
        times+=1;
    }

    public Lepanel()
    {
        times=-1;
    }


}
