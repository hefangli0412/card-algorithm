
public class HandTester {

	public static void main(String[] args) {
		Deck d = new Deck();
		Hand hand = new Hand("hefangli", d);
		hand.displayAll();
		System.out.println();
		
		hand.sortByValue();
		System.out.println("After sorting...");
		hand.displayAll();

	}

}
