package music;

import java.io.BufferedInputStream;

import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * “Ù¿÷¿‡
 * 
 * @author BaoZi
 * 
 */
public class Music {
	private MusicType type;
	private String filePath;
	private InputStream inputStream;
	private boolean running;
	private final int byteChunkSize = 1024;
	private byte[] muteData;

	public Music(MusicType type, String filePath) {
		this.type = type;
		running = false;
		muteData = setMuteData();
		this.filePath = filePath;
	}

	public void playMusic() {
		try {
			inputStream = this.getClass().getResourceAsStream(this.filePath);
			inputStream = new BufferedInputStream(inputStream);
			AudioInputStream in = AudioSystem.getAudioInputStream(inputStream);

			AudioInputStream din = null;
			AudioFormat baseFormat = in.getFormat();
			AudioFormat decodedFormat = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED,
					baseFormat.getSampleRate(), 16, baseFormat.getChannels(),
					baseFormat.getChannels() * 2, baseFormat.getSampleRate(),
					false);
			din = AudioSystem.getAudioInputStream(decodedFormat, in);
			stream(decodedFormat, din);
			in.close();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void stream(AudioFormat targetFormat, AudioInputStream din) {
		try {
			byte[] data = new byte[byteChunkSize];
			SourceDataLine line = getLine(targetFormat);
			if (line != null) {
				line.start();
				int nBytesRead = 0;
				while (nBytesRead != -1) {
					nBytesRead = din.read(data, 0, data.length);
					if (nBytesRead != -1) {
						line.write(data, 0, nBytesRead);
					}
				}
				line.drain();
				line.stop();
				line.close();
				din.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private SourceDataLine getLine(AudioFormat audioFormat) {
		SourceDataLine res = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class,
				audioFormat);
		try {
			res = (SourceDataLine) AudioSystem.getLine(info);
			res.open(audioFormat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public boolean isPlaying() {
		return running;
	}

	public void stop() {
		this.running = false;
	}

	public void play() {
		this.running = true;
	}

	private byte[] setMuteData() {
		byte[] x = new byte[byteChunkSize];
		for (int i = 0; i < x.length; i++) {
			x[i] = 0;
		}

		return x;
	}

	/**
	 * @return byte[] muteData
	 */
	public byte[] getMuteData() {
		return muteData;
	}

	/**
	 * @return int byteChunkSize
	 */
	public int getByteChunkSize() {
		return byteChunkSize;
	}

	/**
	 * @return MusicType type
	 */
	public MusicType getType() {
		return type;
	}

}