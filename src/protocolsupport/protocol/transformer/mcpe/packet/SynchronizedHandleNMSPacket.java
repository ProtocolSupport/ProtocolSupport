package protocolsupport.protocol.transformer.mcpe.packet;

import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.PacketListener;

public abstract class SynchronizedHandleNMSPacket<T extends PacketListener> extends HandleNMSPacket<T> {

	@Override
	public void a(final T listener) {
		MinecraftServer.getServer().processQueue.add(new Runnable() {
			@Override
			public void run() {
				handle(listener);
			}
		});
	}

}
