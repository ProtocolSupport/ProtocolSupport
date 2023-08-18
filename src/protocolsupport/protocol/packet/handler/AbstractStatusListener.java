package protocolsupport.protocol.packet.handler;

import java.text.MessageFormat;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import protocolsupport.ProtocolSupport;
import protocolsupport.ProtocolSupportFileLog;
import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.api.events.ServerPingResponseEvent;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.utils.pingresponse.PingResponseHandlerProvider;
import protocolsupport.utils.JavaSystemProperty;
import protocolsupport.utils.MiscUtils;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.network.NetworkManagerWrapper;
import protocolsupportbuildprocessor.Preload;

@Preload
public abstract class AbstractStatusListener {

	protected static final int statusThreads = JavaSystemProperty.getValue("statusthreads", 2, Integer::parseInt);
	protected static final int statusThreadKeepAlive = JavaSystemProperty.getValue("statusthreadskeepalive", 60, Integer::parseInt);

	static {
		String message = MessageFormat.format("Status threads max count: {0}, keep alive time: {1}", statusThreads, statusThreadKeepAlive);
		ProtocolSupport.logInfo(message);
		if (ProtocolSupportFileLog.isEnabled()) {
			ProtocolSupportFileLog.logInfoMessage(message);
		}
	}

	protected static final Executor statusprocessor = new ThreadPoolExecutor(
		1, statusThreads,
		statusThreadKeepAlive, TimeUnit.SECONDS,
		new LinkedBlockingQueue<>(),
		r -> new Thread(r, "StatusProcessingThread")
	);

	protected final NetworkManagerWrapper networkManager;

	protected AbstractStatusListener(NetworkManagerWrapper networkmanager) {
		this.networkManager = networkmanager;
	}

	private boolean sentInfo = false;

	public void handleStatusRequest() {
		if (sentInfo) {
			networkManager.close(new TextComponent("Status request has already been handled"));
			return;
		}
		sentInfo = true;

		statusprocessor.execute(() -> {
			ServerPingResponseEvent revent = PingResponseHandlerProvider.get().createResponse(ConnectionImpl.getFromChannel(networkManager.getChannel()));
			networkManager.sendPacket(ServerPlatform.get().getPacketFactory().createStatusServerInfoPacket(
				revent.getPlayers(), revent.getProtocolInfo(),
				revent.getIcon(), revent.getJsonMotd(),
				revent.getOnlinePlayers(), revent.getMaxPlayers()
			));
		});
	}

	public void handlePing(long pingId) {
		try {
			networkManager.sendPacket(
				ServerPlatform.get().getPacketFactory().createStatusPongPacket(pingId),
				() -> networkManager.getChannel().close(), 5, TimeUnit.SECONDS,
				() -> networkManager.getChannel().close()
			);
		} catch (Throwable t) {
			networkManager.getChannel().close();
			MiscUtils.rethrowThreadException(t);
		}
	}

}
