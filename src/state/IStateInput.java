package state;

/**
 * ×´Ì¬ÊäÈë Interface StateInput
 * 
 * @author BaoZi
 * 
 * @param <T>
 */
public interface IStateInput<T> {
	void input(StateMachine<T> machine, GameState<T> currentState, T t);
}
