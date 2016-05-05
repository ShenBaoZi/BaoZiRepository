package moudle;

import java.awt.Graphics;
import java.awt.Image;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import scene.SceneManager;
import util.Util;
import action.GameMapDrawer;
import core.GameObject;

/**
 * 游戏关卡
 * 
 * @author BaoZi
 * 
 */
public class GameMission extends GameObject {
	public static final int row = 15;
	public static final int col = 15;
	public static final int imageSize = 48;
	/**
	 * 关卡地图
	 */
	private Map<Integer, GameMap> maps = new HashMap<Integer, GameMap>();
	/**
	 * 敌人出生点
	 */
	private Map<Integer, int[][]> enemyPoint = new HashMap<Integer, int[][]>();
	/**
	 * 敌人位置
	 */
	private Map<Integer, Map<Integer, GameEnemy>> enemys = new HashMap<Integer, Map<Integer, GameEnemy>>();
	private int currentState = 1;
	private boolean stop = true;

	private final int[][] stage1 = {
			{ 1, 1, 2, 1, 2, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1 },
			{ 1, 1, 0, 0, 0, 0, 2, 0, 0, 2, 0, 1, 0, 0, 1 },
			{ 1, 1, 2, 2, 2, 2, 2, 0, 0, 0, 0, 1, 2, 0, 1 },
			{ 1, 1, 0, 2, 0, 0, 2, 2, 0, 2, 0, 1, 2, 0, 1 },
			{ 1, 1, 2, 0, 0, 1, 2, 1, 1, 1, 0, 1, 2, 0, 1 },
			{ 1, 0, 1, 0, 0, 1, 2, 0, 2, 1, 0, 1, 2, 0, 1 },
			{ 1, 0, 1, 0, 1, 1, 2, 1, 0, 1, 0, 0, 2, 0, 1 },
			{ 2, 0, 0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 2, 0, 1 },
			{ 2, 0, 2, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1 },
			{ 2, 0, 0, 1, 1, 0, 0, 1, 2, 0, 0, 0, 0, 2, 1 },
			{ 1, 0, 2, 0, 2, 2, 0, 2, 2, 0, 2, 0, 2, 2, 1 },
			{ 0, 2, 2, 2, 1, 0, 0, 1, 2, 2, 2, 0, 2, 2, 1 },
			{ 0, 0, 1, 0, 0, 2, 0, 1, 0, 0, 2, 0, 0, 0, 1 },
			{ 1, 0, 1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1 } };

	private Image stage1_swardImage = Util.getInstance().createImage(
			"sward.png");
	private Image stage1_rockImage = Util.getInstance().createImage("rock.png");
	private Image stage1_blockImage = Util.getInstance().createImage(
			"block.png");

	public GameMission() {
		super(GameWorld.getInstance().getId(), null, 0, 0, new GameMapDrawer());
		createStage();
	}

	private void createStage() {

		GameMap stage1Map = new GameMap(1, 0, 0, row, col, imageSize, stage1,
				stage1_swardImage, stage1_rockImage, stage1_blockImage, drawer);
		maps.put(1, stage1Map);
		int[][] stage1EnemyPoint = new int[1][2];
		stage1EnemyPoint[0][0] = 10;
		stage1EnemyPoint[0][1] = 10;
		enemyPoint.put(1, stage1EnemyPoint);

		Map<Integer, GameEnemy> es = new HashMap<Integer, GameEnemy>();
		for (int index = 0; index < stage1EnemyPoint.length; index++) {
			GameEnemy gameEnemy = new GameEnemy(stage1EnemyPoint[index][0],
					stage1EnemyPoint[index][1]);
			es.put(gameEnemy.id, gameEnemy);
		}

		enemys.put(1, es);
	}

	public GameMap getCurrentMap() {
		return maps.get(1);
	}

	public void reset() {
		this.maps.clear();
		this.enemys.clear();
		this.createStage();
	}

	public void nextStage() {
		this.stop = true;
		this.currentState++;
	}

	public Iterator<GameEnemy> getCurrentEnems() {
		return enemys.get(1).values().iterator();
	}

	public boolean isEnemyDeadAll() {
		int size = enemys.get(1).size();
		int temp = 0;
		Iterator<GameEnemy> currentEnems = getCurrentEnems();
		while (currentEnems.hasNext()) {
			if (currentEnems.next().isStateDaed()) {
				temp++;
			}
		}
		if (temp == size)
			return true;
		return false;
	}

	public void destoryEnemy(int id) {
		enemys.get(1).remove(id);
	}

	public boolean isPause() {
		return stop;
	}

	public void run() {
		this.stop = false;
	}

	/**
	 * @return currentState
	 */
	public int getCurrentState() {
		return currentState - 1;
	}

	@Override
	public void draw(Graphics g) {
		getCurrentMap().draw(g);
	}

	@Override
	public void tick(Graphics g) {
		if (!isPause()) {
			draw(g);
			SceneManager.getInstance().getPlayer().tick(g);
		}
	}

}
