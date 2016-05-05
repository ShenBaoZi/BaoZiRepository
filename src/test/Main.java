package test;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;

import moudle.GameWorld;

/**
 * 
 * @author BaoZi
 * 
 */
public class Main extends Frame {

	private static final long serialVersionUID = 1;

	public Main() {
		setTitle("HuntMonster");
		setSize(720, 748);
		setResizable(false);
		setLocationRelativeTo(this);
		setVisible(true);

		add(GameWorld.getInstance());
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public static void main(String args[]) throws InvocationTargetException,
			InterruptedException {
		java.awt.EventQueue.invokeAndWait(new Runnable() {
			public void run() {
				new Main();
			}
		});
	}
}
