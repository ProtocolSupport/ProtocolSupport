package protocolsupport.api.unsafe.peskins;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.function.Consumer;

import org.apache.commons.lang3.Validate;

public abstract class PESkinsProvider {

	/**
	 * This method receives url to skin, and should return image data, or null to indicate that skin data retrieve and apply should be scheduled
	 * This method shouldn't block, or do heavy processing because getting skin happens when transforming packets
	 * This method may return cached results, or skin that doesn't match the url
	 * Returned data is used as-is (without cloning), so it shouldn't be modified anywhere
	 * @param url url to skin image
	 * @return skin data or null, or null to indicate that skin data retrieve and apply should be scheduled
	 */
	public abstract byte[] getSkinData(String url);

	/**
	 * This method receives url to skin and should should execute the skindataApplyCallback as soon as it get's the skin data from url
	 * This method should schedule calling the skindataApplyCallback even if for whatever reason it can run it right now
	 * This method can actually skip calling the skindataApplyCallback if it can't get the skin data for whatever reason
	 * @param url url to skin image
	 * @param skindataApplyCallback callback that should be called when receiving skin data completes
	 */
	public abstract void scheduleGetSkinData(String url, Consumer<byte[]> skindataApplyCallback);

	protected static byte[] toData(BufferedImage skin) {
		Validate.isTrue(skin.getWidth() == 64, "Must be 64 pixels wide");
		Validate.isTrue((skin.getHeight() == 64) || (skin.getHeight() == 32), "Must be 64 or 32 pixels high");
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
