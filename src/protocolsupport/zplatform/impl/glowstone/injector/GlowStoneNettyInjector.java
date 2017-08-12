package protocolsupport.zplatform.impl.glowstone.injector;

import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.glowstone.GlowServer;
import net.glowstone.net.GameServer;
import net.glowstone.net.pipeline.CodecsHandler;
import net.glowstone.net.pipeline.MessageHandler;
import net.glowstone.net.protocol.ProtocolType;
import protocolsupport.ProtocolSupport;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.common.LogicHandler;
import protocolsupport.protocol.pipeline.common.SimpleReadTimeoutHandler;
import protocolsupport.protocol.pipeline.version.v_pe.PECompressor;
import protocolsupport.protocol.pipeline.version.v_pe.PEDecompressor;
import protocolsupport.protocol.pipeline.version.v_pe.PEPacketDecoder;
import protocolsupport.protocol.pipeline.version.v_pe.PEPacketEncoder;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.protocol.storage.ProtocolStorage;
import protocolsupport.utils.ReflectionUtils;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.impl.PENetServerConstants;
import protocolsupport.zplatform.impl.glowstone.GlowStoneConnectionImpl;
import protocolsupport.zplatform.impl.glowstone.GlowStoneMiscUtils;
import protocolsupport.zplatform.impl.glowstone.network.GlowStoneChannelHandlers;
import protocolsupport.zplatform.impl.glowstone.network.GlowStoneNetworkManagerWrapper;
import protocolsupport.zplatform.impl.glowstone.network.pipeline.GlowStoneSyncConnectionTicker;
import protocolsupport.zplatform.impl.spigot.network.SpigotChannelHandlers;
import protocolsupport.zplatform.network.NetworkManagerWrapper;
import raknetserver.RakNetServer;
import raknetserver.RakNetServer.UserChannelInitializer;

public class GlowStoneNettyInjector {

	private static GameServer getGameServer() throws IllegalArgumentException, IllegalAccessException {
		return getWithWait(ReflectionUtils.getField(GlowServer.class, "networkServer"), GlowStoneMiscUtils.getServer());
	}

	private static final CountDownLatch injectFinishedLatch = new CountDownLatch(1);

	public static void inject() throws IllegalArgumentException, IllegalAccessException {
		Bukkit.getScheduler().scheduleSyncDelayedTask(JavaPlugin.getPlugin(ProtocolSupport.class), () -> {
			try {
				injectFinishedLatch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		new Thread(() -> {
			try {
				//TODO: PR some sort of channel created signal to GlowStone
				Channel channel = getWithWait(ReflectionUtils.getField(GameServer.class, "channel"), getGameServer());
				channel.pipeline().addFirst(new GlowStoneNettyServerChannelHandler());
				Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer("Channel reset"));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			injectFinishedLatch.countDown();
		}).start();
	}

	private static RakNetServer peserver;

	public static void startPEServer() {
		try {
			GameServer gameserver = getGameServer();
			String serverIp = gameserver.getServer().getIp();
			if (serverIp.isEmpty()) {
				serverIp = "0.0.0.0";
			}
			peserver = new RakNetServer(new InetSocketAddress(serverIp,PENetServerConstants.TEST_PORT), PENetServerConstants.PING_HANDLER, new UserChannelInitializer() {
				@Override
				public void init(Channel channel) {
					MessageHandler networkmanager = new MessageHandler(gameserver);
					NetworkManagerWrapper wrapper = new GlowStoneNetworkManagerWrapper(networkmanager);
					ConnectionImpl connection = new GlowStoneConnectionImpl(wrapper);
					connection.storeInChannel(channel);
					ProtocolStorage.addConnection(channel.remoteAddress(), connection);
					connection.setVersion(ProtocolVersion.MINECRAFT_PE);
					NetworkDataCache cache = new NetworkDataCache();
					channel.pipeline().replace("rns-timeout", SpigotChannelHandlers.READ_TIMEOUT, new SimpleReadTimeoutHandler(30));
					channel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
						@Override
						public void channelActive(ChannelHandlerContext ctx) throws Exception {
							super.channelActive(ctx);
							wrapper.setPacketListener(ServerPlatform.get().getWrapperFactory().createLegacyHandshakeListener(wrapper));
							ctx.channel().pipeline().remove(this);
						}
					});
					channel.pipeline().addLast(new PECompressor());
					channel.pipeline().addLast(new PEPacketEncoder(connection, cache));
					channel.pipeline().addLast(new PEDecompressor());
					channel.pipeline().addLast(new PEPacketDecoder(connection, cache));
					channel.pipeline().addLast(GlowStoneChannelHandlers.DECODER_ENCODER, new CodecsHandler(ProtocolType.HANDSHAKE.getProtocol()));
					channel.pipeline().addLast("ps_glowstone_sync_ticker", new GlowStoneSyncConnectionTicker());
					channel.pipeline().addLast(ChannelHandlers.LOGIC, new LogicHandler(connection));
					channel.pipeline().addLast(GlowStoneChannelHandlers.NETWORK_MANAGER, networkmanager);
				}
			}, PENetServerConstants.USER_PACKET_ID);
			peserver.start();
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public static void stopPEServer() {
		if (peserver != null) {
			peserver.stop();
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> T getWithWait(Field field, Object obj) throws IllegalArgumentException, IllegalAccessException {
		Object val = null;
		while (true) {
			val = field.get(obj);
			if (val == null) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
				}
			} else {
				break;
			}
		}
		return (T) val;
	}

}
