import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Scanner;

/**
 * The whole game structure of SevenFiveTwoThree that implements the Game interface.
 * Some variations from the real game.
 * The real game should allow swipe out two or three card of same suit.
 * The real game recognize the first person who has swipe out all his/her cards as a winner.
 * The real game include two Jokers.
 */

/**
 * @author hefangli
 *
 */
public class SevenFiveTwoThreeGame implements Game {

	private ArrayList<Hand> players;
	private Deck deck;
	private Table table;
	
	private Hand currentPlayer;
	private int skipCount;
	
	private Scanner in; //delete
	
	SevenFiveTwoThreeGame( ArrayList<String> playerNames ) {

		this.deck = new Deck();
		this.players = new ArrayList<Hand>();		
		for (String pn: playerNames) {
			this.players.add(new Hand(pn, this.deck));
		}
		this.table = new Table();
		
		this.currentPlayer = null;
		this.skipCount = 0;
		
		this.in = new Scanner(System.in); //delete
	}
	
	
	/**
	 * Mimic a play of game from start to end. A final winner will come out at the end.
	 */
	public void play() {
		chooseFirstPlayer();
		while ( !isEndOfGame() ) {
			allPlayersAddCards();
			round();
		}
			
		System.out.print("====================================");
		System.out.print("Final winner come out - ");
		ListIterator<Hand> iter = getWinner().listIterator();
		while ( iter.hasNext() ) {
			Hand p = iter.next();
			System.out.print(p.getPlayerName() + " (" + p.getScore() + ") ");
		}
	}

	public ArrayList<Hand> getWinner() {
		ListIterator<Hand> iter = this.players.listIterator();
		ArrayList<Hand> winner = new ArrayList<Hand>();
		int highestScore = 0;
		while ( iter.hasNext() ) {
			Hand p = iter.next();
			int s = p.getScore();
			if ( s != 0 && s > highestScore) {
				highestScore = s;
				winner.add(p);
			}
		}
		return winner;
	}
	
	public void replay() {
		this.deck = new Deck();
		this.table.clear();
		this.currentPlayer = null;
		this.skipCount = 0;
		
		play();
	}
	
	/**
	 * Mimic a round of play. A temporary winner will be raised at the end.
	 */
	private void round() {
		while ( this.skipCount != this.players.size()-1 ) {
			
			System.out.println("skip count is " + this.skipCount);
			System.out.println();
			System.out.println();
			
			currentPlayerAction();
			this.currentPlayer = chooseNextPlayer();
			
			System.out.println("current player is " + this.currentPlayer.getPlayerName());
			System.out.println();
			System.out.println();
		}	
		// TODO a hand raise session
		
		this.currentPlayer.addScore(this.table.getTableScore());
		this.table.clear();
		this.skipCount = 0;
		
		System.out.println("this round the winner " + this.currentPlayer.getPlayerName() 
				+ " has score " + this.currentPlayer.getScore());
		System.out.println();
		System.out.println();
		
	}

	/**
	 * Mimic an action of the current player. He/She will choose to skip or swipe out 
	 * a card in the end. If a card is swiped out, the skip sign should be cleared
	 * otherwise increases.
	 */
	private void currentPlayerAction() {
		while ( true ) {
			while ( !isMessageReceivedFromPlayer() ) {
				continue;
			}
			
			/**
			 * Automatically choose to skip if current player has no cards.
			 */
			if ( this.currentPlayer.getCardCount() == 0 ) {
				this.skipCount++;

				System.out.println(this.currentPlayer.getPlayerName() +
						"has no cards, automatically skipped");
				System.out.println();
				System.out.println();
				
				return;
			}
			
			if ( getSkipChoosingMessageFromPlayer() )  {
				this.skipCount++;
				
				System.out.println("skip count is " + this.skipCount);
				System.out.println();
				System.out.println();
				
				return;
			}
			
			int index = getCardChoosingMessageFromPlayer();
			Card c = this.currentPlayer.getCardAtIndex(index);
			int v = CardValue.values.get(c).intValue();
			
			System.out.println("you choosed card " + c.toString());
			System.out.println("card value = " + v);
			System.out.println("table highest value = " + this.table.getHighestValue());
			System.out.println(v >= this.table.getHighestValue());
			System.out.println();
			System.out.println();	
			
			if ( v >= this.table.getHighestValue()) {
				this.table.addCard(c);
				this.currentPlayer.removeCard(c);
				this.skipCount = 0;
				this.table.displayAll();
				this.currentPlayer.displayAll();
				break;
			} else {
				// TODO notify current player to choose another valid card
				System.out.println("the card you choose is too small. please retry.");
				System.out.println();
				System.out.println();
			}
		}	
	}
	
	private void allPlayersAddCards() {
		/**
		 * In the first loop, add cards to players behind current player
		 * including current player.
		 */
		ListIterator<Hand> iter = this.players.listIterator();
		while ( iter.hasNext() ) {
			Hand player_1 = iter.next();
			if ( player_1.equals(this.currentPlayer) ) {
				player_1.addCardsFromDeck(this.deck);
				while ( iter.hasNext() ) {
					Hand tmp = iter.next();
					tmp.addCardsFromDeck(this.deck);
				}
			}			
		}
		
		/**
		 * In the second loop, add cards to players before current player.
		 */
		iter = this.players.listIterator();
		while ( iter.hasNext() ) {
			Hand player_2 = iter.next();
			if ( !player_2.equals(this.currentPlayer) ) {
				player_2.addCardsFromDeck(this.deck);
			} else {
				break;
			}
		}
		
		iter = this.players.listIterator();
		while ( iter.hasNext() ) {
			iter.next().displayAll();
		}
		this.deck.displayAll();
	}
	
	public boolean isMessageReceivedFromPlayer() {
		// TODO Auto-generated method stub
		return true;
	}	
	
	public boolean getSkipChoosingMessageFromPlayer() {
		// TODO Auto-generated method stub
		System.out.println("Here is your cards.");
		this.currentPlayer.displayAll();
		System.out.println("Here is table cards.");
		this.table.displayAll();
		System.out.println("Do you choose to skip this round? y or n?");
		String response = in.nextLine();
		if (response.contains("y")) {
			System.out.println("you skipped this round");
			System.out.println();
			System.out.println();
			
			return true;
		} else {
			return false;
		}		
	}

	public int getCardChoosingMessageFromPlayer() {
		// TODO Auto-generated method stub
		System.out.println("Which card do you choose? from 0 to " + (this.currentPlayer.getCardCount()-1) + "?");
		int response = in.nextInt();
		in.nextLine();
		int hv = this.table.getHighestValue();
		while (hv != Integer.MIN_VALUE && response > hv) {
			System.out.println("Wrong index. please retry");
			response = in.nextInt();
			in.nextLine();
		}
		return response;
	}
	
	/**
	 * Must be called before all play()s are executed to set up the current player.
	 */
	private void chooseFirstPlayer() {
		Hand firstPlayer = null;
		int smallest = Integer.MAX_VALUE;
		ListIterator<Hand> iter = players.listIterator( players.size() );
		while ( iter.hasPrevious() ) {
			Hand tmp = iter.previous();
			int v = tmp.getSmallestCardValue();
			System.out.println("smallest card value of " + tmp.getPlayerName() + " is " + v);
			
			if ( v <= smallest ) {
				smallest = v;
				firstPlayer = tmp;
			}
		}
		
		this.currentPlayer = firstPlayer;
		System.out.println("So the first player is " + this.currentPlayer.getPlayerName());
		System.out.println();
		System.out.println();
	}	

	/**
	 * Choose next player in a circle.
	 */
	private Hand chooseNextPlayer() {
		ListIterator<Hand> iter = this.players.listIterator();
		Hand np = null;
		while ( iter.hasNext() ) {
			Hand p = iter.next();
			// find current player located at p.
			if ( p.equals(this.currentPlayer) ) {
				if ( iter.hasNext() ) {
					np = iter.next();
					return np;
				} else {
					return this.players.get(0);
				}
			}
		}
		// cannot find current player, this is wrong.
		return null;
	}

	/**
	 * Must be checked before each play() is called.
	 */
	private boolean isEndOfGame() {
		ListIterator<Hand> iter = this.players.listIterator();
		int totalScore = 0;
		while ( iter.hasNext() ) {
			Hand p = iter.next();
			totalScore += p.getScore();
		}
		
		return ( totalScore == 100 );
	}

}
