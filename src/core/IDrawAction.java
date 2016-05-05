package core;

import java.awt.Graphics;
import java.awt.Image;

/**
 * Interface DrawAction
 * 
 * @author BaoZi
 * 
 */
public interface IDrawAction {
	void draw(Graphics g, Image image, int x, int y);

	void draw(Graphics g, Image img, int dx1, int dy1, int dx2, int dy2,
			int sx1, int sy1, int sx2, int sy2);
}
