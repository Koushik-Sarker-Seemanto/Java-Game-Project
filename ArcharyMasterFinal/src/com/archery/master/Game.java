package com.archery.master;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


/**
 *
 * @author koushik
 */
public class Game extends JFrame implements ActionListener{
                    //Initilization
    protected final static int WIDTH = 1280,HEIGHT=720;
    JPanel MenuPanel,HighscorePanel,CreditPanel,LevelPanel;
    JButton playBtn,creditBtn,highscoreBtn,quitBtn,backBtn0,backBtn1,backBtn2,lvl1,lvl2,lvl3;
    public enum GameState{NEW,NEW2, CONTINUE,CONTINUE2, OVER,OVER2, HIGHSCORE, CREDITS, WAIT, QUIT,LEVEL};
    public static GameState state;
    protected static int highScore = 0;
    private Font defaultFont;
    String menubg="images/background/Menu_Background720P.png";
    private JTextField txtHighScore;
    protected final static int REFRESH_TIME = 10;
    private final String fileScore = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getParent() +
            File.separator + "__score_data__.txt";
    private final String fileCredits = "data/credits.txt";
    
    
    Game() throws HeadlessException, IOException
    {
        this("Archery Master");
    }
    
    Game(String title) throws HeadlessException, IOException
    {
        super(title);
        
        
        
        defaultFont = new Font(Font.SANS_SERIF,Font.BOLD,24);
        setMenuPanel();
        setHighScorePanel();
        setCreditsPanel();
        setLevelPanel();
        
        setLayout(null);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        pack();
    }
    public void loadHighScore() throws IOException 
    {
        File file = new File(URLDecoder.decode(fileScore, "UTF-8"));

        if(!file.canRead()) 
        {
            file.createNewFile();
            setHighScore(0);
        }
        else
        {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            setHighScore( (line != null) ? Integer.parseInt(line) : 0 );
            reader.close();
        }
    }
    
    public static void setHighScore(int score)
    {
        if(score < 0)
            throw new IllegalArgumentException("High Score CANNOT BE a negative number!");
        if(score > highScore)
            highScore = score;
        
    }
	
    public static int getHighScore() 
    {
        return highScore;
    }

    public void saveHighScore() throws IOException, SecurityException 
    {
        File file = new File(URLDecoder.decode(fileScore, "UTF-8"));

        if(!file.canWrite())
                file.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));

        writer.write(Integer.toString(getHighScore()));
        writer.close();
    }
    JLabel bgimage;
    public void setMenuPanel()
    {
        
        bgimage = new JLabel(new ImageIcon(getClass().getResource("images/background/Menu_Background720P.png")));
        bgimage.setBounds(0,0,WIDTH,HEIGHT);
        JLabel logo = new JLabel(new ImageIcon(getClass().getResource("images/background/gamelogo2.png")));
        logo.setBounds(330,2,617,228);
        
        playBtn = createButton("images/buttons/play0.png",
                "images/buttons/play1.png",
                "images/buttons/play2.png");
        highscoreBtn = createButton("images/buttons/highscore0.png",
                "images/buttons/highscore1.png",
                "images/buttons/highscore2.png");
        creditBtn = createButton("images/buttons/credits0.png",
                "images/buttons/credits1.png",
                "images/buttons/credits2.png");
        quitBtn = createButton("images/buttons/quit0.png",
                "images/buttons/quit1.png",
                "images/buttons/quit2.png");
                
        MenuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        MenuPanel.setBounds(0,0,WIDTH,HEIGHT);
        
        playBtn.setBounds(535, 113+100, 230, 90);
        highscoreBtn.setBounds(380,253+80,570,90);
        creditBtn.setBounds(470,393+70,360,90);
        quitBtn.setBounds(530,533+60,240,97);
        
        
        MenuPanel.setLayout(null);
        MenuPanel.add(playBtn);
        MenuPanel.add(highscoreBtn);
        MenuPanel.add(creditBtn);
        MenuPanel.add(quitBtn);
        MenuPanel.add(logo);
        MenuPanel.add(bgimage);
    }
    public void setHighScorePanel() throws IOException
    {
        JLabel highscorebg = new JLabel(new ImageIcon(getClass().getResource("images/background/highscorebg.png")));
        highscorebg.setBounds(0,0,WIDTH,HEIGHT);
        
        backBtn0 = createButton("images/buttons/back10.png",
                "images/buttons/back11.png",
                "images/buttons/back12.png");
        backBtn0.setBounds(1030,30,225,90);
      
        HighscorePanel = new JPanel();
        HighscorePanel.setLayout(null);
        HighscorePanel.setBounds(0,0,WIDTH,HEIGHT);
        HighscorePanel.add(backBtn0);
        
        JLabel lblMessage = new JLabel("High Score", JLabel.CENTER);
        lblMessage.setForeground(Color.decode("#F27A03"));
        defaultFont = new Font("Comic Sans MS",Font.BOLD,72);
        lblMessage.setFont(defaultFont);
        lblMessage.setHorizontalTextPosition(JLabel.CENTER);
        lblMessage.setBounds(460,260,400,100);
        loadHighScore();
        txtHighScore = new JTextField(Integer.toString(highScore),6);
        defaultFont = new Font("Comic Sans MS",Font.BOLD,60);
        txtHighScore.setFont(defaultFont);
        
        txtHighScore.setForeground(Color.decode("#080F1F"));
        txtHighScore.setHorizontalAlignment(JTextField.CENTER);
        txtHighScore.setBackground(Color.decode("#F5C208"));
        txtHighScore.setEditable(false);
        txtHighScore.setBounds(560,400,200,80);
        //System.out.println("Score:"+highScore);
        HighscorePanel.add(lblMessage);
        HighscorePanel.add(txtHighScore);
        HighscorePanel.add(highscorebg);
        
    }
    
    public void setCreditsPanel() throws IOException
    {
        backBtn1 = createButton("images/buttons/back10.png",
                "images/buttons/back11.png",
                "images/buttons/back12.png");
        backBtn1.setBounds(1030,30,225,90);
 
        String text = "CREDITS";

        if(getClass().getResourceAsStream(fileCredits) == null)
                throw new FileNotFoundException("credits.txt could NOT be found!");
        else 
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(fileCredits)));
            String line = " ";
            while(line != null) 
            {
                text = text + "\n" + line;
                line = reader.readLine();
            }
            reader.close();
        }
        JTextPane txtPane = new JTextPane();
        txtPane.setText(text);
        txtPane.setFont(new Font("Comic Sans MS",Font.PLAIN,18));
        txtPane.setEditable(false);
        txtPane.setForeground(Color.darkGray);
        
        StyledDocument doc = txtPane.getStyledDocument();
        SimpleAttributeSet body = new SimpleAttributeSet();
        
        SimpleAttributeSet header = new SimpleAttributeSet();
        StyleConstants.setAlignment(body, StyleConstants.ALIGN_CENTER);
        StyleConstants.setFontSize(body, 16);
        StyleConstants.setFontSize(header, 24);
        StyleConstants.setBold(header, true);
        StyleConstants.setAlignment(header, StyleConstants.ALIGN_CENTER);

        doc.setParagraphAttributes(7, doc.getLength(), body, false);
        doc.setParagraphAttributes(0, 7, header, false);

        JScrollPane scrollPane = new JScrollPane(txtPane);
        scrollPane.setPreferredSize(new Dimension(850, HEIGHT - 200));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(300,200,680,320);

        
        CreditPanel = new JPanel();
        CreditPanel.setBackground(Color.LIGHT_GRAY);
        CreditPanel.setBounds(0,0,WIDTH,HEIGHT);
        
        CreditPanel.setLayout(null);
        CreditPanel.add(backBtn1);
        CreditPanel.add(scrollPane);
    }
    
    public void setLevelPanel()
    {
        bgimage = new JLabel(new ImageIcon(getClass().getResource("images/background/Menu_Background720P.png")));
        bgimage.setBounds(0,0,WIDTH,HEIGHT);
        lvl1 = createButton("images/buttons/level-10.png",
                "images/buttons/level-11.png",
                "images/buttons/level-12.png");
        lvl2 = createButton("images/buttons/level-20.png",
                "images/buttons/level-21.png",
                "images/buttons/level-22.png");
        lvl3 = createButton("images/buttons/level-30.png",
                "images/buttons/level-31.png",
                "images/buttons/level-32.png");
        backBtn2 = createButton("images/buttons/back10.png",
                "images/buttons/back11.png",
                "images/buttons/back12.png");
        
        backBtn2.setBounds(1030,30,225,90);
        lvl1.setBounds(515, 113+100, 310, 85);
        lvl2.setBounds(500,253+80,330,85);
        lvl3.setBounds(500,393+70,330,85);
        
        LevelPanel = new JPanel();
        LevelPanel.setLayout(null);
        LevelPanel.setBounds(0,0,WIDTH,HEIGHT);
        LevelPanel.add(lvl1);
        LevelPanel.add(lvl2);
        LevelPanel.add(lvl3);
        LevelPanel.add(backBtn2);
        LevelPanel.add(bgimage);
        
    }
    
    private JButton createButton(String str1,String str2,String str3) 
    {
        ImageIcon icon0 = new ImageIcon(getClass().getResource(str1));
        ImageIcon icon1 = new ImageIcon(getClass().getResource(str2));
        ImageIcon icon2 = new ImageIcon(getClass().getResource(str3));

        JButton button = new JButton(icon0);
        button.setPreferredSize(new Dimension(icon0.getIconWidth(), icon0.getIconHeight()));
        button.setBorderPainted(false);
        
        button.setFocusable(true);
        button.setFocusPainted(false);
        button.setRolloverEnabled(true);
        
        button.setRolloverIcon(icon1);
        button.setPressedIcon(icon2);
        button.setContentAreaFilled(false);
        button.addActionListener(this);
        return button;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource().equals(playBtn))
            setState(GameState.LEVEL);
        
        else if(e.getSource().equals(lvl1))
            setState(GameState.NEW);
        
        else if(e.getSource().equals(lvl2))
            setState(GameState.NEW2);

        else if(e.getSource().equals(highscoreBtn))
        {
            setState(GameState.HIGHSCORE);
        }

        else if(e.getSource().equals(backBtn0) || e.getSource().equals(backBtn1)||e.getSource().equals(backBtn2)) 
        {
            remove(((JButton) e.getSource()).getParent());
            repaint();
            add(MenuPanel);
            pack();
            setState(GameState.WAIT);
        }

        else if(e.getSource().equals(quitBtn))
            setState(GameState.QUIT);

        else if(e.getSource().equals(creditBtn))
            setState(GameState.CREDITS);
    }
    
    public static void setState(GameState state) 
    {
	Game.state = state;
    }
    
    private void exceptionIO(String e) 
    {
        JOptionPane.showMessageDialog(null,
                            "It seems that there is a problem on your file system!\nError: " + e,
                            "Opps!! Something went wrong!", JOptionPane.ERROR_MESSAGE);
    }
    private void exceptionSecurity(String e) 
    {
        JOptionPane.showMessageDialog(null,
                            "It seems that there is a problem about file's permission!\nError: " + e,
                            "Opps!! Something went wrong!", JOptionPane.ERROR_MESSAGE);
    }
    private void exceptionOther(String e) 
    {
        JOptionPane.showMessageDialog(null,
                            "We are sorry about that!\nError: " + e,
                            "Opps!! Something went wrong!", JOptionPane.ERROR_MESSAGE);
    }
    
    public void StartGame() throws IOException
    {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                
                setState(GameState.WAIT);
                add(MenuPanel);
                GameBoard board = null;
                GameBoard2 board2 = null;
                
                while(!state.equals(GameState.QUIT)){
                    
                    switch(state)
                    {
                        case WAIT:
                            
                            try {
                                    Thread.sleep(200);
                            } catch (InterruptedException e) {
                                    e.printStackTrace();
                            }
                            break;
                            
                        case LEVEL:
                            remove(MenuPanel);
                            repaint();
                            add(LevelPanel);
                            repaint();
                            pack();
                            setState(GameState.WAIT);
                            break;

                        case HIGHSCORE:
                            txtHighScore.setText(Integer.toString(getHighScore()));
                            {
                                try {
                                    loadHighScore();
                                } catch (IOException ex) {
                                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            remove(MenuPanel);
                            repaint();
                            add(HighscorePanel);
                            repaint();
                            pack();
                            setState(GameState.WAIT);
                            break;
                            
                        case NEW:
                            remove(MenuPanel);
                            repaint();
                            
                            try {
                                board = new GameBoard();
                            } catch (IOException ex) {
                                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                            add(board);
                            pack();
                            board.gameLoop();
                            setState(GameState.WAIT);
                            break;
                        
                        case NEW2:
                            remove(MenuPanel);
                            repaint();
                            {
                                try {
                                    board2 = new GameBoard2();
                                } catch (IOException ex) {
                                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            add(board2);
                            pack();
                            board2.gameLoop();
                            setState(GameState.WAIT);
                            break;
                            
                        case CREDITS:
                            remove(MenuPanel);
                            repaint();
                            add(CreditPanel);
                            repaint();
                            pack();
                            setState(GameState.WAIT);
                            break;
                            
                        case QUIT:
                            break;
                        
                        
                        case OVER:
                            try {
                                    saveHighScore();
                            } catch (IOException e) {
                                    exceptionIO(e.getMessage());
                            } catch (SecurityException e) {
                                    exceptionSecurity(e.getMessage());
                            }
                            remove(board);
                            repaint();
                            add(MenuPanel);
                            repaint();
                            pack();
                            setState(GameState.WAIT);
                            break;
                            
                        case OVER2:
                            try {
                                    saveHighScore();
                            } catch (IOException e) {
                                    exceptionIO(e.getMessage());
                            } catch (SecurityException e) {
                                    exceptionSecurity(e.getMessage());
                            }
                            remove(board2);
                            repaint();
                            add(MenuPanel);
                            repaint();
                            pack();
                            setState(GameState.WAIT);
                            break;
                            
                        
                            
                    // After game over if user wants to play a new game.
                        case CONTINUE:
                            try {
                                    saveHighScore();
                            } catch (IOException e) {
                                    exceptionIO(e.getMessage());
                            } catch (SecurityException e) {
                                    exceptionSecurity(e.getMessage());
                            }
                            remove(board);
                            repaint();
                            {
                                try {
                                    board = new GameBoard();
                                } catch (IOException ex) {
                                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            add(board);
                            repaint();
                            pack();
                            board.gameLoop();
                            setState(GameState.WAIT);
                            break;
                            
                        case CONTINUE2:
                            try {
                                    saveHighScore();
                            } catch (IOException e) {
                                    exceptionIO(e.getMessage());
                            } catch (SecurityException e) {
                                    exceptionSecurity(e.getMessage());
                            }
                            remove(board2);
                            repaint();
                            {
                                try {
                                    board2 = new GameBoard2();
                                } catch (IOException ex) {
                                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            add(board2);
                            repaint();
                            pack();
                            board.gameLoop();
                            setState(GameState.WAIT);
                            break;
                    }
                }
                dispose();
                System.exit(0);
            }
        });
        t.start();
    }
}
