package state;

/**
 * 
 * @author BaoZi
 * 
 */
public interface ITransactionCondtion {
	boolean check(int playerState, int enemys);
}
