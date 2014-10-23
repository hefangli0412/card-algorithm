import java.util.ArrayList;

/**
 * 
 */

/**
 * @author hefangli
 *
 */
public class Mock {
	
	public static void main(String[] args) {
		
		// create a list of players
		ArrayList<String> playerList = new ArrayList<String>();
		playerList.add("weiyi");
		playerList.add("kanika");
		playerList.add("supriya");
		playerList.add("yan");
		
		Game game = new SevenFiveTwoThreeGame(playerList);
		
		// play the game
		game.play();
		
		// replay the game
//		game.replay();
	}
}
