package state;

/**
 * Interface StateAction
 * 
 * @author BaoZi
 * 
 * @param <T>
 */
public interface IStateAction<T> {

	void execute(StateMachine<T> machine, T t);

	void onInit(StateMachine<T> machine, T t);
}
