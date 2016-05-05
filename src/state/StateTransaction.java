package state;

import java.util.LinkedList;
import java.util.List;

/**
 * ¹ØÁª×´Ì¬
 * 
 * @author BaoZi
 * 
 * @param <T>
 */
public class StateTransaction<T> {
	private List<ITransactionCondtion> list = new LinkedList<ITransactionCondtion>();
	private final GameState<T> transactionState;

	public StateTransaction(GameState<T> transactionState) {
		this.transactionState = transactionState;
	}

	public void addCondition(ITransactionCondtion condtion) {
		list.add(condtion);
	}

	public boolean check(int playerState, int enemys) {
		for (ITransactionCondtion c : list) {
			if (c.check(playerState, enemys)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return GameState<T> transactionState
	 */
	public GameState<T> getTransactionState() {
		return transactionState;
	}

}
