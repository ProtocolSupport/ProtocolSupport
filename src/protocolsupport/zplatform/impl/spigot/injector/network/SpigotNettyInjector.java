package protocolsupport.zplatform.impl.spigot.injector.network;

import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import net.minecraft.server.v1_12_R1.ChatComponentText;
import net.minecraft.server.v1_12_R1.EnumProtocolDirection;
import net.minecraft.server.v1_12_R1.NetworkManager;
import net.minecraft.server.v1_12_R1.ServerConnection;
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
import protocolsupport.zplatform.impl.spigot.SpigotConnectionImpl;
import protocolsupport.zplatform.impl.spigot.SpigotMiscUtils;
import protocolsupport.zplatform.impl.spigot.network.SpigotChannelHandlers;
import protocolsupport.zplatform.impl.spigot.network.SpigotNetworkManagerWrapper;
import protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotPacketDecoder;
import protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotPacketEncoder;
import protocolsupport.zplatform.network.NetworkManagerWrapper;
import raknetserver.RakNetServer;
import raknetserver.RakNetServer.UserChannelInitializer;

public class SpigotNettyInjector {

	@SuppressWarnings("unchecked")
	private static List<NetworkManager> getNetworkManagerList() throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException {
		ServerConnection serverConnection = SpigotMiscUtils.getServer().an();
		try {
			return (List<NetworkManager>) ReflectionUtils.setAccessible(ServerConnection.class.getDeclaredField("pending")).get(serverConnection);
		} catch (NoSuchFieldException e) {
			return (List<NetworkManager>) ReflectionUtils.setAccessible(ServerConnection.class.getDeclaredField("h")).get(serverConnection);
		}
	}

	@SuppressWarnings("unchecked")
	public static void inject() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		ServerConnection serverConnection = SpigotMiscUtils.getServer().an();
		Field connectionsListField = ReflectionUtils.setAccessible(ServerConnection.class.getDeclaredField("g"));
		ChannelInjectList connectionsList = new ChannelInjectList(getNetworkManagerList(), (List<ChannelFuture>) connectionsListField.get(serverConnection));
		connectionsListField.set(serverConnection, connectionsList);
		connectionsList.injectExisting();
	}

	private static RakNetServer peserver;

	public static void startPEServer() {
		try {
			List<NetworkManager> networkmanagerlist = getNetworkManagerList();
			peserver = new RakNetServer(new InetSocketAddress(PENetServerConstants.TEST_PORT), PENetServerConstants.PING_HANDLER, new UserChannelInitializer() {
				@Override
				public void init(Channel channel) {
					NetworkManager networkmanager = new NetworkManager(EnumProtocolDirection.SERVERBOUND);
					NetworkManagerWrapper wrapper = new SpigotNetworkManagerWrapper(networkmanager);
					wrapper.setPacketListener(ServerPlatform.get().getWrapperFactory().createLegacyHandshakeListener(wrapper));
					ConnectionImpl connection = new SpigotConnectionImpl(wrapper);
					connection.storeInChannel(channel);
					ProtocolStorage.addConnection(channel.remoteAddress(), connection);
					connection.setVersion(ProtocolVersion.MINECRAFT_PE);
					NetworkDataCache cache = new NetworkDataCache();
					channel.pipeline().replace("rns-timeout", SpigotChannelHandlers.READ_TIMEOUT, new SimpleReadTimeoutHandler(30));
					channel.pipeline().addLast(new PECompressor());
					channel.pipeline().addLast(new PEPacketEncoder(connection, cache));
					channel.pipeline().addLast(new PEDecompressor());
					channel.pipeline().addLast(new PEPacketDecoder(connection, cache));
					channel.pipeline().addLast(SpigotChannelHandlers.ENCODER, new SpigotPacketEncoder());
					channel.pipeline().addLast(SpigotChannelHandlers.DECODER, new SpigotPacketDecoder());
					channel.pipeline().addLast(ChannelHandlers.LOGIC, new LogicHandler(connection));
					channel.pipeline().addLast(SpigotChannelHandlers.NETWORK_MANAGER, networkmanager);
					networkmanagerlist.add(networkmanager);
				}
			}, PENetServerConstants.USER_PACKET_ID);
			peserver.start();
		} catch (IllegalArgumentException | IllegalAccessException | SecurityException | NoSuchFieldException e) {
			throw new RuntimeException(e);
		}
	}

	public static void stopPEServer() {
		if (peserver != null) {
			peserver.stop();
		}
	}

	public static class ChannelInjectList implements List<ChannelFuture> {

		private final List<NetworkManager> networkManagersList;
		private final List<ChannelFuture> originalList;
		public ChannelInjectList(List<NetworkManager> networkManagerList, List<ChannelFuture> originalList) {
			this.originalList = originalList;
			this.networkManagersList = networkManagerList;
		}

		public void injectExisting() {
			for (ChannelFuture future : originalList) {
				inject(future);
			}
		}

		@Override
		public boolean add(ChannelFuture e) {
			boolean result = originalList.add(e);
			inject(e);
			return result;
		}

		@Override
		public void add(int index, ChannelFuture element) {
			originalList.add(index, element);
			inject(element);
		}

		@Override
		public ChannelFuture set(int index, ChannelFuture element) {
			ChannelFuture result = originalList.set(index, element);
			inject(element);
			return result;
		}

		@Override
		public boolean addAll(Collection<? extends ChannelFuture> c) {
			boolean result = originalList.addAll(c);
			for (ChannelFuture future : c) {
				inject(future);
			}
			return result;
		}

		@Override
		public boolean addAll(int index, Collection<? extends ChannelFuture> c) {
			boolean result = originalList.addAll(index, c);
			for (ChannelFuture future : c) {
				inject(future);
			}
			return result;
		}

		protected void inject(ChannelFuture future) {
			Channel channel = future.channel();
			channel.pipeline().addFirst(new SpigotNettyServerChannelHandler());
			synchronized (networkManagersList) {
				for (NetworkManager nm : networkManagersList) {
					if ((nm.channel != null) && nm.channel.localAddress().equals(channel.localAddress())) {
						nm.close(new ChatComponentText("ProtocolSupport channel reset"));
					}
				}
			}
		}

		@Override
		public int size() {
			return originalList.size();
		}

		@Override
		public boolean isEmpty() {
			return originalList.isEmpty();
		}

		@Override
		public boolean contains(Object o) {
			return originalList.contains(o);
		}

		@Override
		public Iterator<ChannelFuture> iterator() {
			return originalList.iterator();
		}

		@Override
		public Object[] toArray() {
			return originalList.toArray();
		}

		@Override
		public <T> T[] toArray(T[] a) {
			return originalList.toArray(a);
		}

		@Override
		public boolean remove(Object o) {
			return originalList.remove(o);
		}

		@Override
		public boolean containsAll(Collection<?> c) {
			return originalList.containsAll(c);
		}

		@Override
		public boolean removeAll(Collection<?> c) {
			return originalList.removeAll(c);
		}

		@Override
		public boolean retainAll(Collection<?> c) {
			return originalList.retainAll(c);
		}

		@Override
		public void clear() {
			originalList.clear();
		}

		@Override
		public ChannelFuture get(int index) {
			return originalList.get(index);
		}

		@Override
		public ChannelFuture remove(int index) {
			return originalList.remove(index);
		}

		@Override
		public int indexOf(Object o) {
			return originalList.indexOf(o);
		}

		@Override
		public int lastIndexOf(Object o) {
			return originalList.lastIndexOf(o);
		}

		@Override
		public ListIterator<ChannelFuture> listIterator() {
			return originalList.listIterator();
		}

		@Override
		public ListIterator<ChannelFuture> listIterator(int index) {
			return originalList.listIterator(index);
		}

		@Override
		public List<ChannelFuture> subList(int fromIndex, int toIndex) {
			return originalList.subList(fromIndex, toIndex);
		}

	}

}
