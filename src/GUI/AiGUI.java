package GUI;

import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.skhu.Game;

public class AiGUI extends JFrame implements GameGui {

   private ImageIcon icon;
   private JLabel jlabel;
   private JPanel panel ;

   @Override
   public JPanel changeView(Game game) {
      panel = new JPanel(null);



      panel.setBounds(900,150,200,250);
      panel.add(changeImage(game.getAi().getCard(),game.getRound()));
      panel.add(jlabel);
      return panel;
   }

   @Override
   public JLabel changeImage(List list,int round) {
      // TODO Auto-generated method stub

      jlabel =new JLabel("");
      if(list.get(round-1).equals(1)) icon=new ImageIcon(getClass().getResource("/image/2_1.png"));
      else if(list.get(round-1).equals(2)) icon=new ImageIcon(getClass().getResource("/image/2_2.png"));
      else if(list.get(round-1).equals(3)) icon=new ImageIcon(getClass().getResource("/image/2_3.png"));
      else if(list.get(round-1).equals(4)) icon=new ImageIcon(getClass().getResource("/image/2_4.png"));
      else if(list.get(round-1).equals(5)) icon=new ImageIcon(getClass().getResource("/image/2_5.png"));
      else if(list.get(round-1).equals(6)) icon=new ImageIcon(getClass().getResource("/image/2_6.png"));
      else if(list.get(round-1).equals(7)) icon=new ImageIcon(getClass().getResource("/image/2_7.png"));
      else if(list.get(round-1).equals(8)) icon=new ImageIcon(getClass().getResource("/image/2_8.png"));
      else if(list.get(round-1).equals(9)) icon=new ImageIcon(getClass().getResource("/image/2_9.png"));
      else if(list.get(round-1).equals(10)) icon=new ImageIcon(getClass().getResource("/image/2_10.png"));
      jlabel.setIcon(icon);
      icon.setImage(icon.getImage().getScaledInstance(200, 250, Image.SCALE_SMOOTH));
      jlabel.setBounds(0, 0, 200, 250);

      return jlabel;
   }
   public JPanel im(Game game){
      return panel;
   }





}