package scene;

import java.awt.Graphics;

import moudle.GameMission;
import moudle.GamePlayer;
import state.GameState;
import state.IStateChange;
import state.IStateInput;
import state.IntCondtion;
import state.StateMachine;
import state.StateTransaction;
import state.action.GameLoadAction;
import state.action.GameOverAction;
import state.action.GameStartAction;
import state.action.NextStageAction;

/**
 * 场景管理，设置场景事件与场景关联
 * 
 * @author BaoZi
 * 
 */
public class SceneManager {

	private static SceneManager instance = null;
	public static final int SCENE_START = 0;
	public static final int SCENE_MAP = 1;
	public static final int SCENE_NEXT_STAGE = 2;
	public static final int SCENE_GAME_OVER = 3;
	private static int sceneStep;
	private StateMachine<Graphics> stateMachine;
	private GameMission gamemission;
	private GameStartScene starScene;
	private GamePlayer player;

	private SceneManager() {
		
		sceneStep = SCENE_START;
		stateMachine = new StateMachine<Graphics>(createIstateInput(),
				createIStateChange());
		starScene = new GameStartScene(0, 0);
		gamemission = new GameMission();
		player = new GamePlayer(4, 4);

		GameState<Graphics> sceneState = stateMachine.addState(SCENE_START);
		sceneState.setStateAction(new GameLoadAction());
		
		GameState<Graphics> mapState = stateMachine.addState(SCENE_MAP);
		mapState.setStateAction(new GameStartAction());

		GameState<Graphics> nextStageState = stateMachine
				.addState(SCENE_NEXT_STAGE);
		nextStageState.setStateAction(new NextStageAction());

		GameState<Graphics> gameOverState = stateMachine
				.addState(SCENE_GAME_OVER);
		gameOverState.setStateAction(new GameOverAction());

		StateTransaction<Graphics> start2MapTranscation = stateMachine
				.addTranscation(sceneState, mapState);
		start2MapTranscation.addCondition(new IntCondtion(SCENE_MAP));

		StateTransaction<Graphics> win2NextStageTranscation = stateMachine
				.addTranscation(mapState, nextStageState);
		win2NextStageTranscation
				.addCondition(new IntCondtion(SCENE_NEXT_STAGE));

		StateTransaction<Graphics> next2MapTranscation = stateMachine
				.addTranscation(nextStageState, mapState);
		next2MapTranscation.addCondition(new IntCondtion(SCENE_MAP));

		StateTransaction<Graphics> map2GameOverTranscation = stateMachine
				.addTranscation(mapState, gameOverState);
		map2GameOverTranscation.addCondition(new IntCondtion(SCENE_GAME_OVER));

		StateTransaction<Graphics> over2StartSceneTranscation = stateMachine
				.addTranscation(gameOverState, sceneState);
		over2StartSceneTranscation.addCondition(new IntCondtion(SCENE_START));

		stateMachine.setDefaultDate(sceneState);

	}

	public static SceneManager getInstance() {
		if (instance == null) {
			instance = new SceneManager();
		}
		return instance;
	}

	public void reset() {
		instance = null;
	}

	public void tick(Graphics g) {
		stateMachine.tick(g);
	}

	public GameState<Graphics> getCurrentState() {
		return stateMachine.getCurrentState();
	}

	/**
	 * 
	 * @return player
	 */
	public GamePlayer getPlayer() {
		return player;
	}

	/**
	 * 
	 * @param player
	 */
	public void setPlayer(GamePlayer player) {
		this.player = player;
	}

	/**
	 * 
	 * @return gamemission
	 */
	public GameMission getGamemission() {
		return gamemission;
	}

	/**
	 * 
	 * @return starScene
	 */
	public GameStartScene getStarScene() {
		return starScene;
	}

	/**
	 * 
	 * @param sceneStep
	 */
	public void setSceneStep(int sceneStep) {
		SceneManager.sceneStep = sceneStep;
	}

	private static IStateChange<Graphics> createIStateChange() {
		return null;
	}

	private static IStateInput<Graphics> createIstateInput() {
		return new IStateInput<Graphics>() {
			@Override
			public void input(StateMachine<Graphics> machine,
					GameState<Graphics> currentState, Graphics t) {
				machine.setSceneStep(sceneStep);
			}
		};
	}
}
