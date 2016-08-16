package protocolsupport.protocol.pipeline.wrapped;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.ClosedChannelException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

	private static final HashMap<Class<? extends Throwable>, Set<?>> ignoreExceptions = new HashMap<>();
	static {
		ignoreExceptions.put(ClosedChannelException.class, Collections.emptySet());
		ignoreExceptions.put(ReadTimeoutException.class, Collections.emptySet());
		ignoreExceptions.put(IOException.class, new HashSet<String>(Arrays.asList("Connection reset by peer", "Broken pipe")));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
		super.exceptionCaught(ctx, e);
		Set<?> ignore = ignoreExceptions.get(e.getClass());
		if (ignore == null || (!ignore.isEmpty() && !ignore.contains(e.getMessage()))) {
			SocketAddress remoteaddr = ChannelUtils.getNetworkManagerSocketAddress(ctx.channel());
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
