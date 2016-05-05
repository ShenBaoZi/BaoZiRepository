package scene;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import moudle.GameWorld;
import music.MusicManager;
import music.MusicType;
import util.Util;
import action.GameSceneDrawer;
import core.GameObject;

/**
 * 
 * @author BaoZi
 * 
 */
public class GameStartScene extends GameObject {

	public GameStartScene(int x, int y) {
		super(GameWorld.getInstance().getId(), Util.getInstance().createImage(
				"start.png"), x, y, new GameSceneDrawer());
	}

	@Override
	public void keyPressed(KeyEvent event, Graphics g) {
		if (event.getKeyCode() == KeyEvent.VK_ENTER) {
			SceneManager.getInstance().setSceneStep(SceneManager.SCENE_MAP);
			MusicManager.getInstance().playerMusic(MusicType.READY);
		}
	}
}
