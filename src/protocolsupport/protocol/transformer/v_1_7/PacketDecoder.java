package protocolsupport.protocol.transformer.v_1_7;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

import java.util.List;

import org.spigotmc.SneakyThrow;

import net.minecraft.server.v1_8_R3.EnumProtocol;
import net.minecraft.server.v1_8_R3.NetworkManager;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.ServerBoundPacket;
import protocolsupport.protocol.core.IPacketDecoder;
import protocolsupport.protocol.transformer.middlepacket.ServerBoundMiddlePacket;
import protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.serverbound.play.Animation;
import protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.serverbound.play.BlockDig;
import protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.serverbound.play.BlockPlace;
import protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.serverbound.play.InventoryClick;
import protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.serverbound.play.KeepAlive;
import protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.serverbound.play.Position;
import protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.serverbound.play.TabComplete;
import protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.serverbound.play.UpdateSign;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_6_1_7.serverbound.play.EntityAction;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_6_1_7.serverbound.play.PositionLook;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_6_1_7.serverbound.play.SteerVehicle;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_7.serverbound.handshake.SetProtocol;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_7.serverbound.play.ClientSettings;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_7.serverbound.play.CustomPayload;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_7.serverbound.play.UseEntity;
import protocolsupport.protocol.transformer.utils.registry.MiddleTransformerRegistry;
import protocolsupport.utils.PacketCreator;
import protocolsupport.utils.Utils;

public class PacketDecoder implements IPacketDecoder {

	private static final AttributeKey<EnumProtocol> currentStateAttrKey = NetworkManager.c;

	private static final MiddleTransformerRegistry<ServerBoundMiddlePacket> registry = new MiddleTransformerRegistry<>();
	static {
		try {
			registry.register(EnumProtocol.HANDSHAKING, 0x00, SetProtocol.class);
			registry.register(EnumProtocol.PLAY, 0x00, KeepAlive.class);
			registry.register(EnumProtocol.PLAY, 0x02, UseEntity.class);
			registry.register(EnumProtocol.PLAY, 0x04, Position.class);
			registry.register(EnumProtocol.PLAY, 0x06, PositionLook.class);
			registry.register(EnumProtocol.PLAY, 0x07, BlockDig.class);
			registry.register(EnumProtocol.PLAY, 0x08, BlockPlace.class);
			registry.register(EnumProtocol.PLAY, 0x0A, Animation.class);
			registry.register(EnumProtocol.PLAY, 0x0B, EntityAction.class);
			registry.register(EnumProtocol.PLAY, 0x0C, SteerVehicle.class);
			registry.register(EnumProtocol.PLAY, 0x0E, InventoryClick.class);
			registry.register(EnumProtocol.PLAY, 0x12, UpdateSign.class);
			registry.register(EnumProtocol.PLAY, 0x14, TabComplete.class);
			registry.register(EnumProtocol.PLAY, 0x15, ClientSettings.class);
			registry.register(EnumProtocol.PLAY, 0x17, CustomPayload.class);
		} catch (Throwable t) {
			SneakyThrow.sneaky(t);
		}
	}

	private final ProtocolVersion version;
	public PacketDecoder(ProtocolVersion version) {
		this.version = version;
	}

	@Override
	public void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
		if (!input.isReadable()) {
			return;
		}
		Channel channel = ctx.channel();
		PacketDataSerializer serializer = new PacketDataSerializer(input, version);
		EnumProtocol currentProtocol = channel.attr(currentStateAttrKey).get();
		int packetId = serializer.readVarInt();
		ServerBoundMiddlePacket packetTransformer = registry.getTransformer(currentProtocol, packetId);
		if (packetTransformer != null) {
			if (packetTransformer.needsPlayer()) {
				packetTransformer.setPlayer(Utils.getPlayer(channel));
			}
			packetTransformer.readFromClientData(serializer);
			list.addAll(packetTransformer.toNative());
		} else {
			list.add(PacketCreator.createWithData(ServerBoundPacket.get(currentProtocol, packetId), serializer));
		}
	}

}
