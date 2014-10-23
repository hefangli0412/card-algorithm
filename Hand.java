import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ListIterator;

/**
 *  Represent a hand of cards, also be called player.
 */

/**
 * @author hefangli
 *
 */

public class Hand {
	private String playerName;
	private ArrayList<Card> cards;
	private int score;
	private final int MAX_CARDS = 5;
	
	Hand(String pn, Deck d) {
		this.playerName = pn;
		this.cards = d.drawFromDeck(MAX_CARDS);
		this.score = 0;	
	}
	
    /**
     * Get the card at a particular position.
     */
	public Card getCardAtIndex(int index) {
		if (index >= 0 && index < this.cards.size()) {
			return this.cards.get(index);
		} else {
			return null;
		}
	}

    /**
     * Add the card c to the hand.  c should be non-null.
     * @throws NullPointerException if c is null.
     */
/*    public void addCard(Card c) {
    	if (getCardCount() >= MAX_CARDS) {
    		System.out.printf("cannot add card to hand, hand is full");
    	} else {
    		this.cards.add(c);
    	}
    }
*/
    /**
     * Draw cards from deck to make hand full.
     */
    public void addCardsFromDeck(Deck d) {
    	ArrayList<Card> c = d.drawFromDeck(MAX_CARDS-getCardCount());
    	this.cards.addAll(c);
    	sortByValue();
    }

    /**
     * If the specified card is in the hand, it is removed.
     */
    public void removeCard(Card c) {
    	this.cards.remove(c);
    }

    /**
     * Remove the card in the specified position from the
     * hand.  Cards are numbered counting from zero.
     * @throws IllegalArgumentException if the specified 
     *    position does not exist in the hand.
     */
    public void removeCardAtIndex(int index) {
    	this.cards.remove(index);
    }
    /**
     * Exchange the positions of two cards in the player's own hand.
     */
    public void exchangeCard(int index_1, int index_2) {
		Card temp = cards.get( index_2 );
		cards.set( index_2 , cards.get( index_1 ) );
		cards.set( index_1, temp );
    }
    
    public void addScore(int s) {
    	this.score += s;
    }
    
    public int getScore() {
    	return this.score;
    }

    /**
     * Sorts the cards in the hand so that cards are sorted into
     * order of increasing value.  The suits of cards do not matter. 
     */
    public void sortByValue() {
    	Collections.sort(this.cards, new CardComparator());
    }
 
    /**
	 * This method is called at the start of the game to decide who should be
	 * the first one the start. We can use sort since we use it anyway.
	 */
	public int getSmallestCardValue() {
		sortByValue();
		
		Card c = this.cards.get(0);
		int v = CardValue.values.get(c).intValue();
		
		System.out.println("Hand class: smallest card of " + this.playerName + " is " + c.toString());
		
		return v;
	}
    
    public String getPlayerName() {
    	return this.playerName;
    }
    	
    /**
     * Print out all the cards in hand for debug purpose.
     */
	public void displayAll() {
		System.out.println("player " + this.playerName + " has cards:");
		
		ListIterator<Card> iter = this.cards.listIterator();
		while ( iter.hasNext() ) {
			Card c = iter.next();
			int v = CardValue.values.get(c).intValue();
			System.out.println( c + "  " + v); //calls cards[x].toString()
		}
		System.out.println();
		System.out.println();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		Hand other = (Hand) obj;

		return ( this.playerName.equals(other.playerName) ); 
	}
	
    /**
     * Return the number of cards in the hand.
     */
    public int getCardCount() {
    	return this.cards.size();
    }
	
}

//*****************************************************************
//Helper class needed for call to Collections.sort above

class CardComparator implements Comparator<Card> {
	public int compare(Card c1, Card c2) {
		int v1 = CardValue.values.get(c1).intValue();
		int v2 = CardValue.values.get(c2).intValue();
		return (v1 - v2);
	}
}
