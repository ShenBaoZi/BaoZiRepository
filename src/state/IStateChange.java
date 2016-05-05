package state;

/**
 * 状态改变时的通知 Interface StateChange
 * 
 * @author BaoZi
 * 
 * @param <T>
 */
public interface IStateChange<T> {
	void process(StateMachine<T> machine, T t, GameState<T> oldState,
			GameState<T> currentState);
}
