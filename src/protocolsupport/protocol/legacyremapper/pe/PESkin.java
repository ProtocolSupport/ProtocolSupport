package protocolsupport.protocol.legacyremapper.pe;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.Callable;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.Validate;

import protocolsupport.utils.Utils;

public class PESkin {

	public static void init() {
	}

	public static final byte[] STEVE = new Callable<byte[]>() {
		@Override
		public byte[] call() {
			try {
				return toNetworkData(ImageIO.read(Utils.getResource("steve_skin.png")));
			} catch (IOException e) {
				return new byte[0];
			}
		}
	}.call();

	public static byte[] toNetworkData(BufferedImage skin) {
		Validate.isTrue(skin.getWidth() == 64, "Must be 64 pixels wide");
		Validate.isTrue(skin.getHeight() == 64, "Must be 64 pixels high");
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		for (int y = 0; y < skin.getHeight(); y++) {
			for (int x = 0; x < skin.getWidth(); x++) {
                Color color = new Color(skin.getRGB(x, y), true);
                stream.write(color.getRed());
                stream.write(color.getGreen());
                stream.write(color.getBlue());
                stream.write(color.getAlpha());
			}
		}
		return stream.toByteArray();
	}

}
