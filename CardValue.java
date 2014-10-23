import java.util.HashMap;
import java.util.Map;

/**
 * @author hefangli
 *
 */

public class CardValue {
	static final public Map<Card, Integer> values;
	
	static {
		values = new HashMap<Card, Integer>();
		for (int a=1; a<=13; a++) {
			for (int b=1; b<=4; b++) 
			{
				int value;
				switch ( a ) {
				case 4:	value = 1; break;
				case 6: value = 2; break;
				case 8: value = 3; break;
				case 9: value = 4; break;
				case 10: value = 5; break;
				case 11: value = 6; break;
				case 12: value = 7; break;
				case 13: value = 8; break;
				case 1:	value = 9; break;
				case 3: value = 10; break;
				case 2: value = 11; break;
				case 5: value = 12; break;
				default: value = 13; break;
				}
				values.put(new Card(a,b), value);
			 }
		} 
	}
	
}
