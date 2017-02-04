package protocolsupport.zplatform.impl.spigot;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import net.minecraft.server.v1_11_R1.CancelledPacketHandleException;
import net.minecraft.server.v1_11_R1.Packet;
import net.minecraft.server.v1_11_R1.PacketListener;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.zplatform.impl.spigot.network.SpigotChannelHandlers;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class SpigotConnectionImpl extends ConnectionImpl {

	public SpigotConnectionImpl(NetworkManagerWrapper networkmanager) {
		super(networkmanager);
	}

	@Override
	public void receivePacket(Object packet) {
		@SuppressWarnings("unchecked")
		final Packet<PacketListener> packetInst = (Packet<PacketListener>) packet;
		if (networkmanager.getChannel().isOpen()) {
			try {
				packetInst.a((PacketListener) networkmanager.getPacketListener());
			} catch (CancelledPacketHandleException ex) {
			}
		}
	}

	@Override
	public void sendPacket(Object packet) {
		@SuppressWarnings("unchecked")
		final Packet<PacketListener> packetInst = (Packet<PacketListener>) packet;
		Runnable packetSend = () -> {
			try {
				ChannelHandlerContext encoderContext = networkmanager.getChannel().pipeline().context(SpigotChannelHandlers.ENCODER);
				ChannelOutboundHandler encoderChannelHandler = (ChannelOutboundHandler) encoderContext.handler();
				encoderChannelHandler.write(encoderContext, packetInst, encoderContext.voidPromise());
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
		if (networkmanager.getChannel().eventLoop().inEventLoop()) {
			packetSend.run();
		} else {
			networkmanager.getChannel().eventLoop().submit(packetSend);
		}
	}

}
