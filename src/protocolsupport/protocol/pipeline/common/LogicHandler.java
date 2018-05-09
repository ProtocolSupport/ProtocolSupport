package protocolsupport.protocol.pipeline.common;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Bukkit;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.unix.Errors.NativeIoException;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.timeout.ReadTimeoutException;
import protocolsupport.ProtocolSupport;
import protocolsupport.api.events.ConnectionCloseEvent;
import protocolsupport.api.events.ConnectionOpenEvent;
import protocolsupport.api.events.PlayerDisconnectEvent;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.storage.ProtocolStorage;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class LogicHandler extends MessageToMessageCodec<Object, Object> {

	protected static final HashSet<Class<? extends Throwable>> ignoreExceptions = new HashSet<>();
	static {
		ignoreExceptions.add(ClosedChannelException.class);
		ignoreExceptions.add(ReadTimeoutException.class);
		ignoreExceptions.add(IOException.class);
		ignoreExceptions.add(NativeIoException.class);
	}

	protected final ConnectionImpl connection;
	protected final Class<?> nativePacketSuperClass;
	public LogicHandler(ConnectionImpl connection, Class<?> nativePacketSuperClass) {
		this.connection = connection;
		this.nativePacketSuperClass = nativePacketSuperClass;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, Object packet, List<Object> out) throws Exception {
		if (!nativePacketSuperClass.isInstance(packet)) {
			out.add(packet);
		} else {
			connection.handlePacketReceive(packet, out);
		}
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, Object packet, List<Object> out) throws Exception {
		if (!nativePacketSuperClass.isInstance(packet)) {
			out.add(packet);
		} else {
			connection.handlePacketSend(packet, out);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
		if (ServerPlatform.get().getMiscUtils().isDebugging() && !ignoreExceptions.contains(e.getClass())) {
			super.exceptionCaught(ctx, new NetworkException(e, connection));
		} else {
			super.exceptionCaught(ctx, e);
		}
	}

	protected static final class NetworkException extends Exception {
		private static final long serialVersionUID = 1L;

		public NetworkException(Throwable original, ConnectionImpl connection) {
			super(MessageFormat.format(
				"ProtocolSupport(buildinfo: {0}): Network exception occured(address: {1}, username: {2}, version: {3})",
				ProtocolSupport.getInstance().getBuildInfo(),
				connection.getAddress(),
				connection.getNetworkManagerWrapper().getUserName(),
				connection.getVersion()
			), original);
		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		Bukkit.getPluginManager().callEvent(new ConnectionOpenEvent(connection));
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		NetworkManagerWrapper networkmanager = connection.getNetworkManagerWrapper();
		String username = networkmanager.getUserName();
		if (username != null) {
			Bukkit.getPluginManager().callEvent(new PlayerDisconnectEvent(connection, username));
		}
		Bukkit.getPluginManager().callEvent(new ConnectionCloseEvent(connection));
		ProtocolStorage.removeConnection(networkmanager.getRawAddress());
	}

}
