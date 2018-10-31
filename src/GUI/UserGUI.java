package GUI;

import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.skhu.Game;

public class UserGUI {

	private JPanel panel = new JPanel();
	private ImageIcon icon;

	public JPanel changeView(Game game,int check){
		JLabel userCard =new JLabel("");

		panel.setLayout(null);
		panel.setBounds(200,150,200,250);
		panel.add(changeImage(game.getUser().card(),check,game.getRound()));
		return panel;
	}
	public JLabel changeImage(List list,int check,int round){
		if(check ==0){
			JLabel userCard =new JLabel("");
			ImageIcon icon=new ImageIcon(getClass().getResource("/image/back.png"));
			userCard.setIcon(icon);
			icon.setImage(icon.getImage().getScaledInstance(200, 250, Image.SCALE_SMOOTH));
			userCard.setBounds(0, 0, 200, 250);
			return userCard;

		}
		else{
			JLabel userCard =new JLabel("");
			if(list.get(round-1).equals(1)) icon=new ImageIcon(getClass().getResource("/image/1_1.png"));
			else if(list.get(round-1).equals(2)) icon=new ImageIcon(getClass().getResource("/image/1_2.png"));
			else if(list.get(round-1).equals(3)) icon=new ImageIcon(getClass().getResource("/image/1_3.png"));
			else if(list.get(round-1).equals(4)) icon=new ImageIcon(getClass().getResource("/image/1_4.png"));
			else if(list.get(round-1).equals(5)) icon=new ImageIcon(getClass().getResource("/image/1_5.png"));
			else if(list.get(round-1).equals(6)) icon=new ImageIcon(getClass().getResource("/image/1_6.png"));
			else if(list.get(round-1).equals(7)) icon=new ImageIcon(getClass().getResource("/image/1_7.png"));
			else if(list.get(round-1).equals(8)) icon=new ImageIcon(getClass().getResource("/image/1_8.png"));
			else if(list.get(round-1).equals(9)) icon=new ImageIcon(getClass().getResource("/image/1_9.png"));
			else if(list.get(round-1).equals(10)) icon=new ImageIcon(getClass().getResource("/image/1_10.png"));
			userCard.setIcon(icon);
			icon.setImage(icon.getImage().getScaledInstance(200, 250, Image.SCALE_SMOOTH));
			userCard.setBounds(0, 0, 200, 250);

			return userCard;
		}
	}
	public JPanel im(Game game){
		return panel;
	}
}