package protocolsupport.protocol.pipeline.common;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.text.MessageFormat;
import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.unix.Errors.NativeIoException;
import io.netty.handler.timeout.ReadTimeoutException;
import protocolsupport.ProtocolSupport;
import protocolsupport.api.events.ConnectionCloseEvent;
import protocolsupport.api.events.ConnectionOpenEvent;
import protocolsupport.api.events.PlayerDisconnectEvent;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.storage.ProtocolStorage;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class LogicHandler extends ChannelDuplexHandler {

	private static final HashSet<Class<? extends Throwable>> ignoreExceptions = new HashSet<>();
	static {
		ignoreExceptions.add(ClosedChannelException.class);
		ignoreExceptions.add(ReadTimeoutException.class);
		ignoreExceptions.add(IOException.class);
		ignoreExceptions.add(NativeIoException.class);
	}

	private final ConnectionImpl connection;
	public LogicHandler(ConnectionImpl connection) {
		this.connection = connection;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		msg = connection.handlePacketReceive(msg);
		if (msg == null) {
			return;
		}
		super.channelRead(ctx, msg);
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		msg = connection.handlePacketSend(msg);
		if (msg == null) {
			promise.setSuccess();
			return;
		}
		super.write(ctx, msg, promise);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
		if (ServerPlatform.get().getMiscUtils().isDebugging() && !ignoreExceptions.contains(e.getClass())) {
			super.exceptionCaught(ctx, new NetworkException(e, connection));
		} else {
			super.exceptionCaught(ctx, e);
		}
	}

	private static final class NetworkException extends Exception {
		private static final long serialVersionUID = 1L;

		public NetworkException(Throwable original, ConnectionImpl connection) {
			super(MessageFormat.format(
				"ProtocolSupport(buildinfo: {0}): Network exception occured(address: {1}, username: {2}, version: {3})",
				JavaPlugin.getPlugin(ProtocolSupport.class).getBuildInfo(),
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
