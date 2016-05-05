package music;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import util.Common;

/**
 * “Ù¿÷π‹¿Ì¿‡
 * 
 * @author BaoZi
 * 
 */
public class MusicManager {
	
	private static MusicManager instance;
	private Thread musicThread;
	private static Map<MusicType, Music> musics = new HashMap<MusicType, Music>();

	private MusicManager() {
		musics.put(MusicType.BACK_GROUND, new Music(MusicType.BACK_GROUND,
				"/sound/back_sound.wav"));
		musics.put(MusicType.READY, new Music(MusicType.READY,
				"/sound/ready.wav"));
		musics.put(MusicType.BOOM, new Music(MusicType.BOOM, "/sound/boom.wav"));
		musics.put(MusicType.MOVE, new Music(MusicType.MOVE, "/sound/move.wav"));
		musics.put(MusicType.SET_BOMB, new Music(MusicType.SET_BOMB,
				"/sound/setbomb.wav"));
		musics.put(MusicType.WIN, new Music(MusicType.WIN, "/sound/win.wav"));
		musics.put(MusicType.FAILE,
				new Music(MusicType.FAILE, "/sound/faile.wav"));
		musics.put(MusicType.GET_ITEM, new Music(MusicType.GET_ITEM,
				"/sound/getItem.wav"));
		musics.put(MusicType.ENEMY_BOOM, new Music(MusicType.GET_ITEM,
				"/sound/enemy.wav"));
	}

	public static MusicManager getInstance() {
		if (instance == null) {
			instance = new MusicManager();
		}
		return instance;
	}

	public void run() {
		musicThread = new Thread(new Runnable() {
			@Override
			public void run() {
				
				while (true) {
					Iterator<Music> musicIterator = musics.values().iterator();
					while (musicIterator.hasNext()) {
						Music music = musicIterator.next();
						if (music.getType() != MusicType.BACK_GROUND) {
							if (music.isPlaying()) {
								music.playMusic();
								music.stop();
							}
						}
					}

					try {
						Thread.sleep(Common.THREAD_SLEEP_TIME);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		});

		musicThread.start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					musics.get(MusicType.BACK_GROUND).playMusic();
					
					try {
						Thread.sleep(Common.THREAD_SLEEP_TIME);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	public void playerMusic(MusicType type) {
		musics.get(type).play();
	}

	public void stopMusic(MusicType type) {
		musics.get(type).stop();
	}

}
