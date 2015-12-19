package protocolsupport.protocol.transformer.v_1_5;

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
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.handshake.v_1_4_1_5_1_6.ClientLogin;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.handshake.v_1_5.Ping;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5.EntityAction;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5.PlayerAbilities;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5.PositionLook;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6.ClientCommand;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6.ClientSettings;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6.CustomPayload;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6.KickDisconnect;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6.UseEntity;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6_1_7.Animation;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6_1_7.BlockDig;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6_1_7.BlockPlace;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6_1_7.InventoryClick;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6_1_7.KeepAlive;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6_1_7.Position;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6_1_7.TabComplete;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6_1_7.UpdateSign;
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
		packetIdRegistry.register(EnumProtocol.LOGIN, 0xFC, ServerBoundPacket.LOGIN_ENCRYPTION_BEGIN.getId());
		packetIdRegistry.register(EnumProtocol.PLAY, 0x03, ServerBoundPacket.PLAY_CHAT.getId());
		packetIdRegistry.register(EnumProtocol.PLAY, 0x0A, ServerBoundPacket.PLAY_PLAYER.getId());
		packetIdRegistry.register(EnumProtocol.PLAY, 0x0C, ServerBoundPacket.PLAY_LOOK.getId());
		packetIdRegistry.register(EnumProtocol.PLAY, 0x10, ServerBoundPacket.PLAY_HELD_SLOT.getId());
		packetIdRegistry.register(EnumProtocol.PLAY, 0x65, ServerBoundPacket.PLAY_CLOSE_WINDOW.getId());
		packetIdRegistry.register(EnumProtocol.PLAY, 0x6A, ServerBoundPacket.PLAY_WINDOW_TRANSACTION.getId());
		packetIdRegistry.register(EnumProtocol.PLAY, 0x6B, ServerBoundPacket.PLAY_CREATIVE_SET_SLOT.getId());
		packetIdRegistry.register(EnumProtocol.PLAY, 0x6C, ServerBoundPacket.PLAY_ENCHANT_SELECT.getId());
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
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0x66, InventoryClick.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0x82, UpdateSign.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0xCA, PlayerAbilities.class);
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
					packetTransformer.setPlayer(Utils.getBukkitPlayer(channel));
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
