import java.util.List;
import java.util.ArrayList;

/**
 * The ElevensBoard class represents the board in a game of Elevens.
 */
public class ElevensBoard extends Board {

	/**
	 * The size (number of cards) on the board.
	 */
	private static final int BOARD_SIZE = 9;

	/**
	 * The ranks of the cards for this game to be sent to the deck.
	 */
	private static final String[] RANKS =
		{"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};

	/**
	 * The suits of the cards for this game to be sent to the deck.
	 */
	private static final String[] SUITS =
		{"spades", "hearts", "diamonds", "clubs"};

	/**
	 * The values of the cards for this game to be sent to the deck.
	 */
	private static final int[] POINT_VALUES =
		{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 0, 0, 0};

	/**
	 * Flag used to control debugging print statements.
	 */
	private static final boolean I_AM_DEBUGGING = false;


	/**
	 * Creates a new <code>ElevensBoard</code> instance.
	 */
	 public ElevensBoard() {
	 	super(BOARD_SIZE, RANKS, SUITS, POINT_VALUES);
	 }

	/**
	 * Determines if the selected cards form a valid group for removal.
	 * In Elevens, the legal groups are (1) a pair of non-face cards
	 * whose values add to 11, and (2) a group of three cards consisting of
	 * a jack, a queen, and a king in some order.
	 * @param selectedCards the list of the indices of the selected cards.
	 * @return true if the selected cards form a valid group for removal;
	 *         false otherwise.
	 */
	public boolean isLegal(List<Integer> selectedCards) {
		return (containsPairSum11(selectedCards) || containsJQK(selectedCards));
	}

	/**
	 * Determine if there are any legal plays left on the board.
	 * In Elevens, there is a legal play if the board contains
	 * (1) a pair of non-face cards whose values add to 11, or (2) a group
	 * of three cards consisting of a jack, a queen, and a king in some order.
	 * @return true if there is a legal play left on the board;
	 *         false otherwise.
	 */
	@Override
	public boolean anotherPlayIsPossible() {
		ArrayList<Integer> theseCards  = (ArrayList<Integer>)cardIndexes();
		ArrayList<Integer> selectedCards = new ArrayList<Integer>();
		ArrayList<Card> nowThese = new ArrayList<Card>();
		for (int i : theseCards) {
			nowThese.add(cardAt(i));
		}
		for (int i = 0; i < nowThese.size(); i++) {
			for (int j = 0; i < nowThese.size(); i++) {
				Card itemp = cardAt(i);
				Card jtemp = cardAt(j);
				if (!itemp.matches(jtemp)) {
					selectedCards.add(i);
					selectedCards.add(j);
					if (containsPairSum11(selectedCards)) {
						return true;
					}
					selectedCards.remove(i);
					selectedCards.remove(j);
				}
			}
		}
		for (Card i : nowThese) {
			if (i.rank() == "jack") {
				for (Card j : nowThese) {
					if (j.rank() == "queen") {
						for (Card k : nowThese) {
							if (k.rank() == "king") {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * Check for an 11-pair in the selected cards.
	 * @param selectedCards selects a subset of this board.  It is list
	 *                      of indexes into this board that are searched
	 *                      to find an 11-pair.
	 * @return true if the board entries in selectedCards
	 *              contain an 11-pair; false otherwise.
	 */
	private boolean containsPairSum11(List<Integer> selectedCards) {
		int sum = 0;
		if (selectedCards.size() != 2) {
			return false;
		}
		for (int i : selectedCards) {
 			sum += (cardAt(i).pointValue());
		}
		if (sum == 11) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Check for a JQK in the selected cards.
	 * @param selectedCards selects a subset of this board.  It is list
	 *                      of indexes into this board that are searched
	 *                      to find a JQK group.
	 * @return true if the board entries in selectedCards
	 *              include a jack, a queen, and a king; false otherwise.
	 */
	private boolean containsJQK(List<Integer> selectedCards) {
		int sum = 0;
		for (int i : selectedCards) {
			sum += (cardAt(i).pointValue());
		}
		if (sum == 11) {
			return true;
		} else {
			return false;
		}
	}
}
