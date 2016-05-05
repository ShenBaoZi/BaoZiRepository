package state.action;

import java.awt.Graphics;

import music.MusicManager;
import music.MusicType;
import scene.SceneManager;
import state.IStateAction;
import state.StateMachine;

/**
 * ∂¡»°”Œœ∑
 * 
 * @author BaoZi
 * 
 */
public class GameLoadAction implements IStateAction<Graphics> {

	@Override
	public void execute(StateMachine<Graphics> machine, Graphics g) {
		SceneManager.getInstance().getStarScene().draw(g);
	}

	@Override
	public void onInit(StateMachine<Graphics> machine, Graphics g) {
		MusicManager.getInstance().playerMusic(MusicType.BACK_GROUND);
	}
}
