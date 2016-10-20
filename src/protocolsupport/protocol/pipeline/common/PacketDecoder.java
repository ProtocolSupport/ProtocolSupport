package protocolsupport.protocol.pipeline.common;

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
import io.netty.handler.codec.DecoderException;
import io.netty.handler.timeout.ReadTimeoutException;
import net.minecraft.server.v1_10_R1.EnumProtocol;
import net.minecraft.server.v1_10_R1.EnumProtocolDirection;
import net.minecraft.server.v1_10_R1.NetworkManager;
import net.minecraft.server.v1_10_R1.Packet;
import net.minecraft.server.v1_10_R1.PacketDataSerializer;
import net.minecraft.server.v1_10_R1.PacketListener;
import net.minecraft.server.v1_10_R1.PlayerConnection;
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.events.PlayerDisconnectEvent;
import protocolsupport.logger.AsyncErrorLogger;
import protocolsupport.protocol.packet.handler.AbstractLoginListener;
import protocolsupport.protocol.packet.handler.LoginListenerPlay;
import protocolsupport.protocol.storage.ProtocolStorage;
import protocolsupport.utils.netty.ChannelUtils;
import protocolsupport.utils.netty.WrappingBuffer;

public class PacketDecoder extends ByteToMessageDecoder {

	private final WrappingBuffer wrapper = new WrappingBuffer();
	private final PacketDataSerializer nativeSerializer = new PacketDataSerializer(wrapper);

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
		if (!input.isReadable()) {
			return;
		}
		EnumProtocol protocol = ctx.channel().attr(ChannelUtils.CURRENT_PROTOCOL_KEY).get();
		wrapper.setBuf(input);
		int packetId = ChannelUtils.readVarInt(wrapper);
		final Packet<?> packet = protocol.a(EnumProtocolDirection.SERVERBOUND, packetId);
		if (packet == null) {
			throw new DecoderException("Bad packet id " + packetId);
		}
		packet.a(nativeSerializer);
		if (nativeSerializer.isReadable()) {
			throw new DecoderException("Did not read all data from packet " + packet.getClass().getName()
					+ ", bytes left: " + nativeSerializer.readableBytes());
		}
		list.add(packet);
	}

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
