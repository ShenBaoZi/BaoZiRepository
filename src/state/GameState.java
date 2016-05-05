package state;

/**
 * ×´Ì¬Àà
 * 
 * @author BaoZi
 *
 * @param <T>
 */
public class GameState<T> {
	private final int state;
	private IStateAction<T> stateAction;
	
	public GameState(int state){
		this.state = state;
	}
	
	public void doStateAction(StateMachine<T> machine,T t){
		stateAction.execute(machine, t);
	}
	
	public void onInit(StateMachine<T> machine,T t){
		stateAction.onInit(machine, t);
	}

	/**
	 * 
	 * @return state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param IStateAction<T> stateAction
	 */
	public void setStateAction(IStateAction<T> stateAction) {
		this.stateAction = stateAction;
	}
	
	
}
