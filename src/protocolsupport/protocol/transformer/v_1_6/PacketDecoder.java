package protocolsupport.protocol.transformer.v_1_6;

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
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_5_1_6.serverbound.handshake.ClientLogin;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_5_1_6.serverbound.play.ClientCommand;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_5_1_6.serverbound.play.ClientSettings;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_5_1_6.serverbound.play.CustomPayload;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_5_1_6.serverbound.play.KickDisconnect;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_5_1_6.serverbound.play.UseEntity;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_6.serverbound.handshake.Ping;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_6_1_7.serverbound.play.EntityAction;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_6_1_7.serverbound.play.PositionLook;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_6_1_7.serverbound.play.SteerVehicle;
import protocolsupport.protocol.transformer.utils.registry.MiddleTransformerRegistry;
import protocolsupport.protocol.transformer.utils.registry.PacketIdTransformerRegistry;
import protocolsupport.utils.PacketCreator;
import protocolsupport.utils.ReplayingDecoderBuffer;
import protocolsupport.utils.Utils;
import protocolsupport.utils.ReplayingDecoderBuffer.EOFSignal;

public class PacketDecoder implements IPacketDecoder {

	private static final AttributeKey<EnumProtocol> currentStateAttrKey = NetworkManager.c;

	private static final PacketIdTransformerRegistry packetIdRegistry = new PacketIdTransformerRegistry();
	static {
		packetIdRegistry.register(EnumProtocol.PLAY, 0x03, ServerBoundPacket.PLAY_CHAT.getId());
		packetIdRegistry.register(EnumProtocol.PLAY, 0x0A, ServerBoundPacket.PLAY_PLAYER.getId());
		packetIdRegistry.register(EnumProtocol.PLAY, 0x0C, ServerBoundPacket.PLAY_LOOK.getId());
		packetIdRegistry.register(EnumProtocol.PLAY, 0x10, ServerBoundPacket.PLAY_HELD_SLOT.getId());
		packetIdRegistry.register(EnumProtocol.PLAY, 0x65, ServerBoundPacket.PLAY_CLOSE_WINDOW.getId());
		packetIdRegistry.register(EnumProtocol.PLAY, 0x6A, ServerBoundPacket.PLAY_WINDOW_TRANSACTION.getId());
		packetIdRegistry.register(EnumProtocol.PLAY, 0x6B, ServerBoundPacket.PLAY_CREATIVE_SET_SLOT.getId());
		packetIdRegistry.register(EnumProtocol.PLAY, 0x6C, ServerBoundPacket.PLAY_ENCHANT_SELECT.getId());
		packetIdRegistry.register(EnumProtocol.PLAY, 0xCA, ServerBoundPacket.PLAY_ABILITIES.getId());
	}

	private static final MiddleTransformerRegistry<ServerBoundMiddlePacket> dataRemapperRegistry = new MiddleTransformerRegistry<>();
	static {
		try {
			dataRemapperRegistry.register(EnumProtocol.HANDSHAKING, 0x02, ClientLogin.class);
			dataRemapperRegistry.register(EnumProtocol.HANDSHAKING, 0xFE, Ping.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0x00, KeepAlive.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0x07, UseEntity.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0x0B, Position.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0x0D, PositionLook.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0x0E, BlockDig.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0x0F, BlockPlace.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0x12, Animation.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0x13, EntityAction.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0x1B, SteerVehicle.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0x66, InventoryClick.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0x82, UpdateSign.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0xCB, TabComplete.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0xCC, ClientSettings.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0xCD, ClientCommand.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0xFA, CustomPayload.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0xFF, KickDisconnect.class);
		} catch (Throwable t) {
			SneakyThrow.sneaky(t);
		}
	}

	private final ProtocolVersion version;
	public PacketDecoder(ProtocolVersion version) {
		this.version = version;
	}

	private final ReplayingDecoderBuffer buffer = new ReplayingDecoderBuffer();

	@Override
	public void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
		if (!input.isReadable()) {
			return;
		}
		buffer.setCumulation(input);
		buffer.markReaderIndex();
		Channel channel = ctx.channel();
		PacketDataSerializer serializer = new PacketDataSerializer(buffer, version);
		EnumProtocol currentProtocol = channel.attr(currentStateAttrKey).get();
		try {
			int packetId = serializer.readUnsignedByte();
			ServerBoundMiddlePacket packetTransformer = dataRemapperRegistry.getTransformer(currentProtocol, packetId);
			if (packetTransformer != null) {
				if (packetTransformer.needsPlayer()) {
					packetTransformer.setPlayer(Utils.getPlayer(channel));
				}
				packetTransformer.readFromClientData(serializer);
				list.addAll(packetTransformer.toNative());
			} else {
				int realPacketId = packetIdRegistry.getNewPacketId(currentProtocol, packetId);
				list.add(PacketCreator.createWithData(ServerBoundPacket.get(currentProtocol, realPacketId), serializer));
			}
		} catch (EOFSignal ex) {
			buffer.resetReaderIndex();
		}
	}

}
