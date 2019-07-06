package com.archery.master;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author koushik
 */
public class GameBoard extends JPanel implements ActionListener{
    
                                                ///Initialization.
    JPanel gameover;
    private final String fileBackground = "images/background/bg-11.jpg";
    private Timer mainTimer;
    static int getx=0,gety=0;
    double tim=0,speed=0;
    static boolean clicked=false,start=false;
    Font defaultFont;
    int life = 3;
    private boolean isStopped;
    private AudioClip man_pain,Arrow_swoosh,pumpkin_clip;
    double timclick,timrelease;
    
    int score = 0;
    private Image bowarrowimg,bowimg,arrowShoot,avatar1,aim1,aim1sad,arrow,blood,avatar1sad;
    double angle;
    static int arrow_x=50,arrow_y=443;
    private static boolean shoot;
    String bow0="images/BowArrows/bow 0.png",
            bow1="images/BowArrows/bow 1.png",
            BackGroundFile="images/background/bg-11.jpg";
    String arrowshoot = "images/BowArrows/Black_Circle.png";
    String avatar_1="images/avatars/man-1.png";
    String aim_1 = "images/avatars/Pumpkin-1.png";
    String aim_sad_1 = "images/avatars/Pumpkin-1(sad).png";
    String Arrow = "images/BowArrows/arrow_1.png";
    String Blood="images/avatars/blood.png";
    String avatar1_sad="images/avatars/man-1(sad).png";
    boolean shooted = false;
    boolean dead = false;
    JButton backBtn;
    
                                                ///Constractor
    
    GameBoard()throws IOException{
        setBounds(0, 0, Game.WIDTH, Game.HEIGHT);
        
        defaultFont = new Font(Font.SERIF, Font.BOLD, 24);
        
        URL clipUrl = getClass().getResource("sounds/man_pain.wav");

            man_pain = Applet.newAudioClip(clipUrl);
        
        URL clipUrl2 = getClass().getResource("sounds/Arrow Swoosh.wav");

            Arrow_swoosh = Applet.newAudioClip(clipUrl2);
            
        URL clipUrl3 = getClass().getResource("sounds/pumpkin.wav");

            pumpkin_clip = Applet.newAudioClip(clipUrl3);
        
        isStopped=false;
        setLayout(null);
        setBowArrow();
        setShoot(false);
        setImage();
        setButton();
        
    }

                                                ///Mouse Listening.
    
    public static void setShoot(boolean f) {
	shoot = f;
    }
    
    private void setBowArrow(){
       
        addMouseMotionListener(new MouseMotionListener() {		
            @Override
            public void mouseMoved(MouseEvent e) {                 
                    rotate(e.getX(),e.getY(),false);
            }		
            @Override
            public void mouseDragged(MouseEvent arg0) {
                rotate(arg0.getX(),arg0.getY(),false);
            }
        });
        
        addMouseListener(new MouseAdapter() {
            
            boolean flag = false;
            @Override
            public void mouseClicked(MouseEvent e) {
                //setShoot(true);
                                                     
            }
            @Override
            public void mousePressed(MouseEvent e) {
                
                setShoot(false);
                timclick = System.nanoTime();
                flag = true;
                //clicked = false;
                start=true;
                
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                setShoot(true);
                start = false;
                if(flag)
                {
                    timrelease = System.nanoTime();
                    flag = false;
                }
                tim = (timrelease-timclick)/Math.pow(10, 6);
                if(tim>1000)
                {
                    //tim=1000;
                    tim=1000-(tim%1000);
                }
                speed = 80+(tim/20);
                
                if(!clicked)
                {
                    clicked = true;
                    move_arrow(e.getX(), e.getY(), true);
                    //clicked=true;
                }
                else
                {
                    
                }
            }		
        });// End of addMouseListener.
    }
    
                                            ///Main GameLoop
    
    public void gameLoop(){
        mainTimer = new Timer(Game.REFRESH_TIME, new ActionListener() {			
            @Override
            public void actionPerformed(ActionEvent arg0) {
                repaint();
                if(start)
                {
                    repaint();
                }
                if(life==0)
                {
                    gameOver();
                    return;
                }
                if(isStopped)
                {
                    mainTimer.stop();
                    Game.setState(Game.GameState.OVER);
                    return;
                }
            }
        });
        mainTimer.start();
    }
                                             ///Button System.
    public void setButton()
    {
        backBtn = createButton("images/buttons/back10.png",
                "images/buttons/back11.png",
                "images/buttons/back12.png");
        backBtn.setBounds(1030,20,225,90);
        add(backBtn);
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
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == backBtn) 
            {
                gameStop();
                Game.setState(Game.GameState.OVER);
            }    
    }
    
    public void gameStop() {
        isStopped = true;
        mainTimer.stop();
    }
                                                 ///Arrow Movement.
    
    public void rotate(int x, int y, boolean shoot) {
        setShoot(shoot);
        angle = Math.atan2(y-445,x-50 );
    }
    
    public void move_arrow(int p,int q,boolean shoot){
        
//        if(tim>1000)
//        {
//            //tim=1000;
//            tim=1000-(tim%1000);
//        }
//        double speed = 80+(tim/20);
        System.out.println("Time "+tim+"\tSpeed "+speed);
        double theta = Math.atan2(443-q,p-50);
        double res_x=50;
        double res_y=q;
        Arrow_swoosh.play();
        while(arrow_x<1280 && arrow_y < 700 && arrow_y>0 && arrow_x>0 )
        {
            res_x=res_x+0.003;
            double xtantheta = (res_x-50)*(Math.tan(theta));
            double extra = (9.8*(res_x-50)*(res_x-50))/(2*(speed*Math.cos(theta))*(speed*Math.cos(theta)));
            res_y = xtantheta-extra;
            res_y=443-res_y;
            
            arrow_x=(int)res_x;
            arrow_y=(int)res_y;
            
            avatar_dead();
            
            
            if(dead)
            {
                Arrow_swoosh.stop();
                man_pain.play();
                life--;
                dead=false;
                int i=0;
                while(i<60000)
                {
                    i++;
                    Graphics g = getGraphics();
                    draw3(g);
                }
                break;
            }
            scoreCheck();
            if(!shooted)
            {
                Graphics g = getGraphics();
                draw(g);
                
            }
            else
            {
                int i=0;
                Arrow_swoosh.stop();
                pumpkin_clip.play();
                while(i<60000)
                {
                    
                    i++;
                    Graphics g = getGraphics();
                    draw2(g);
                }
                score=score+1;
                shooted = false;
                break;
            }
            
        }
        
        clicked = false;
        repaint();
        arrow_x=50;
        arrow_y=443;
    }
                                        ///Draw & Paint System.
    
    private void setImage()
    {
        bowarrowimg = Toolkit.getDefaultToolkit().getImage(getClass().getResource(bow0));
        bowimg = Toolkit.getDefaultToolkit().getImage(getClass().getResource(bow1));
        arrowShoot = Toolkit.getDefaultToolkit().getImage(getClass().getResource(arrowshoot));
        avatar1 = Toolkit.getDefaultToolkit().getImage(getClass().getResource(avatar_1));
        aim1 = Toolkit.getDefaultToolkit().getImage(getClass().getResource(aim_1));
        aim1sad = Toolkit.getDefaultToolkit().getImage(getClass().getResource(aim_sad_1));
        arrow = Toolkit.getDefaultToolkit().getImage(getClass().getResource(Arrow));
        blood = Toolkit.getDefaultToolkit().getImage(getClass().getResource(Blood));
        avatar1sad = Toolkit.getDefaultToolkit().getImage(getClass().getResource(avatar1_sad));
        angle = 0;
    }
    
    @Override
    public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2d = (Graphics2D) g;
            double temp = System.nanoTime();
            Font f= new Font(Font.SERIF, Font.BOLD, 30);
            g2d.setFont(f);
            if(start)
            {
                double nowtime = (temp-timclick)/Math.pow(10, 6);
                if(nowtime>1000)
                    g2d.drawString("Speed: "+(1000-((int)nowtime%1000)),20,130);
                else
                    g2d.drawString("Speed: "+(int)nowtime,20,130);
            }
            g2d.drawString("Your Score: "+ score, 20, 50);
            g2d.drawString("Life: "+life,20,90);
            
            
            g2d.drawImage(avatar1,1100,400, null);
            g2d.drawImage(aim1, 1095,350, null);
            
            if(angle<=1 && angle>-1.3)
                g2d.rotate(angle,50, 375+70);
            
            if(!shoot)
            {
                    g2d.drawImage(bowarrowimg, 51, 376, null);
            }
            else{
                    g2d.drawImage(bowimg, 51, 376, null);
            }
    }
    
    public void draw(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(arrowShoot,arrow_x,arrow_y, null);
       
    }
    
    public void draw2(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(aim1sad, 1095-18,320, null);
        g2.drawImage(arrow,arrow_x-100,arrow_y-70,null);
    }
    
    public void draw3(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(arrow, arrow_x-100,arrow_y-70, null);
        g2.drawImage(blood,arrow_x-60,arrow_y-20,null);
        g2.drawImage(avatar1sad,1100,400, null);
    }
    
    public void speed_meter()
    {
        if(start)
        {
            Graphics g = null;
            speed_draw(g);
        }
    }
    
    public void speed_draw(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        Font f= new Font(Font.SERIF, Font.BOLD, 30);
        g2d.setFont(f);
        g2d.drawString("Speed: "+speed,20,130);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Draw background of the panel.
            g.drawImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(BackGroundFile)), 0, 0, null);
    }
    
    
                                            ///Collition Detection.
    public void scoreCheck()
    {
        if((arrow_x+2)>=1100 && (arrow_x+2)<=1160 && (arrow_y+1)>=360 && (arrow_y+1)<=405)
            shooted = true;
    }
    
    public void avatar_dead()
    {
        if(arrow_y>400 && arrow_y<660 &&arrow_x>=1135 &&arrow_x<1160)
        {
            dead = true;
        }
    }
    
                                            ///GameOver System.
    
    public void gameOver() {
        mainTimer.stop();
        int selectedOption = JOptionPane.showConfirmDialog(this,
                        ("Your Score: " + score + "\nDo you want to play a new Game?"),
                        "GAME OVER", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        Game.setHighScore(score);
        switch (selectedOption) {
        case JOptionPane.YES_OPTION:
                Game.setState(Game.GameState.CONTINUE);
                break;
        case JOptionPane.NO_OPTION:
                Game.setState(Game.GameState.OVER);
                break;
        }
    }
    
    
}