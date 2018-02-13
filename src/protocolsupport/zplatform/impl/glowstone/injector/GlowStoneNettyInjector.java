package protocolsupport.zplatform.impl.glowstone.injector;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import net.glowstone.GlowServer;
import net.glowstone.net.GameServer;
import protocolsupport.ProtocolSupport;
import protocolsupport.utils.ReflectionUtils;
import protocolsupport.zplatform.impl.glowstone.GlowStoneMiscUtils;

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

	public static EventLoopGroup getServerEventLoop() {
		try {
			return (EventLoopGroup) ReflectionUtils.getField(GameServer.class, "workerGroup").get(getGameServer());
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException("unable to get event loop", e);
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
