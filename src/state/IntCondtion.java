package state;

/**
 * 
 * @author BaoZi
 * 
 */
public class IntCondtion implements ITransactionCondtion {
	
	private int step;

	public IntCondtion(int step) {
		this.step = step;
	}

	@Override
	public boolean check(int playerState, int enemys) {
		if (playerState == step) {
			return true;
		}
		return false;
	}

}
