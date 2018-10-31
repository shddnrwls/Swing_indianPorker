
package net.skhu;

public class Game {
	private User user;
	private Ai ai;
	private int drawCoin;
	static public int round;

	public Game(){
		user = new User();
		ai = new Ai();
		round = 1;
	}
	public Ai getAi(){ //ai �ҷ�����
		return ai;
	}
	public int getRound(){
		return round;
	}
	public void userDie(){
		ai.setUserCard((int)user.userCardDeck().get(round-1));
	}
	public User getUser(){ //user�ҷ�����
		return user;
	}
	public void setRound(){
		round=round+1;
	}
	public int getDrawCoin() {
		return drawCoin;
	}
	//������� ��������
	public void setDrawCoin(int drawCoin) {
		this.drawCoin=drawCoin;
	}

	public String winner()
	{
		return user.getUserCoin() >ai.getAiCoin() ? "user�� �¸��ϰ� ai�� �����ϴ�" : "ai��  �¸��ϰ�  user�� �����ϴ�";
	}

	public boolean exhaustion()
	{
		if(user.getUserCoin()<=0 || user.getUserCoin()<=0)
			return true;
		else
			return false;
	}

	public String coinExhaustion()
	{
		if(user.getUserCoin()<=0)
			return "ai�� �¸��ϰ� user�� �����ϴ�";
		else// if(ai.getAiCoin()<=0)
			return "user�� �¸��ϰ� ai�� �����ϴ�";
		
		//return user.getUserCoin()<=0 ? "ai�� �¸��ϰ� user�� �����ϴ�" : "user�� �¸��ϰ� ai�� �����ϴ�";
	}
	

}
