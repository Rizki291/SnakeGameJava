/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import javax.swing.JFrame;

/**
 *
 * @author Rizki Kurniawan
 */
public class gameFrame extends JFrame{
   gameFrame(){
       this.add(new gamePanel());
       this.setTitle("Snake");
       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       this.setResizable(false);
       this.pack();
       this.setVisible(true);
       this.setLocationRelativeTo(null);
   } 
}
