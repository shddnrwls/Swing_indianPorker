

package net.skhu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class User {
	private List<Integer> cardList;
	static int coin;
	static public int nowUser;

	static int getCoin() {
		return coin;
	}

	public User(){
		cardList=Arrays.asList(1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10);
		coin = 30;
		Collections.shuffle(cardList);
	    System.out.println("user ī�� ��: "+cardList);
	}

	public int getUserCard() {
		nowUser = cardList.get(Game.round - 1);
		return nowUser;
	}

	public List userCardDeck(){
		return cardList;
	}

	public int number(Game game){
		return cardList.get(game.getRound()-1);
	}

	public List card(){
		return cardList;
	}

	public int getUserCoin() {
		return coin;
	}

	public void betCoin(int bet) {
		this.coin -=bet;
	}

	public void setCoin(int coin) {
		this.coin=coin;
	}
}
