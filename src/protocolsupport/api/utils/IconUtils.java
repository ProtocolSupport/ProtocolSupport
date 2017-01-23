package protocolsupport.api.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.bukkit.util.CachedServerIcon;

import protocolsupport.utils.ApacheCommonsUtils;
import protocolsupport.zplatform.ServerPlatform;

public class IconUtils {

	public static String loadIcon(File file) throws IOException {
		return loadIcon(new FileInputStream(file));
	}

	public static String loadIcon(InputStream rawStream) throws IOException {
		return loadIcon(ImageIO.read(rawStream));
	}

	public static String loadIcon(BufferedImage image) throws IOException {
		ApacheCommonsUtils.isTrue(image.getWidth() == 64, "Must be 64 pixels wide");
		ApacheCommonsUtils.isTrue(image.getHeight() == 64, "Must be 64 pixels high");
		ByteArrayOutputStream data = new ByteArrayOutputStream();
		ImageIO.write(image, "PNG", data);
		return "data:image/png;base64," + Base64.getEncoder().encodeToString(data.toByteArray());
	}

	public static String fromBukkit(CachedServerIcon icon) {
		return ServerPlatform.get().getMiscUtils().convertBukkitIconToBase64(icon);
	}

}
