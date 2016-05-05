package core;

import java.awt.Graphics;
import java.awt.Image;

/**
 * Interface MoveAction
 * 
 * @author BaoZi
 * 
 */
public interface IMoveAction {
	void move(Graphics g, Image image, int x, int y);

	void move(Graphics g, Image img, int dx1, int dy1, int dx2, int dy2,
			int sx1, int sy1, int sx2, int sy2);
}
