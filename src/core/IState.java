package core;

import java.awt.Graphics;

/**
 * Interface State
 * 
 * @author BaoZi
 * 
 */
public interface IState {

	void createStartScene(Graphics g);

	void createMap(Graphics g);

	void startGame(Graphics g);
}
