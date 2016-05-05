package util;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * 
 * @author BaoZi
 * 
 */
public class Util {

	private static Util instance;

	private Util() {

	}

	/**
	 * 
	 * @return Util instance
	 */
	public static Util getInstance() {
		if (instance == null) {
			instance = new Util();
		}
		return instance;
	}

	/**
	 * 
	 * @param fileName
	 * @return image
	 */
	public Image createImage(String fileName) {
		ImageIcon icon = new ImageIcon(getClass().getClassLoader()
				.getSystemResource("img/" + fileName));
		return icon.getImage();
	}
}
