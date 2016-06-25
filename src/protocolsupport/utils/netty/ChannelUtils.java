package protocolsupport.utils.netty;

import java.net.SocketAddress;

import org.bukkit.entity.Player;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.handler.codec.DecoderException;
import io.netty.util.AttributeKey;
import net.minecraft.server.v1_10_R1.EntityPlayer;
import net.minecraft.server.v1_10_R1.EnumProtocol;
import net.minecraft.server.v1_10_R1.NetworkManager;
import net.minecraft.server.v1_10_R1.PlayerConnection;
import protocolsupport.protocol.pipeline.ChannelHandlers;

public class ChannelUtils {

	public static final AttributeKey<EnumProtocol> CURRENT_PROTOCOL_KEY = NetworkManager.c;

	public static Player getBukkitPlayer(Channel channel) {
		return getPlayer(getNetworkManager(channel)).getBukkitEntity();
	}

	public static EntityPlayer getPlayer(NetworkManager networkManager) {
		return ((PlayerConnection) networkManager.i()).player;
	}

	public static SocketAddress getNetworkManagerSocketAddress(Channel channel) {
		return ChannelUtils.getNetworkManager(channel).getSocketAddress();
	}

	public static NetworkManager getNetworkManager(Channel channel) {
		return (NetworkManager) channel.pipeline().get(ChannelHandlers.NETWORK_MANAGER);
	}

	public static byte[] toArray(ByteBuf buf) {
		byte[] result = new byte[buf.readableBytes()];
		buf.readBytes(result);
		return result;
	}

	public static int readVarInt(ByteBuf from) {
		int value = 0;
		int length = 0;
		byte part;
		do {
			part = from.readByte();
			value |= (part & 0x7F) << (length++ * 7);
			if (length > 5) {
				throw new DecoderException("VarInt too big");
			}
		} while (part < 0);
		return value;
	}

	public static void writeVarInt(ByteBuf to, int i) {
		while ((i & 0xFFFFFF80) != 0x0) {
			to.writeByte(i | 0x80);
			i >>>= 7;
		}
		to.writeByte(i);
	}

}
