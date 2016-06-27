package protocolsupport.protocol.pipeline.wrapped;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.ClosedChannelException;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;

import com.mojang.authlib.GameProfile;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.timeout.ReadTimeoutException;
import net.minecraft.server.v1_10_R1.NetworkManager;
import net.minecraft.server.v1_10_R1.PacketListener;
import net.minecraft.server.v1_10_R1.PlayerConnection;
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.events.PlayerDisconnectEvent;
import protocolsupport.logger.AsyncErrorLogger;
import protocolsupport.protocol.packet.handler.AbstractLoginListener;
import protocolsupport.protocol.pipeline.IPacketDecoder;
import protocolsupport.protocol.storage.ProtocolStorage;
import protocolsupport.utils.netty.ChannelUtils;

public class WrappedDecoder extends ByteToMessageDecoder {

	private IPacketDecoder realDecoder = new IPacketDecoder() {
		@Override
		public void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
		}
	};

	public void setRealDecoder(IPacketDecoder realDecoder) {
		this.realDecoder = realDecoder;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
		realDecoder.decode(ctx, input, list);
	}

	private static final HashMap<Class<? extends Throwable>, String> ignoreExceptions = new HashMap<>();
	static {
		ignoreExceptions.put(ClosedChannelException.class, "");
		ignoreExceptions.put(ReadTimeoutException.class, "");
		ignoreExceptions.put(IOException.class, "Connection reset by peer");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
		super.exceptionCaught(ctx, e);
		String ignoreMSG = ignoreExceptions.get(e.getClass());
		if (ignoreMSG == null || !ignoreMSG.equals(e.getMessage())) {
			SocketAddress remoteaddr = ctx.channel().remoteAddress();
			AsyncErrorLogger.INSTANCE.log(e, remoteaddr, ProtocolSupportAPI.getProtocolVersion(remoteaddr));
		}
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		NetworkManager networkManager = ChannelUtils.getNetworkManager(ctx.channel());
		InetSocketAddress addr = (InetSocketAddress) networkManager.getSocketAddress();
		String username = null;
		PacketListener listener = networkManager.i();
		if (listener instanceof AbstractLoginListener) {
			GameProfile profile = ((AbstractLoginListener) listener).getProfile();
			if (profile != null) {
				username = profile.getName();
			}
		} else if (listener instanceof PlayerConnection) {
			username = ((PlayerConnection) listener).player.getProfile().getName();
		}
		if (username != null) {
			PlayerDisconnectEvent event = new PlayerDisconnectEvent(addr, username);
			Bukkit.getPluginManager().callEvent(event);
		}
		ProtocolStorage.clearData(addr);
	}

}
