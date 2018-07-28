//package protocolsupport.zplatform.impl.glowstone.injector;
//
//import java.lang.reflect.Field;
//import java.util.concurrent.CountDownLatch;
//
//import org.bukkit.Bukkit;
//import org.bukkit.plugin.java.JavaPlugin;
//
//import io.netty.channel.Channel;
//import net.glowstone.GlowServer;
//import net.glowstone.net.GameServer;
//import protocolsupport.ProtocolSupport;
//import protocolsupport.utils.ReflectionUtils;
//import protocolsupport.zplatform.impl.glowstone.GlowStoneMiscUtils;
//
//public class GlowStoneNettyInjector {
//
//	private static final CountDownLatch injectFinishedLatch = new CountDownLatch(1);
//
//	public static void inject() {
//		Bukkit.getScheduler().scheduleSyncDelayedTask(JavaPlugin.getPlugin(ProtocolSupport.class), () -> {
//			try {
//				injectFinishedLatch.await();
//			} catch (InterruptedException e) {
//				System.err.println("Interrupted while waiting for inject finish");
//				e.printStackTrace();
//			}
//		});
//		new Thread(() -> {
//			try {
//				GlowServer server = GlowStoneMiscUtils.getServer();
//				//TODO: PR some sort of channel created signal to GlowStone
//				GameServer gameserver = getWithWait(ReflectionUtils.getField(GlowServer.class, "networkServer"), server);
//				Channel channel = getWithWait(ReflectionUtils.getField(GameServer.class, "channel"), gameserver);
//				channel.pipeline().addFirst(new GlowStoneNettyServerChannelHandler());
//				Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer("Channel reset"));
//			} catch (IllegalArgumentException | IllegalAccessException | InterruptedException e) {
//				System.err.println("Unable to inject");
//				e.printStackTrace();
//			}
//			injectFinishedLatch.countDown();
//		}).start();
//	}
//
//	@SuppressWarnings("unchecked")
//	private static <T> T getWithWait(Field field, Object obj) throws IllegalAccessException, InterruptedException {
//		Object val = null;
//		while (true) {
//			val = field.get(obj);
//			if (val == null) {
//				Thread.sleep(50);
//			} else {
//				break;
//			}
//		}
//		return (T) val;
//	}
//
//}
