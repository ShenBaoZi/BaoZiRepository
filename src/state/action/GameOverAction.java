package state.action;

import java.awt.Font;
import java.awt.Graphics;

import moudle.GameWorld;
import music.MusicManager;
import music.MusicType;
import scene.SceneManager;
import state.IStateAction;
import state.StateMachine;
import util.Common;

/**
 * ”Œœ∑Ω· ¯
 * 
 * @author BaoZi
 * 
 */
public class GameOverAction implements IStateAction<Graphics> {
	
	private long transactionTime;

	@Override
	public void execute(StateMachine<Graphics> machine, Graphics g) {
		if (GameWorld.getInstance().getGameTime() - this.transactionTime >= Common.STAGE_WAIT_TIME) {
			SceneManager.getInstance().reset();
		}
	}

	@Override
	public void onInit(StateMachine<Graphics> machine, Graphics g) {
		g.setFont(new Font(Common.FONT, Common.FONT_STYLE, Common.FONT_SIZE));
		g.setColor(Common.FONT_COLOR);
		g.drawString("Game over.", 300, 360);
		this.transactionTime = GameWorld.getInstance().getGameTime();
		MusicManager.getInstance().playerMusic(MusicType.FAILE);
	}

}
