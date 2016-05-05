package state.action;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.logging.Logger;

import moudle.GameEnemy;
import moudle.GameWorld;
import music.MusicManager;
import music.MusicType;
import scene.SceneManager;
import state.IStateAction;
import state.StateMachine;
import util.Common;

/**
 * 下一个怪兽
 * 
 * @author BaoZi
 * 
 */
public class NextStageAction implements IStateAction<Graphics> {

	private long transactionTime;

	@Override
	public void execute(StateMachine<Graphics> machine, Graphics t) {
		if (GameWorld.getInstance().getGameTime() - this.transactionTime >= Common.STAGE_WAIT_TIME) {
			SceneManager.getInstance().setSceneStep(SceneManager.SCENE_MAP);
			
			for (Iterator<GameEnemy> currentEnems = SceneManager.getInstance()
					.getGamemission().getCurrentEnems(); currentEnems.hasNext();) {
				GameEnemy ge = currentEnems.next();
				ge.setStateRun();
			}
		}
	}

	@Override
	public void onInit(StateMachine<Graphics> machine, Graphics g) {
		SceneManager.getInstance().getGamemission().nextStage();
		g.setFont(new Font(Common.FONT, Common.FONT_STYLE, Common.FONT_SIZE));
		g.setColor(Common.FONT_COLOR);
		g.drawString("怪兽即将复活！", 280, 360);
		this.transactionTime = GameWorld.getInstance().getGameTime();
		MusicManager.getInstance().playerMusic(MusicType.WIN);
	}

}
