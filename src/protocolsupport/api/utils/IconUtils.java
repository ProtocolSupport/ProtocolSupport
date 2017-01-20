package protocolsupport.api.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.Validate;
import org.bukkit.util.CachedServerIcon;

import protocolsupport.zplatform.ServerImplementationType;

public class IconUtils {

	public static String loadIcon(File file) throws IOException {
		return loadIcon(new FileInputStream(file));
	}

	public static String loadIcon(InputStream rawStream) throws IOException {
		return loadIcon(ImageIO.read(rawStream));
	}

	public static String loadIcon(BufferedImage image) throws IOException {
        Validate.isTrue(image.getWidth() == 64, "Must be 64 pixels wide");
        Validate.isTrue(image.getHeight() == 64, "Must be 64 pixels high");
		ByteArrayOutputStream data = new ByteArrayOutputStream();
		ImageIO.write(image, "PNG", data);
		return "data:image/png;base64," + Base64.encodeBase64String(data.toByteArray());
	}

	public static String fromBukkit(CachedServerIcon icon) {
		return ServerImplementationType.get().getMiscUtils().convertBukkitIconToBase64(icon);
	}

}
