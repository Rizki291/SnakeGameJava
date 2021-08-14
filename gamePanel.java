/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

/**
 *
 * @author Rizki Kurniawan
 */
public class gamePanel extends JPanel implements ActionListener{
    //Atur Ukuran Window
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    //besar object
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    //Kecepatan Game
    static final int Delay = 75;
    final int x[] = new int [GAME_UNITS];
    final int y[] = new int [GAME_UNITS];
    //panjangnya ular sesuai grid
    int BodyPart = 6;
    //variable makanan
    int FoodEaten;
    int FoodX;
    int FoodY;
    //arah ular
    char direction = 'R';
    //aturan kecepatan ular berlari
    boolean running = false;
    Timer timer;
    Random random;
    
    gamePanel(){
        //initialisasi Tempat Bermain
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        //Atur Warna BG
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new myKeyAdapter());
        startGame();
    }
    public void startGame(){
        newFood();
        running = true;
        timer= new Timer(Delay, this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        //mendeklarasi gambar
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(running){
            //menggambar garis tile dan makanan
            for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++){
                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
            }
            g.setColor(Color.red);
            g.fillOval(FoodX, FoodY, UNIT_SIZE, UNIT_SIZE);
            //gambar ular
            for(int i =0;i<BodyPart;i++){
                if(i==0){
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }else{
                    g.setColor(new Color(45,180,0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            //buat tulisan game over
            g.setColor(Color.red);
            g.setFont(new Font("ARIAL",Font.BOLD,40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("SCORE: "+FoodEaten, (SCREEN_WIDTH-metrics.stringWidth("SCORE: "+FoodEaten))/2,
                    g.getFont().getSize());
        }else{
            gameOver(g);
        }
    }
    public void newFood(){
        //implementasi makanan
        FoodX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        FoodY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    public void move(){
        //menggerakan karakter
        for(int i = BodyPart;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        switch(direction){
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }
    public void checkFood(){
        if((x[0]==FoodX) && (y[0]==FoodY)){
            BodyPart++;
            FoodEaten++;
            newFood();
        }
    }
    public void checkCollision(){
        //mengecek jika menabrak badan
        for(int i = BodyPart;i>0;i--){
            if((x[0] == x[i]) && (y[0] == y[i])){
                running = false;
            }
        }
        //check jika menabrak tembok
        if(x[0]<0){
            running = false;
        }
        if(x[0]>SCREEN_WIDTH){
            running = false;
        }
        if(y[0]<0){
            running = false;
        }
        if(y[0]>SCREEN_HEIGHT){
            running = false;
        }
        
        if(!running){
            timer.stop();
        }
    }
    public void gameOver(Graphics g){
        g.setColor(Color.red);
        g.setFont(new Font("ARIAL",Font.BOLD,40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("SCORE: "+FoodEaten, (SCREEN_WIDTH-metrics1.stringWidth("SCORE: "+FoodEaten))/2,
                g.getFont().getSize());
        //buat tulisan game over
        g.setColor(Color.red);
        g.setFont(new Font("ARIAL",Font.BOLD,75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("GAME OVER", (SCREEN_WIDTH-metrics.stringWidth("GAME OVER"))/2,
                SCREEN_HEIGHT/2);
    }
    public class myKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){
                        direction = 'L'; 
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction = 'R'; 
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction = 'U'; 
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction = 'D'; 
                    }
                    break;
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkFood();
            checkCollision();
        }
        repaint();
    }
    
}
