package protocolsupport.protocol.transformer.mcpe.packet;

import net.minecraft.server.v1_9_R1.MinecraftServer;
import net.minecraft.server.v1_9_R1.PacketListener;

public abstract class SynchronizedHandleNMSPacket<T extends PacketListener> extends HandleNMSPacket<T> {

	@SuppressWarnings("deprecation")
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
