package GUI;

import java.awt.Button;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.List;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.skhu.Ai;
import net.skhu.Game;
import net.skhu.User;

//ù ȭ�� frame
public class MainFrame extends JFrame {

	private UserGUI usergui = new UserGUI();
	private AiGUI aigui = new AiGUI();
	private Game game;
	private List list = new List();
	private JLabel label;
	private JTextField textField;
	private User user;
	private Ai ai;
	private int aiBet;
	static int userBet;
	private int aiDie;
	private int totalAiBet;
	private int totalUserBet;


	private ImageIcon startButtonImage = new ImageIcon(Main.class.getResource("../image/gamestart.png"));
	private ImageIcon dieButtonImage = new ImageIcon(Main.class.getResource("../image/Die3.png"));
	private ImageIcon conButtonImage = new ImageIcon(Main.class.getResource("../image/con2.png"));
	private ImageIcon battingButtonImage = new ImageIcon(Main.class.getResource("../image/batting2.png"));
	private JButton startButton = new JButton(startButtonImage);
	private Image Background;
	private Image screenImage;
	private Graphics screenGraphic;
	// �߰�����count��
	private int count;
	private JPanel panel;
	JButton button;
	private boolean isstart = true;

	public static int getUserBet() {
		return userBet;
	}

	public MainFrame(Game game) {
		this.game = game;
	}

	// ���ӽ��� ��ư Ŭ���� ȭ�� repaint
	public void MainViewchange(JPanel panel) {

		getContentPane().removeAll();
		getContentPane().add(panel);
		revalidate();
		repaint();
	}

	public void gameViewChange(JPanel panel, AiGUI aigui) {

	}

	// ���� ȭ�� ����
	public JPanel mainView() {
		JPanel panel = new JPanel(null);
		setUndecorated(true);
		setSize(1280,720);
		setResizable(false);
		setLocationRelativeTo(null);


		JLabel jlabel = new JLabel("");
		ImageIcon icon = new ImageIcon(getClass().getResource("/image/card.jpg"));
		jlabel.setIcon(icon);
		icon.setImage(icon.getImage().getScaledInstance(1280, 720, Image.SCALE_SMOOTH));
		jlabel.setBounds(0, 0, 1280, 720);
		panel.add(jlabel);

		button = new JButton(startButtonImage);
		button.setBounds(350,400, 600, 302);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		add(button);


		return panel;
	}



	// ���� ���� ������ ���� ȭ�� ����
	public JPanel gameView(Game game) {

		panel = new JPanel(null);

		setSize(1280,720);
		setResizable(false);
		setLocationRelativeTo(null);
		changeText(game, 0);
		JButton batting = new JButton(battingButtonImage);
		batting.setBounds(430, 260, 127, 64); // ����
		JButton die = new JButton(dieButtonImage);
		die.setBounds(590, 260, 127, 64); // ����
		JButton cont = new JButton(conButtonImage);
		cont.setBounds(750, 260, 127, 64); // ���
		JLabel jlabel = new JLabel("");
		ImageIcon icon = new ImageIcon(getClass().getResource("/image/main4.jpg"));
		jlabel.setIcon(icon);
		icon.setImage(icon.getImage().getScaledInstance(1280, 720, Image.SCALE_SMOOTH));
		jlabel.setBounds(0, 0, 1280, 720);

		panel.add(aigui.changeView(game));
		panel.add(batting);
		panel.add(die);
		panel.add(cont);
		panel.add(usergui.changeView(game, 0));
		panel.add(list);

		Button betBtn = new Button("�Է�");
		betBtn.setBounds(715, 335, 50, 27);
		panel.add(betBtn);
		betBtn.setEnabled(false);

		label = new JLabel();
		label.setBounds(new Rectangle(550, 310, 120, 79));
		label.setText("������ ���� �Է�: ");
		panel.add(label);

		JTextField inCoin = new JTextField();
		inCoin.setColumns(3);
		inCoin.setBounds(660, 337, 50, 25);
		panel.add(inCoin);
		inCoin.setEnabled(false);
		panel.add(jlabel);



		//���ÿ��� �Է� ��ư �������� �̺�Ʈ, user�� �����ϸ� �׿����� ai�� ���� or ����
		betBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String s = inCoin.getText();
				inCoin.setText("");
				String msg = "User ����=" + s;
				userBet = Integer.parseInt(s);
				if(userBet<=0)
				{
					list.add("0���Ϸδ� ������ �� �����ϴ�. �ٽ� �������ּ���");
					return ;
				}

				else {
					// �� ������ ������ ����
					totalUserBet += userBet;

					if(totalUserBet<totalAiBet && totalUserBet==game.getUser().getUserCard()) {
						roundWinner();
					}
					//user�� ai�� ������ ������ ���� ���ɰ� �ϱ�
					else if(totalUserBet<totalAiBet) {
						userLimitCoin(totalUserBet,totalAiBet);
						totalUserBet -=userBet;


					}
					else if (userBet > game.getUser().getUserCoin() || totalUserBet > game.getUser().getUserCoin()) {
						// ���� �� ������ ��ũ�ٸ�
						list.add("���� �� ���� ���� ������ ���� ���� �������ϴ�!");
						list.add("�ٽ� ����");
						totalUserBet -= userBet;

					}
					else {
						count++;
						System.out.println("count�� ������ "+count);
						//ai�� ������ �Ͽ��µ� user�� ���� ���� ������ �Ͽ����� ī�带 ���ؼ� ���� ���
						if(((totalAiBet!=0&&totalUserBet!=0)&&totalAiBet==totalUserBet)) {
							list.add(msg);
							list.add("user�� ai�� �� ������ ���� �����ϴ�.");
							roundWinner();
							panel.add(jlabel);
						}
						//������ ������ ���� �Ͽ���, ai�� ������ �����϶�
						else if(game.getUser().getUserCoin()==totalUserBet&&totalAiBet>0) {
							list.add(msg);
							roundWinner();
						}
						else {
							// *****************************************************************
							System.out.println("���� ���ð�: " + userBet + " ������Ż ���ð�: " + totalUserBet);
							// *****************************************************************
							list.add(msg);

							if(count ==3) //�߰����� 3ȸ�� �ϱ�
							{
								//                  list.add(game.winner());
								//                  roundTwentyEnd(); //20���� �Ǹ�  ���� ��Ű�� ->���� 738
								list.add("�߰� ����3ȸ�� ����� ��ǥ�մϴ�!");
								roundWinner();
								count = 0;//0���� �ʱ�ȭ
								return ;

							}
							battleAiWithUser();
							panel.add(jlabel);

						}

					}
				}
			}
		});

		// ���� ��ư �������� �Է¹�ư�� ���� �Է�â Ȱ��ȭ
		batting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				betBtn.setEnabled(true);
				inCoin.setEnabled(true);
				panel.repaint();
				panel.add(jlabel);

			}
		});

		// die ��ư Ŭ���� �̺�Ʈ, user�� ����, ����ī�� �����ְ� ai �¸�
		die.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// ���尡 �������� ������ ī�� �����ֱ�
				usergui.im(game).removeAll();
				// panel.remove(usergui.im(game));
				panel.add(usergui.changeView(game, 1));

				changeText(game, 1);
				panel.repaint();
				userDie();
				count = 0; // ���尡�Ѿ������ �ʱ�ȭ ��Ű��
				panel.add(jlabel);
			}
		});

		// ��� ��ư ������ �� �̺�Ʈ, ���尡 �ٲ�� ī�� �ٲ�
		cont.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				if (game.getRound() == 11) {
//					game.getAi().aiRechargeDeck();
//					game.getUser().userRechargeDeck();
//
//				}
				betBtn.setEnabled(false);
				inCoin.setEnabled(false);
				game.setRound();
				// ���尡 �������� ������ ī�� �����ֱ�
				// ai�� ī�嵵 �ٲٱ�

				if (game.getRound() <= 20) {
					usergui.im(game).removeAll();
					// panel.remove(usergui.im(game));

					panel.remove(aigui.im(game));
					panel.add(aigui.changeView(game));
					panel.add(usergui.changeView(game, 0));
					changeText(game, 0);
					panel.repaint();
					panel.add(jlabel);

				} else if (game.getRound() > 20) {
					list.add("");
					list.add("������ �������ϴ�");
					list.add(game.winner());
					roundTwentyEnd();
					panel.repaint();

				}

				totalUserBet = 0;
				totalAiBet = 0;
				userBet = 0;
				aiBet = 0;
				count = 0;
			}
		});

		return panel;
	}

	// ����ȭ�� �ؿ� ������ �۾���
	public void changeText(Game game, int check) {
		list.setBounds(410, 430, 484, 120);
		if (check == 1) {
			String msg = "������� ���ڴ�" + game.getUser().number(game);
			list.add(msg);
		} else if (game.getRound() == 1) { // 1���� ����

			int first =((int) (Math.random() * 2)) + 1;
			list.add("INDIAN ��Ŀ�� ���Ű� ȯ���մϴ�.");
			list.add("\n");
			list.add(String.format("%d���带 �����ϰڽ��ϴ�.", game.getRound()));
			String currentCoin = String.format("User ����: %d, Ai ����: %d, ���º� ����: %d", game.getUser().getUserCoin(),
					game.getAi().getAiCoin(), game.getDrawCoin());
			list.add(currentCoin);
			list.add("\n");
			String betFirstMsg = first == 1 ? "����ڰ� ���� ������ �����մϴ�" : "Ai�� ���� ������ �����մϴ�.";
			list.add("�߰������� 3ȸ���� �����մϴ�!");
			list.add("\n");
			list.add(betFirstMsg);

			if (first == 1) // ����� ���� ����
			{
				// ���� ��ư�̺�Ʈ�� Ȱ��ȭ�Ǽ� addActionListener�� ����
			} else { // ai ���� ����
				//            count += 1; // AI�� ���� ������ �ϰ� �Ǹ� count���� 1����
				battleAiWithUser();
			}

		} else if (game.getRound() > 1 && game.getRound() <= 20)// 10���� ����
		{
			if (game.exhaustion() == true) // ������ �پ��� �Ǹ� �¸��� �Ǻ��ϱ�
			{
				roundWinner();
				list.add(game.coinExhaustion());
				System.exit(0);
			} else // ������ �� �Ƚ�ٸ�
			{
				int first = ((int) (Math.random() * 2)) + 1;
				list.add("\n");
				list.add(String.format("%d���带 �����ϰڽ��ϴ�.", game.getRound()));
				String currentCoin = String.format("User ����: %d, Ai ����: %d, ���º� ����: %d", game.getUser().getUserCoin(),
						game.getAi().getAiCoin(), game.getDrawCoin());
				list.add(currentCoin);
				list.add("\n");
				String betFirstMsg = first == 1 ? "����ڰ� ���� ������ �����մϴ�" : "Ai�� ���� ������ �����մϴ�.";
				list.add("�߰������� 3ȸ���� �����մϴ�!");
				list.add("\n");
				list.add(betFirstMsg);

				if (first == 1) // ����� ���� ����
				{
					// ���� ��ư�̺�Ʈ�� Ȱ��ȭ�Ǽ� addActionListener�� ����
				} else {// ai ���� ����
					//               count += 1; // AI�� ���� ������ �ϰ� �Ǹ� count���� 1����
					battleAiWithUser();

				}

				// ********************************************
				System.out.println(
						"totalUserBet: " + totalUserBet + " userBet: " + userBet + " totalAiBet: " + totalAiBet);
				// *******************************************
			}
		}

		else // ���� ���� 20�ʰ��� ���� �Ѿ��
			list.add(game.winner());
	}

	// AI�� User���� �ο�
	public void battleAiWithUser() {
		// aiBattingBattle�� aiDiePercentage���� ��ũ�ٸ� -> ������ Ȯ���� �� ũ�ٸ�
		if(game.getAi().aiDiePercentage(game.getUser().number(game)) == 1) {

			String ai = String.format("ai�� ������ ���� ���� -> %d",
					aiBet = game.getAi().aiBattingBattle(game.getUser().number(game), userBet));
			// �� AI�� ������ ����

			totalAiBet += aiBet;



			if (aiBet > game.getAi().getAiCoin() || totalAiBet > game.getAi().getAiCoin()) {
				// ���� �� ������ ��ũ�ٸ�
				list.add("ai�� ������ �ڱ� ���κ��� ���� �Ϸ��� �մϴ�!,ai �ٽ� ����");
				totalAiBet -= aiBet;
				againBatting();  // -> 657
				roundWinner();
				return ;
			}
			// user�� �������Ͽ��µ� ai�� �� ������ ���� ������ ī�尪�� ���ؼ� ����� ����ؾ� �Ѵ�.
			else if (((totalAiBet != 0 && totalUserBet != 0) && totalAiBet == totalUserBet)) {
				list.add(ai);
				list.add("\n");
				list.add("ai�� user�� ������ ���� �����ϴ�");
				roundWinner();

			}
			// ������ ������ �����̰�, ai�� ������ ��� �����Ͽ�����
			else if (game.getAi().getAiCoin() == totalAiBet && totalUserBet > 0) {
				list.add(ai);
				roundWinner();
			}
			// ������ ������ �� �����ѻ����̰�, ai�� ������ �Ͽ�����
			else if (totalUserBet == game.getUser().getUserCoin() && totalAiBet > 0) {
				list.add(ai);
				roundWinner();

			} else
				list.add(ai);

			// ************************************************************
			System.out.println("aiBet��: " + aiBet + " totalAiBet��: " + totalAiBet);
			// **************************************************************
		} else // ���� Ȯ���� ��ũ�ٸ�
		{
			String message = "ai��  ��� �ϴ��� �����߽��ϴ�";
			// ���� �׾����� ���׾����� Ȯ���ϴ� ���� ->����5�� �׾��ٴ� �Ҹ�
			aiDie = 5;
			if (aiDie == 5) {
				list.add(message);
				aiDieCheck(aiDie);
				aiDie = 0;
			}

		}
	}

	public JPanel aiDieCheck(int aiDie) {

		if (aiDie == 5) {
			// ���� ������ ���� �ʾҴµ�, ���̾��̰� ���� �Ҷ�
			if (aiBet == 0 && totalUserBet == 0) {
				System.out.println("here");
				// ������ �����ϳ��� �԰�, ���º� ���������� �Ա�
				game.getUser().setCoin(game.getUser().getUserCoin() + 1 + game.getDrawCoin());
				game.getAi().aiBetCoin(1);
				count = 0; // 0�����ʱ�ȭ
				list.add("������ �¸��߽��ϴ�!");

				if(game.getRound() <= 20) {
					int count = 0;
					for(int i=0; i < game.getAi().getAiRemoveCard().size(); ++i)
						if(game.getAi().getAiRemoveCard().get(i) == game.getAi().number(game)) count++;
					if(count >= 2) { }
					else game.getAi().setAiRemoveCard(game.getAi().number(game));
				}

				// ���尡 �������� ������ ī�� �����ֱ�
				if (game.getRound() <= 20) {
					usergui.im(game).removeAll();
					panel.add(usergui.changeView(game, 1));
					changeText(game, 1);
					panel.repaint();

				}

				game.setDrawCoin(0);
				// user �¸�
			} else if (aiBet == 0 && userBet > 0) // ���� ó���� ���ðɰ� ai�� �ٷ��״´ٸ�
			{
				game.getUser().setCoin(game.getUser().getUserCoin() + 1 + game.getDrawCoin());
				game.getAi().aiBetCoin(1);
				count = 0; // 0�����ʱ�ȭ
				list.add("������ �¸��߽��ϴ�!");

				if(game.getRound() <= 20) {
					int count = 0;
					for(int i=0; i < game.getAi().getAiRemoveCard().size(); ++i)
						if(game.getAi().getAiRemoveCard().get(i) == game.getAi().number(game)) count++;
					if(count >= 2) { }
					else game.getAi().setAiRemoveCard(game.getAi().number(game));
				}

				// ���尡 �������� ������ ī�� �����ֱ�
				if (game.getRound() <= 20) {
					usergui.im(game).removeAll();
					panel.add(usergui.changeView(game, 1));
					changeText(game, 1);
					panel.repaint();

				}

				game.setDrawCoin(0);
				totalUserBet = 0;
				userBet = 0;

			} else if (aiBet > 0 || userBet > 0) // ���� ������ �ɾ��µ� AI�� �׾��ٸ�
			{
				// AI�����ð� ���ε�� user�� �����ε��� �������� �Ѵ�, ���º����������� �Ա�
				game.getUser()
				.setCoin(game.getUser().getUserCoin() - userBet + totalAiBet + userBet + game.getDrawCoin());
				game.getAi().setCoin(game.getAi().getAiCoin() - totalAiBet);
				count = 0; // 0�����ʱ�ȭ
				list.add("������ �¸��߽��ϴ�!");

				if(game.getRound() <= 20) {
					int count = 0;
					for(int i=0; i < game.getAi().getAiRemoveCard().size(); ++i)
						if(game.getAi().getAiRemoveCard().get(i) == game.getAi().number(game)) count++;
					if(count >= 2) { }
					else game.getAi().setAiRemoveCard(game.getAi().number(game));
				}

				// ���尡 �������� ������ ī�� �����ֱ�
				if (game.getRound() <= 20) {
					usergui.im(game).removeAll();
					panel.add(usergui.changeView(game, 1));
					changeText(game, 1);
					panel.repaint();
				}

				// �����Ѱ� �� �ʱ�ȭ
				game.setDrawCoin(0);
				aiBet = 0;
				totalUserBet = 0;
				userBet = 0;
				totalAiBet = 0;
			}

		}

		return panel;

	}

	// ����ڰ� �����Ҷ�
	public void userDie() {
		if (totalUserBet == 0) // ���� ������ �ѹ��� �Ȱɾ��ٸ�
		{
			game.getUser().setCoin(game.getUser().getUserCoin() - 1);
			game.getAi().setCoin(game.getAi().getAiCoin() + 1 + game.getDrawCoin());

			if(game.getRound() <= 20) {
				int count = 0;
				for(int i=0; i < game.getAi().getAiRemoveCard().size(); ++i)
					if(game.getAi().getAiRemoveCard().get(i) == game.getAi().number(game)) count++;
				if(count >= 2) { }
				else game.getAi().setAiRemoveCard(game.getAi().number(game));
			}

			list.add(String.format("die �ϼ̽��ϴ� User������ %d �ٰ� ��������� �Ѿ�ϴ�", 1));
		}

		else // ���� ������ �ѹ��̶� �ɰ� ���̸� �����ٸ�
		{
			game.getUser().setCoin(game.getUser().getUserCoin() - totalUserBet);
			game.getAi().setCoin(game.getAi().getAiCoin() + totalUserBet + game.getDrawCoin());

			if(game.getRound() <= 20) {
				int count = 0;
				for(int i=0; i < game.getAi().getAiRemoveCard().size(); ++i)
					if(game.getAi().getAiRemoveCard().get(i) == game.getAi().number(game)) count++;
				if(count >= 2) { }
				else game.getAi().setAiRemoveCard(game.getAi().number(game));
			}

			list.add(String.format("die �ϼ̽��ϴ� User������ %d �ٰ� ��������� �Ѿ�ϴ�", totalUserBet));

		}
		game.setDrawCoin(0);
		totalUserBet = 0;
		totalAiBet = 0;
	}

	public void gameWinRule() {
		// user�� ī�尡 ��ũ��!(user��)
//		JLabel jlabel = new JLabel("");
//		ImageIcon icon = new ImageIcon(getClass().getResource("/image/main4.jpg"));
//		jlabel.setIcon(icon);
//		icon.setImage(icon.getImage().getScaledInstance(1280, 720, Image.SCALE_SMOOTH));
//		jlabel.setBounds(0, 0, 1280, 720);
		if (game.getUser().number(game) > game.getAi().number(game)) {

			if(game.getRound() <= 20) {
				int count = 0;
				for(int i=0; i < game.getAi().getAiRemoveCard().size(); ++i)
					if(game.getAi().getAiRemoveCard().get(i) == game.getAi().number(game)) count++;
				if(count >= 2) { }
				else game.getAi().setAiRemoveCard(game.getAi().number(game));
			}

			list.add("\n");
			list.add("user ī�尡 ai�� ī�庸�� ũ�Ƿ� user �¸�");
//			panel.add(jlabel);
			// ���尡 �������� ������ ī�� �����ֱ�
			if (game.getRound() <= 20) {
				usergui.im(game).removeAll();
				panel.add(usergui.changeView(game, 1));
				changeText(game, 1);
				panel.repaint();
//				panel.add(jlabel);
			}
			// user�� �̰����� ai�� �� �����ߴ����θԱ�, �� ���尡 ���º� ���ٸ� ���º� ���ε� �Ա�
			game.getUser().setCoin(game.getUser().getUserCoin() + totalAiBet + game.getDrawCoin());
			// ai�� �����Ƿ� ai�� ������ �ִ� ���ο��� �� ������ ������ ����.
			game.getAi().setCoin(game.getAi().getAiCoin() - totalAiBet);

			// ���º� ���� 0���� �ʱ�ȭ
			game.setDrawCoin(0);


		}
		// ai�� ī�尡 ��ũ��!(ai��)
		else if (game.getUser().number(game) < game.getAi().number(game)) {

			if(game.getRound() <= 20) {
				int count = 0;
				for(int i=0; i < game.getAi().getAiRemoveCard().size(); ++i)
					if(game.getAi().getAiRemoveCard().get(i) == game.getAi().number(game)) count++;
				if(count >= 2) { }
				else game.getAi().setAiRemoveCard(game.getAi().number(game));
			}

			list.add("\n");
			list.add("ai ī�尡 user�� ī�庸�� ũ�Ƿ� ai �¸�");

			// ���尡 �������� ������ ī�� �����ֱ�
			if (game.getRound() <= 20) {
				usergui.im(game).removeAll();
				panel.add(usergui.changeView(game, 1));
				changeText(game, 1);
				panel.repaint();
//				panel.add(jlabel);
			}

			// ai�� �̰����Ƿ� user�� ������ ������ �Դ´�., �� ���尡 ���ºο��ٸ� ���º� ���ε� �Ա�
			game.getAi().setCoin(game.getAi().getAiCoin() + totalUserBet + game.getDrawCoin());
			// user�� �����Ƿ� ������ �ִ� ���ο��� ������ ������ ����.
			game.getUser().setCoin(game.getUser().getUserCoin() - totalUserBet);

			// ���º� ���� 0���� �ʱ�ȭ
			game.setDrawCoin(0);

		}
		// ai�� ī��� user�� ī�尡 ������(���º�)
		else if (game.getUser().number(game) == game.getAi().number(game)) {

			if(game.getRound() <= 20) {
				int count = 0;
				for(int i=0; i < game.getAi().getAiRemoveCard().size(); ++i)
					if(game.getAi().getAiRemoveCard().get(i) == game.getAi().number(game)) count++;
				if(count >= 2) { }
				else game.getAi().setAiRemoveCard(game.getAi().number(game));
			}

			list.add("\n");
			list.add("ai ī��� user�� ī�尡 �����ϴ�");
			list.add("�̹� ���� ������ �����ϰ�, �������� ���ڰ� ������ ������ �������ϴ�.");
			// ������Ƿ� ���� ������ ���� ���� �����ϰ�, �������忡 ���
			game.setDrawCoin(totalAiBet + totalUserBet);
			// ������Ƿ� user�� ������ ���� ����
			game.getUser().setCoin(game.getUser().getUserCoin() - totalUserBet);
			// ������Ƿ� ai�� ������ ���� ����
			game.getAi().setCoin(game.getAi().getAiCoin() - totalAiBet);

			// ���尡 �������� ������ ī�� �����ֱ�
			if (game.getRound() <= 20) {
				usergui.im(game).removeAll();
				panel.add(usergui.changeView(game, 1));
				changeText(game, 1);
				panel.repaint();
//				panel.add(jlabel);
			}

		}
	}

	// ���� ���������� ��Ȳ�� ���� ���� ���
	public void roundWinner() {
		// user�� �� ������ ���μ��� ai�� �� ������ ���� ���� ������
		if (totalUserBet == totalAiBet) {
			// user�� ai�� ī�带 ���Ѵ�
			gameWinRule();
		}

		// ai�� ������ ���ÿ� ����
		else if (game.getAi().getAiCoin() == totalAiBet) {
			gameWinRule();

			// ai�� ������ �����߰�, ī��񱳽� user�� �̰��, ai�� ������ �� �����ϸ�
			if (game.getAi().getAiCoin() == 0) {
				list.add("ai�� ������ ��� �����Ͽ����ϴ�. ������ �������ϴ�");
				list.add(game.coinExhaustion());
				addBattingEnd(); // �޼ҵ� ���μ� -> 712
			}

			// ai�� ������ �����ߴµ� ī��������� ai�� �̱��, user�� ������ �� �Һ��ϸ�
			if (game.getUser().getUserCoin() == 0) {
				list.add("user�� ������ ��� �Һ��߽��ϴ�. ������ �������ϴ�");
				list.add(game.coinExhaustion());
				addBattingEnd(); // �޼ҵ� ���μ� -> 712
			}

		}

		// user�� ������ ����
		else if (game.getUser().getUserCoin() == totalUserBet) {
			gameWinRule();
			// user�� ������ �����߰�, ī��񱳽� user�� �̰��, ai�� ������ �� �����ϸ�
			if (game.getAi().getAiCoin() == 0) {
				list.add("ai�� ������ ��� �����Ͽ����ϴ�. ������ �������ϴ�");
				list.add(game.coinExhaustion());
				addBattingEnd(); // �޼ҵ� ���μ� -> 712
			}
			// user�� ������ �����ߴµ� ī��������� ai�� �̱��
			if (game.getUser().getUserCoin() == 0) {
				list.add("user�� ������ ��� �Һ��߽��ϴ�. ������ �������ϴ�");
				list.add(game.coinExhaustion());
				addBattingEnd(); // �޼ҵ� ���μ� -> 712
			}

		}

		// �߰� ���� 3ȸ�Ǹ�
		// ī�带 � �¸��� ������
		else if (count == 3) {
			gameWinRule();
			// user�� ������ �����߰�, ī��񱳽� user�� �̰��, ai�� ������ �� �����ϸ�
			if (game.getAi().getAiCoin() == 0) {
				list.add("ai�� ������ ��� �����Ͽ����ϴ�. ������ �������ϴ�");
				list.add(game.coinExhaustion());
				addBattingEnd(); // �޼ҵ� ���μ� -> 712

			}
			// user�� ������ �����ߴµ� ī��������� ai�� �̱��
			if (game.getUser().getUserCoin() == 0) {
				list.add("user�� ������ ��� �Һ��߽��ϴ�. ������ �������ϴ�");
				list.add(game.coinExhaustion());
				addBattingEnd(); // �޼ҵ� ���μ� -> 712
			}
		}

		totalUserBet = 0;
		userBet = 0;
		totalAiBet = 0;
		aiBet = 0;
	}

	// ������ ai�� �����Ѱź��� ���۰� ���ɰ� ����
	public void userLimitCoin(int totalUserBet, int totalAiBet) {
		if (totalAiBet > totalUserBet) // ������ AI���ü����� ������
		{
			list.add("AI�� �� �������� ������ �۽��ϴ� ���ų� ũ�� �ٽ� �����ϼ���");
			return;
		}
	}

	// ���� ������ �ϱ�
	public void addBattingEnd() {
		list.add("���� �ϼ̽��ϴ� ��վ�����? 10�ʵڿ� ����˴ϴ� ");
		list.add("�ٽ� �� �ƿ�~");
		Timer timer = new Timer();
		TimerTask time = new TimerTask() {
			@Override
			public void run() {
				System.exit(0); // ���� ����
			}
		};
		timer.schedule(time, 10000); // ������ ������ 10���Ŀ� �����Ű��

	}

	// 20���� �Ǹ� ������
	public void roundTwentyEnd() {
		if (game.getRound() > 20) {
			list.add("���� �ϼ̽��ϴ� ��վ�����? 10�ʵڿ� ����˴ϴ� ");
			list.add("�ٽ� �� �ƿ�~");
			Timer timer = new Timer();
			TimerTask time = new TimerTask() {
				@Override
				public void run() {
					System.exit(0); // ���� ����
				}
			};
			timer.schedule(time, 10000); // ������ ������ 10���Ŀ� �����Ű��
		}

	}
	//�߰����ðɶ� �� ���μ����� ������ �ɱ�
	public int againBatting() // ->339
	{
		// �� ���� ���� �� �� ���� �̻� ���ɰ� �ؾ��Ѵ�
		int rebet = (int)(Math.random()*(game.getAi().getAiCoin()-totalAiBet-totalUserBet-1))+Math.abs(totalUserBet - totalAiBet);
		list.add(String.format("ai�� ������ ���� ����  -> %d", rebet));
		totalAiBet += rebet;
		return rebet;
	}
}