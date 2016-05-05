package moudle;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import core.GameObject;
import core.IDrawAction;

/**
 * ÎäÆ÷»ùÀà
 * 
 * @author BaoZi
 * 
 */
public class GameWeapon extends GameObject {
	private int scope;
	private long setTime;
	private Image boomImage;
	private ArrayList<Integer[]> boomScope;
	private int status;
	private final int CREATE = 0;
	private final int SET = 1;
	private final int BOOM = 2;
	private final int DEAD = 3;

	public GameWeapon(int id, Image image, int x, int y, IDrawAction drawer) {
		super(id, image, x, y, drawer);
	}

	public void setBomb(long time, int x, int y) {
		
	}

	public void clearBoomScope() {
		
	}

	public int getScope() {
		return scope;
	}

	public void addScopeLevel() {
		this.scope++;
	}

	@Override
	public void draw(Graphics g) {

	}

	public boolean isBobmSetStatus() {
		return this.status == SET;
	}

	public boolean isBombCreateStatus() {
		return this.status == CREATE;
	}

	public boolean isBombDeadState() {
		return this.status == DEAD;
	}

	public boolean isAllowMove(int x, int y) {
		if (x == this.x && this.y == y)
			return true;
		return false;
	}

	@Override
	public void tick(Graphics g) {
		
	}

}
