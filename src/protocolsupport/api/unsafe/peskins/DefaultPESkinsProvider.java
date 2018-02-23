package protocolsupport.api.unsafe.peskins;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

import javax.imageio.ImageIO;

import protocolsupport.utils.Utils;

public class DefaultPESkinsProvider extends PESkinsProvider {

	public static final DefaultPESkinsProvider INSTANCE = new DefaultPESkinsProvider();

	public static final byte[] DEFAULT_STEVE = new Callable<byte[]>() {
		@Override
		public byte[] call() {
			try {
				return toData(ImageIO.read(Utils.getResourceAsStream("pe/steve_skin.png")));
			} catch (IOException e) {
				return new byte[0];
			}
		}
	}.call();

	@Override
	public byte[] getSkinData(String url) {
		return DEFAULT_STEVE;
	}

	@Override
	public void scheduleGetSkinData(String url, Consumer<byte[]> skindataApplyCallback) {
	}

}
