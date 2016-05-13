package protocolsupport.protocol.pipeline.wrapped;

import java.net.InetSocketAddress;
import java.util.List;

import org.bukkit.Bukkit;

import com.mojang.authlib.GameProfile;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import net.minecraft.server.v1_9_R2.MinecraftServer;
import net.minecraft.server.v1_9_R2.NetworkManager;
import net.minecraft.server.v1_9_R2.PacketListener;
import net.minecraft.server.v1_9_R2.PlayerConnection;
import protocolsupport.api.events.PlayerDisconnectEvent;
import protocolsupport.protocol.packet.handler.AbstractLoginListener;
import protocolsupport.protocol.pipeline.IPacketDecoder;
import protocolsupport.protocol.storage.ProtocolStorage;
import protocolsupport.utils.netty.ChannelUtils;

public class WrappedDecoder extends ByteToMessageDecoder {

	private IPacketDecoder realDecoder = new IPacketDecoder() {
		@Override
		public void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
		}
	};

	public void setRealDecoder(IPacketDecoder realDecoder) {
		this.realDecoder = realDecoder;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
		realDecoder.decode(ctx, input, list);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		try {
			NetworkManager networkManager = ChannelUtils.getNetworkManager(ctx.channel());
			InetSocketAddress addr = (InetSocketAddress) networkManager.getSocketAddress();
			String username = null;
			PacketListener listener = networkManager.i();
			if (listener instanceof AbstractLoginListener) {
				GameProfile profile = ((AbstractLoginListener) listener).getProfile();
				if (profile != null) {
					username = profile.getName();
				}
			} else if (listener instanceof PlayerConnection) {
				username = ((PlayerConnection) listener).player.getProfile().getName();
			}
			if (username != null) {
				PlayerDisconnectEvent event = new PlayerDisconnectEvent(addr, username);
				Bukkit.getPluginManager().callEvent(event);
			}
			ProtocolStorage.clearData(addr);
		} catch (Throwable t) {
			if (MinecraftServer.getServer().isDebugging()) {
				t.printStackTrace();
			}
		}
	}

}
