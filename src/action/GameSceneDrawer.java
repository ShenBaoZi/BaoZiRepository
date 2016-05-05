package action;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import util.Common;
import core.IDrawAction;

/**
 * 初始场景
 * 
 * @author BaoZi
 * 
 */
public class GameSceneDrawer implements IDrawAction {

	@Override
	public void draw(Graphics g, Image image, int x, int y) {
		g.drawImage(image, x, y, null);
		
		g.setColor(Common.FONT_COLOR);
		g.setFont(new Font(Common.FONT, Common.FONT_STYLE, Common.FONT_SIZE * 2));
		g.drawString("追捕怪兽", 220, 250);
		g.setFont(new Font(Common.FONT, Common.FONT_STYLE, Common.FONT_SIZE));
		g.drawString("按【方向】移动", 240, 550);
		g.drawString("按【空格】攻击", 240, 600);
		g.drawString("按【回车】开始游戏", 210, 650);
	}

	@Override
	public void draw(Graphics g, Image img, int dx1, int dy1, int dx2, int dy2,
			int sx1, int sy1, int sx2, int sy2) {

	}
}
