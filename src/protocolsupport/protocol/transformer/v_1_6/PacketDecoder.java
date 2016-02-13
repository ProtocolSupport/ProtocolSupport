package protocolsupport.protocol.transformer.v_1_6;

import java.util.List;

import org.spigotmc.SneakyThrow;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import net.minecraft.server.v1_8_R3.EnumProtocol;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.Packet;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.core.IPacketDecoder;
import protocolsupport.protocol.transformer.middlepacket.ServerBoundMiddlePacket;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.handshake.v_1_4_1_5_1_6.ClientLogin;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.handshake.v_1_6.Ping;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.login.v_1_4_1_5_1_6_1_7.EncryptionResponse;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6.ClientCommand;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6.ClientSettings;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6.CustomPayload;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6.KickDisconnect;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6.UseEntity;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6_1_7.Animation;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6_1_7.BlockDig;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6_1_7.BlockPlace;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6_1_7.Chat;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6_1_7.CreativeSetSlot;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6_1_7.Flying;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6_1_7.HeldSlot;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6_1_7.InventoryClick;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6_1_7.InventoryClose;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6_1_7.InventoryEnchant;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6_1_7.InventoryTransaction;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6_1_7.KeepAlive;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6_1_7.Look;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6_1_7.Position;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6_1_7.TabComplete;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6_1_7.UpdateSign;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_6_1_7.EntityAction;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_6_1_7.PlayerAbilities;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_6_1_7.PositionLook;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_6_1_7.SteerVehicle;
import protocolsupport.protocol.transformer.utils.registry.MiddleTransformerRegistry;
import protocolsupport.utils.netty.ChannelUtils;
import protocolsupport.utils.netty.ReplayingDecoderBuffer;
import protocolsupport.utils.netty.ReplayingDecoderBuffer.EOFSignal;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class PacketDecoder implements IPacketDecoder {

	private static final AttributeKey<EnumProtocol> currentStateAttrKey = NetworkManager.c;

	private final MiddleTransformerRegistry<ServerBoundMiddlePacket> dataRemapperRegistry = new MiddleTransformerRegistry<>();
	{
		try {
			dataRemapperRegistry.register(EnumProtocol.HANDSHAKING, 0x02, ClientLogin.class);
			dataRemapperRegistry.register(EnumProtocol.HANDSHAKING, 0xFE, Ping.class);
			dataRemapperRegistry.register(EnumProtocol.LOGIN, 0xFC, EncryptionResponse.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0x00, KeepAlive.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0x03, Chat.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0x07, UseEntity.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0x0A, Flying.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0x0B, Position.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0x0C, Look.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0x0D, PositionLook.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0x0E, BlockDig.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0x0F, BlockPlace.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0x10, HeldSlot.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0x12, Animation.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0x13, EntityAction.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0x1B, SteerVehicle.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0x65, InventoryClose.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0x66, InventoryClick.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0x6A, InventoryTransaction.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0x6B, CreativeSetSlot.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0x6C, InventoryEnchant.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0x82, UpdateSign.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0xCB, TabComplete.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0xCA, PlayerAbilities.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0xCC, ClientSettings.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0xCD, ClientCommand.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0xFA, CustomPayload.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, 0xFF, KickDisconnect.class);
		} catch (Throwable t) {
			SneakyThrow.sneaky(t);
		}
	}

	private final ReplayingDecoderBuffer buffer = new ReplayingDecoderBuffer();
	private final PacketDataSerializer serializer;
	public PacketDecoder(ProtocolVersion version) {
		serializer = new PacketDataSerializer(buffer, version);
	}

	@Override
	public void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
		if (!input.isReadable()) {
			return;
		}
		buffer.setCumulation(input);
		serializer.markReaderIndex();
		Channel channel = ctx.channel();
		EnumProtocol currentProtocol = channel.attr(currentStateAttrKey).get();
		try {
			int packetId = buffer.readUnsignedByte();
			ServerBoundMiddlePacket packetTransformer = dataRemapperRegistry.getTransformer(currentProtocol, packetId);
			if (packetTransformer != null) {
				if (packetTransformer.needsPlayer()) {
					packetTransformer.setPlayer(ChannelUtils.getBukkitPlayer(channel));
				}
				packetTransformer.readFromClientData(serializer);
				RecyclableCollection<? extends Packet<?>> collection = packetTransformer.toNative();
				try {
					list.addAll(collection);
				} finally {
					collection.recycle();
				}
			}
		} catch (EOFSignal ex) {
			serializer.resetReaderIndex();
		}
	}

}
