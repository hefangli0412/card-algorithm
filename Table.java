import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Mimic the pad/table in the middle.
 */

/**
 * @author hefangli
 *
 */
public class Table {
	private ArrayList<Card> cards;
	private int highestValue;
	private int score;
	
	Table() {
		this.cards = new ArrayList<Card>();
		this.highestValue = Integer.MIN_VALUE;
		this.score = 0;
		assert isValidCards();
	}
	
	/**
	 * Some player swipe out a valid card onto the table.
	 * Add a card to arraylist
	 * Modify biggestCard
	 * Add to score if the card values bigger than 0
	 * @param c a card bigger than the biggest card on the table except for 7(>=)
	 */
	public void addCard(Card c) {
		assert isValidAddedCard(c);
		this.cards.add(c);
		this.highestValue = CardValue.values.get(c).intValue();
		this.score += getCardScore(c);
		assert isValidCards();
	}

	public int getHighestValue() {
		return this.highestValue;
	}
	
	public int getTableScore() {
		return this.score;
	}
	
	public void clear() {
		this.cards = new ArrayList<Card>();
		this.highestValue = Integer.MIN_VALUE;
		this.score = 0;
		assert isValidCards();
	}
	
	public void displayAll() {
		System.out.println("cards on the table:");
		
		ListIterator<Card> iter = this.cards.listIterator();
		while ( iter.hasNext() ) {
			Card c = iter.next();
			int v = CardValue.values.get(c).intValue();
			System.out.println( c + "  " + v); //calls cards[x].toString()
		}
		
		System.out.println();		
		System.out.println("highest value is " + this.highestValue);
		System.out.println("table score is " + this.score);
		System.out.println("card count is " + this.cards.size());
		System.out.println();
		System.out.println();
	}
	
	// **************************************************************
    //  PRIVATE METHOD(S)	
	/**
	 * Only need to calculate card's score in table class. As for player's 
	 * score, pass table's total score to him/her.
	 */
	private int getCardScore(Card c) {
		if (c.getValue() == 5) {
			return 5;
		} else if (c.getValue() == 10 || c.getValue() == 13) {
			return 10;
		} else {
			return 0;
		}
	}
	  
	/**
    Returns true iff the cards data is in a valid state.
    Checking point: the score field equals to all scores of cards on the table.
    Checking point: the biggestCard field equals to the biggest value card.
    Checking point: .
	 */
	private boolean isValidCards() {
		if (this.cards.isEmpty()) {
			return true;
		}
		
		int s = 0;
		for (Card c: this.cards) {
			s += getCardScore(c);
		}
		if (s != this.score) {
			return false;
		}
		
		int v = 0;
		for (Card c: this.cards) {
			v = Math.max(c.getValue(), v);
		}
		if (v != this.highestValue) {
			return false;
		}
		
		return true;     
	}
	
	private boolean isValidAddedCard(Card c) {
		if (c.getValue() == 7) {
			return true;
		}
		if (c.getValue() <= this.highestValue) {
			return false;
		}
		
		return true;
	}
}
