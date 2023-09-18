package Reader;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class Pendu extends JFrame implements EventListener {

    JPanel outPutsContainer;
    List<String> words=null;
    List<Character> theWord;
    String alpha="AZERTYUIOPQSDFGHJKLMWXCVBN";
    List<JTextField> outPutsField;
    Lepanel dessin;
    int succes=0;
    int failed=0;
    JPanel clavier;
    JLabel combienLettres;

    public Pendu(){
        super("pendu");
        words=getWords("src/words");
        WindowListener wl=new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };

        getContentPane().setLayout(new GridLayout(0,1));


        //buttons
        JButton buttonN=new JButton("Nouveau mot");
        JButton buttonQ=new JButton("Quitter");
        buttonN.setEnabled(false);
        buttonQ.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        JPanel buttons=new JPanel();
        buttons.add(buttonN,BorderLayout.EAST);
        buttons.add(buttonQ,BorderLayout.WEST);
        //buttons.setLayout(new BorderLayout());

        play();

        //DESSIN
        dessin=new Lepanel();
        dessin.setBounds(new Rectangle(10,10,600,500));

        //CLAVIER
        clavier=new JPanel();
        clavier.setLayout(new GridLayout(0,8));
        for (int i = 0; i <alpha.length() ; i++)
        {
            char key=alpha.charAt(i);
            Button b=new Button(key+"");
            b.setFont(new Font(Font.SERIF, Font.PLAIN, 17));
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    boolean itContains=false;
                    int len=theWord.size();
                    for (int j = 0; j <len ; j++)
                    {
                        if(theWord.get(j).charValue()==key)
                        {
                            b.setEnabled(false);
                            b.setBackground(Color.BLUE);               //System.out.println("IT HAS A "+key);
                            show(key);
                            if(succes==theWord.size())
                            {
                                enableClavier(false);
                                buttonN.setEnabled(true);
                            }
                            itContains=true;

                            break;
                        }
                    }
                    //PAINT
                    if(itContains==false)
                    {
                        b.setBackground(Color.RED);
                        failed+=1;
                        System.out.println(failed + " failed ");
                        dessin.update(dessin.getGraphics());
                        if(failed==6)
                        {
                            enableClavier(false);
                            buttonN.setEnabled(true);
                        }



                        //dessin.paint(dessin.getGraphics());
                        //dessin.updateUI();
                    }
                }
            });
            clavier.add(b);
        }


        //TEXT
        JPanel lettres=new JPanel();
        combienLettres=new JLabel();
        combienLettres.setText(String.valueOf(theWord.size()));
        lettres.add(combienLettres);

        //MOT A DEVINER
        outPutsContainer=new JPanel();
        outPutsField=new ArrayList<>();
        for (int i = 0; i <theWord.size(); i++) {
            outPutsField.add(new JTextField(" ___ "));
            outPutsField.get(i).setEnabled(false);
            outPutsContainer.add(outPutsField.get(i));
        }




        //ordre
        getContentPane().add(lettres);
        getContentPane().add(dessin);
        getContentPane().add(outPutsContainer);
        getContentPane().add(clavier);
        getContentPane().add(buttons);

        buttonN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //re-enable the buttons
                dessin.setTimes(7);
                dessin.update(dessin.getGraphics());
                succes=0;
                failed=0;
                play();
                combienLettres.setText(String.valueOf(theWord.size()));
                actualiser();
                buttonN.setEnabled(false);
                enableClavier(true);
                Component[] components=clavier.getComponents();
                for (Component c:components) {
                    c.setBackground(Color.WHITE);
                }

            }
        });


        addWindowListener(wl);
        setSize(600,700);
        setVisible(true);
        setResizable(false);





    }//fin de constructeur

    public void enableClavier( boolean en){
        for (int i = 0; i <alpha.length(); i++) {
            clavier.getComponent(i).setEnabled(en);
            //clavier.setBackground(Color.WHITE);
        }
    }

    public void show(char a)
    {
        //show
        String d=" "+a+" ";
        for (int i = 0; i <theWord.size() ; i++)
        {
            if(theWord.get(i)==a)
            {
                outPutsField.get(i).setText(d);
                succes+=1;
                System.out.println(succes+"SUCCES");
            }
        }


    }

    public void actualiser()
    {
        //ponga esto en play porque es lo mismo
        int old=outPutsField.size();
        outPutsField.clear();//=new ArrayList<>();
        outPutsContainer.removeAll();

        for (int i = 0; i <theWord.size(); i++) {
            outPutsField.add(new JTextField(" ___ "));
            outPutsContainer.add(outPutsField.get(i));

        }

            outPutsContainer.repaint();
            outPutsContainer.revalidate();



    }

    public List<String> getWords(String s)
    {
        List<String> words=new ArrayList<String>();
        try
        {
            String line;
            BufferedReader file=new BufferedReader(new FileReader(s));
            while((line= file.readLine())!=null)
            {
                StringTokenizer st = new StringTokenizer(line);// falta ,
                while (st.hasMoreTokens()) {
                    //System.out.println(st.nextToken());
                    words.add(st.nextToken());
                }
            }
            file.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }


        return words;
    }

    public  String chooseWord()
    {
        Random random = new Random();
        return words.get(random.nextInt(words.size()));
    }

    public void play()
    {

        theWord=new ArrayList<Character>();
        String word=chooseWord();
        for (int i = 0; i < word.length(); i++)
        {
            theWord.add(word.charAt(i));
        }


    }

    public static void main(String[] args) {
        Pendu pendu=new Pendu();

    }

}
