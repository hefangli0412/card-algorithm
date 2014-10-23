import java.util.ArrayList;
import java.util.Random;

/**
 *  Represent a full deck of cards in random order at the start and the remaining 
 *  unused part of cards when the game proceed.
 */

/**
 * @author hefangli
 *
 */
public class Deck {

	private ArrayList<Card> cards;

    /**
     * Constructor. Create a deck of cards and shuffle them into a random order.
     */
	Deck() {
		cards = new ArrayList<Card>();
		int index_1, index_2;
		Random generator = new Random();
		Card temp;

		for (int a=1; a<=13; a++) {
			for (int b=1; b<=4; b++)
			 {
			   cards.add(new Card(a,b));
			 }
		}  
		// add Jokers
//		cards.add(new Card());
//		cards.add(new Card());

		for (int i=0; i<100; i++) {
			index_1 = generator.nextInt( cards.size() - 1 );
			index_2 = generator.nextInt( cards.size() - 1 );

			temp = cards.get( index_2 );
			cards.set( index_2 , cards.get( index_1 ) );
			cards.set( index_1, temp );
		}
	}

	public Card drawFromDeck() {
		if ( cardsLeft() > 0 ) {
			return cards.remove( 0 );
		} else {
			return null;
		}
	}
	
	public ArrayList<Card> drawFromDeck(int num) {
		ArrayList<Card> tmp = new ArrayList<Card>();
		for (int i = 0; i < num; i++) {
			if ( cardsLeft() > 0 ) {
				tmp.add(drawFromDeck());
			} else {
				break;
			}
		}
		return tmp;
	}

    /**
     * As cards are dealt from the deck, the number of 
     * cards left decreases.  This function returns the 
     * number of cards that are still left in the deck.
     */
    public int cardsLeft() {
    	return cards.size();
    }
    
    public Deck shuffle() {
    	return new Deck();
    }
    
    public void displayAll() {
		System.out.print("number of cards left in Deck = " + cards.size());

    	for (int i = 0; i < cards.size(); i++) {
			if (i % 5 == 0) {
				System.out.println();
			}
			System.out.print(cards.get(i).toString() + "\t");
    	}
    	
		System.out.println();
		System.out.println();
    }
}
