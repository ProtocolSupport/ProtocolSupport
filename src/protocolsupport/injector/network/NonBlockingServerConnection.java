package protocolsupport.injector.network;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.logging.log4j.Logger;
import org.spigotmc.SpigotConfig;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.CrashReport;
import net.minecraft.server.v1_8_R3.CrashReportSystemDetails;
import net.minecraft.server.v1_8_R3.LazyInitVar;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.PacketPlayOutKickDisconnect;
import net.minecraft.server.v1_8_R3.ReportedException;
import net.minecraft.server.v1_8_R3.ServerConnection;
import protocolsupport.utils.Utils;

public class NonBlockingServerConnection extends ServerConnection {

	//the same names as in ServerConnection in case of plugins that do not search ServerConnection or class hierarchy
	private static Logger e;
	private List<ChannelFuture> g;
	private List<NetworkManager> h;
	//synthetic methods from ServerConnection with signatures that plugins may have actually used
	static List<NetworkManager> access$0(ServerConnection connection) {
		return ((NonBlockingServerConnection) connection).h;
	}
	static List<NetworkManager> access$0(NonBlockingServerConnection connection) {
		return connection.h;
	}
	static MinecraftServer access$1(ServerConnection connection) {
		return MinecraftServer.getServer();
	}
	static MinecraftServer access$1(NonBlockingServerConnection connection) {
		return MinecraftServer.getServer();
	}

	//constructor that is used when initalizing server connection in case there wasn't one before
	@SuppressWarnings("unchecked")
	public NonBlockingServerConnection() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		super(MinecraftServer.getServer());
		e = (Logger) Utils.setAccessible(ServerConnection.class.getDeclaredField("e")).get(null);
		g = (List<ChannelFuture>) Utils.setAccessible(ServerConnection.class.getDeclaredField("g")).get(this);
		h = new ConcurrentLinkedQueueFakeListImpl<NetworkManager>();
		Utils.setAccessible(ServerConnection.class.getDeclaredField("h")).set(this, h);
	}

	public static void inject() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Utils.setAccessible(MinecraftServer.class.getDeclaredField("q")).set(MinecraftServer.getServer(), new NonBlockingServerConnection());
	}

	@Override
	public void a(InetAddress inetaddress, int port) throws IOException {
		synchronized (this.g) {
			Class<? extends ServerSocketChannel> oclass;
			LazyInitVar<? extends EventLoopGroup> lazyinitvar;
			if (Epoll.isAvailable() && MinecraftServer.getServer().ai()) {
				oclass = EpollServerSocketChannel.class;
				lazyinitvar = ServerConnection.b;
				e.info("Using epoll channel type");
			} else {
				oclass = NioServerSocketChannel.class;
				lazyinitvar = ServerConnection.a;
				e.info("Using default channel type");
			}
			this.g.add(
				new ServerBootstrap()
				.channel(oclass)
				.childHandler(new ServerConnectionChannel(h))
				.group(lazyinitvar.c())
				.localAddress(inetaddress, port)
				.bind().syncUninterruptibly()
			);
		}
	}

	//We use CLQ, so we no longer need to synchronize, so netty channel initializer no longer wastes time waiting for network manager list to unlock
	@SuppressWarnings("unchecked")
	@Override
	public void c() {
		if (SpigotConfig.playerShuffle > 0 && MinecraftServer.currentTick % SpigotConfig.playerShuffle == 0) {
			Collections.shuffle(this.h);
		}
		final Iterator<NetworkManager> iterator = this.h.iterator();
		while (iterator.hasNext()) {
			final NetworkManager networkmanager = iterator.next();
			if (!networkmanager.h()) {
				if (!networkmanager.g()) {
					if (networkmanager.preparing) {
						continue;
					}
					iterator.remove();
					networkmanager.l();
				} else {
					try {
						networkmanager.a();
					} catch (Exception exception) {
						if (networkmanager.c()) {
							final CrashReport crashreport = CrashReport.a(exception, "Ticking memory connection");
							final CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Ticking connection");
							crashreportsystemdetails.a("Connection", new Callable<String>() {
								@Override
								public String call() throws Exception {
									return networkmanager.toString();
								}
							});
							throw new ReportedException(crashreport);
						}
						e.warn("Failed to handle packet for " + networkmanager.getSocketAddress(), exception);
						final ChatComponentText chatcomponenttext = new ChatComponentText("Internal server error");
						networkmanager.a(new PacketPlayOutKickDisconnect(chatcomponenttext), new GenericFutureListener<Future<? super Void>>() {
							@Override
							public void operationComplete(Future<? super Void> future) throws Exception {
								networkmanager.close(chatcomponenttext);
							}
						});
						networkmanager.k();
					}
				}
			}
		}
	}

	//CLQ that implements List, but no list methods are actually supported
	public static class ConcurrentLinkedQueueFakeListImpl<E> extends ConcurrentLinkedQueue<E> implements List<E> {
		private static final long serialVersionUID = -8466302736959675653L;
		@Override
		public boolean addAll(int index, Collection<? extends E> c) {
			throw new UnsupportedOperationException();
		}
		@Override
		public E get(int index) {
			throw new UnsupportedOperationException();
		}
		@Override
		public E set(int index, E element) {
			throw new UnsupportedOperationException();
		}
		@Override
		public void add(int index, E element) {
			throw new UnsupportedOperationException();
		}
		@Override
		public E remove(int index) {
			throw new UnsupportedOperationException();
		}
		@Override
		public int indexOf(Object o) {
			throw new UnsupportedOperationException();
		}
		@Override
		public int lastIndexOf(Object o) {
			throw new UnsupportedOperationException();
		}
		@Override
		public ListIterator<E> listIterator() {
			throw new UnsupportedOperationException();
		}
		@Override
		public ListIterator<E> listIterator(int index) {
			throw new UnsupportedOperationException();
		}
		@Override
		public List<E> subList(int fromIndex, int toIndex) {
			throw new UnsupportedOperationException();
		}
	}

}
