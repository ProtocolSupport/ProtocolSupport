package protocolsupport.api.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.Validate;
import org.bukkit.util.CachedServerIcon;

import protocolsupport.zplatform.ServerPlatform;

public class IconUtils {

	/**
	 * Loads icon to base64 form from {@link File}
	 * @param file file from which base64 will be constructed
	 * @return base64 icon
	 * @throws IOException if new FileInputStream(file) throws
	 */
	public static String loadIcon(File file) throws IOException {
		return loadIcon(new FileInputStream(file));
	}

	/**
	 * Loads icon to base64 form from {@link InputStream}
	 * @param rawStream input stream from which base64 will be constructed
	 * @return base64 icon
	 * @throws IOException if ImageIO.read(rawStream) throws
	 */
	public static String loadIcon(InputStream rawStream) throws IOException {
		return loadIcon(ImageIO.read(rawStream));
	}

	/**
	 * Converts icon to base64 from {@link BufferedImage}
	 * @param image image from which base64 will be constructed
	 * @return base64 icon
	 * @throws IOException if can't write image data as png
	 * @throws IllegalArgumentException if image widget and height != 64x64 pixels
	 */
	public static String loadIcon(BufferedImage image) throws IOException {
		Validate.isTrue(image.getWidth() == 64, "Must be 64 pixels wide");
		Validate.isTrue(image.getHeight() == 64, "Must be 64 pixels high");
		ByteArrayOutputStream data = new ByteArrayOutputStream();
		ImageIO.write(image, "PNG", data);
		return "data:image/png;base64," + Base64.getEncoder().encodeToString(data.toByteArray());
	}

	/**
	 * Converts icon to base64 form from bukkit {@link CachedServerIcon}
	 * Throws exception if icon is not constructed by server implementation
	 * @param icon bukkit server icon
	 * @return base64 icon
	 */
	public static String fromBukkit(CachedServerIcon icon) {
		return ServerPlatform.get().getMiscUtils().convertBukkitIconToBase64(icon);
	}

}
