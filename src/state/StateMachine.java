package state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * 状态机
 * 
 * @author BaoZi
 * 
 * @param <T>
 */
public class StateMachine<T> {

	private int sceneStep;
	private int enemys;
	private GameState<T> currentState;
	private GameState<T> lastState;
	private final List<StateTransaction<T>> TRANSACTION_LIST_HOLDER = new ArrayList<StateTransaction<T>>();
	private Map<Integer, GameState<T>> allStates = new HashMap<Integer, GameState<T>>();
	private Map<GameState<T>, List<StateTransaction<T>>> transactionStates = new HashMap<GameState<T>, List<StateTransaction<T>>>();
	private IStateInput<T> stateInput;
	private IStateChange<T> stateChange;

	public StateMachine(IStateInput<T> stateInput, IStateChange<T> stateChange) {
		if (stateInput == null) {
			stateInput = new IStateInput<T>() {
				@Override
				public void input(StateMachine<T> machine,
						GameState<T> currentState, T t) {

				}
			};
		}

		this.stateInput = stateInput;

		if (stateChange == null) {
			stateChange = new IStateChange<T>() {
				@Override
				public void process(StateMachine<T> machine, T t,
						GameState<T> oldState, GameState<T> currentState) {
				}
			};
		}

		this.stateChange = stateChange;
	}

	/**
	 * 设置默认状态
	 * 
	 * @param state
	 */
	public void setDefaultDate(GameState<T> state) {
		if (state == null) {
			throw new NullPointerException(" Default state can not be null.");
		}
		currentState = state;
	}

	public void setDefaultState(int stateType) {
		GameState<T> state = allStates.get(stateType);
		if (state == null) {
			throw new NullPointerException("Can not found such state.");
		}
		setDefaultDate(state);
	}

	/**
	 * 输入状态
	 * 
	 * @param currentState
	 * @param t
	 */
	public void processCurrentState(GameState<T> currentState, T t) {
		stateInput.input(this, currentState, t);
	}

	/**
	 * 检查状态
	 */
	public void checkCurrentState() {
		GameState<T> state = null;
		List<StateTransaction<T>> list = transactionStates.get(currentState);
		for (StateTransaction<T> st : list) {
			if (st.check(sceneStep, enemys)) {
				state = st.getTransactionState();
				break;
			}
		}

		if (state != null && state != currentState) {
			currentState = state;
		}
	}

	/**
	 * 添加状态
	 * 
	 * @param stateType
	 * @return
	 */
	public GameState<T> addState(int stateType) {
		GameState<T> state = allStates.get(stateType);
		if (state == null) {
			state = new GameState<T>(stateType);
			allStates.put(stateType, state);
			if (transactionStates.get(state) == null) {
				transactionStates.put(state, TRANSACTION_LIST_HOLDER);
			}
		}
		return state;
	}

	/**
	 * 为两个状态添加关联。如果两个关联的状态存在，则返回此关联
	 * 
	 * @param src
	 * @param dst
	 * @return
	 */
	public StateTransaction<T> addTranscation(GameState<T> src, GameState<T> dst) {
		List<StateTransaction<T>> list = transactionStates.get(src);
		boolean checkContains = true;
		if (list == TRANSACTION_LIST_HOLDER) {
			list = new LinkedList<StateTransaction<T>>();
			transactionStates.put(src, list);
			checkContains = false;
		}
		StateTransaction<T> transactionState = null;
		if (checkContains) {
			for (StateTransaction<T> st : list) {
				if (st.getTransactionState() == dst) {
					transactionState = st;
					break;
				}
			}
		}
		if (transactionState == null) {
			transactionState = new StateTransaction<T>(dst);
			list.add(transactionState);
		}

		return transactionState;
	}

	public void tick(T t) {
		if (lastState != currentState) {
			currentState.onInit(this, t);
			stateChange.process(this, t, lastState, currentState);
			lastState = currentState;
		}
		currentState.doStateAction(this, t);
		try {
			this.processCurrentState(currentState, t);
		} catch (Exception e) {
			e.printStackTrace();
		}
		checkCurrentState();
	}

	/**
	 * @return GameState<T> currentState
	 */
	public GameState<T> getCurrentState() {
		return currentState;
	}

	/**
	 * @param sceneStep
	 */
	public void setSceneStep(int sceneStep) {
		this.sceneStep = sceneStep;
	}

	/**
	 * @param enemys
	 */
	public void setEnemys(int enemys) {
		this.enemys = enemys;
	}

}
