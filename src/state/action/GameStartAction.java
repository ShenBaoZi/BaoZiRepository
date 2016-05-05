package state.action;

import java.awt.Font;
import java.awt.Graphics;
import java.util.Iterator;

import moudle.GameEnemy;
import moudle.GameWorld;
import scene.SceneManager;
import state.IStateAction;
import state.StateMachine;
import util.Common;

/**
 * ÓÎÏ·¿ªÊ¼
 * 
 * @author BaoZi
 * 
 */
public class GameStartAction implements IStateAction<Graphics> {

	private long transactionTime;

	@Override
	public void execute(StateMachine<Graphics> machine, Graphics g) {
		if (GameWorld.getInstance().getGameTime() - this.transactionTime >= Common.STAGE_WAIT_TIME) {
			SceneManager.getInstance().getGamemission().run();
		}
		SceneManager.getInstance().getGamemission().tick(g);
	}

	@Override
	public void onInit(StateMachine<Graphics> machine, Graphics g) {
		SceneManager.getInstance().getGamemission().getCurrentMap().draw(g);
		SceneManager.getInstance().getPlayer().draw(g);
		for (Iterator<GameEnemy> currentEnems = SceneManager.getInstance()
				.getGamemission().getCurrentEnems(); currentEnems.hasNext();) {
			GameEnemy ge = currentEnems.next();
			if (ge.isStateDaed() || ge.isStateBoom()) {
				continue;
			}
			ge.tick(g);
		}
		g.setFont(new Font(Common.FONT, Common.FONT_STYLE, Common.FONT_SIZE));
		g.setColor(Common.FONT_COLOR);
		g.drawString(
				"Point: "
						+ SceneManager.getInstance().getGamemission()
								.getCurrentState(), 300, 360);
		this.transactionTime = GameWorld.getInstance().getGameTime();
	}

}
