package protocolsupport.protocol.pipeline.common;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.ClosedChannelException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;

import com.mojang.authlib.GameProfile;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.ReadTimeoutException;
import net.minecraft.server.v1_10_R1.NetworkManager;
import net.minecraft.server.v1_10_R1.PacketListener;
import net.minecraft.server.v1_10_R1.PlayerConnection;
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.events.PlayerDisconnectEvent;
import protocolsupport.logger.AsyncErrorLogger;
import protocolsupport.protocol.packet.handler.AbstractLoginListener;
import protocolsupport.protocol.packet.handler.LoginListenerPlay;
import protocolsupport.protocol.storage.ProtocolStorage;
import protocolsupport.utils.netty.ChannelUtils;

public class ChannelCloseCleanup extends ChannelInboundHandlerAdapter {

	private static final HashMap<Class<? extends Throwable>, Set<?>> ignoreExceptions = new HashMap<>();
	static {
		ignoreExceptions.put(ClosedChannelException.class, Collections.emptySet());
		ignoreExceptions.put(ReadTimeoutException.class, Collections.emptySet());
		ignoreExceptions.put(IOException.class, new HashSet<>(Arrays.asList("Connection reset by peer", "Broken pipe")));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
		super.exceptionCaught(ctx, e);
		Set<?> ignore = ignoreExceptions.get(e.getClass());
		if ((ignore == null) || (!ignore.isEmpty() && !ignore.contains(e.getMessage()))) {
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
		} else if (listener instanceof LoginListenerPlay) {
			username = ((LoginListenerPlay) listener).getProfile().getName();
		} else if (listener instanceof PlayerConnection) {
			username = ((PlayerConnection) listener).player.getProfile().getName();
		}
		if (username != null) {
			PlayerDisconnectEvent event = new PlayerDisconnectEvent(addr, username);
			Bukkit.getPluginManager().callEvent(event);
		}
		ProtocolStorage.removeConnection(addr);
	}

}
