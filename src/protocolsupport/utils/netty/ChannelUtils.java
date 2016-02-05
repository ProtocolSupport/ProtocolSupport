package protocolsupport.utils.netty;

import java.net.SocketAddress;

import org.bukkit.entity.Player;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import protocolsupport.protocol.core.ChannelHandlers;

public class ChannelUtils {

	public static Player getBukkitPlayer(Channel channel) {
		return getPlayer(getNetworkManager(channel)).getBukkitEntity();
	}

	public static EntityPlayer getPlayer(NetworkManager networkManager) {
		return ((PlayerConnection) networkManager.getPacketListener()).player;
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
		byte b0;
		do {
			b0 = from.readByte();
			value |= (b0 & 0x7F) << (length++ * 7);
			if (length > 5) {
				throw new RuntimeException("VarInt too big");
			}
		} while ((b0 & 0x80) == 0x80);
		return value;
	}

	public static void writeVarInt(ByteBuf to, int i) {
		while ((i & 0xFFFFFF80) != 0x0) {
			to.writeByte((i & 0x7F) | 0x80);
			i >>>= 7;
		}
		to.writeByte(i);
	}

}
