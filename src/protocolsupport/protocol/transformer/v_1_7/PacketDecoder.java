package protocolsupport.protocol.transformer.v_1_7;

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
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.handshake.v_1_7.SetProtocol;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.login.v_1_4_1_5_1_6_1_7.EncryptionResponse;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.login.v_1_7.LoginStart;
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
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_7.ClientCommand;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_7.ClientSettings;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_7.CustomPayload;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_7.UseEntity;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.status.v_1_7.Ping;
import protocolsupport.protocol.transformer.middlepacketimpl.serverbound.status.v_1_7.ServerInfoRequest;
import protocolsupport.protocol.transformer.utils.registry.MiddleTransformerRegistry;
import protocolsupport.utils.netty.ChannelUtils;
import protocolsupport.utils.netty.WrappingBuffer;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class PacketDecoder implements IPacketDecoder {

	private static final AttributeKey<EnumProtocol> currentStateAttrKey = NetworkManager.c;

	private final MiddleTransformerRegistry<ServerBoundMiddlePacket> registry = new MiddleTransformerRegistry<>();
	{
		try {
			registry.register(EnumProtocol.HANDSHAKING, 0x00, SetProtocol.class);
			registry.register(EnumProtocol.LOGIN, 0x00, LoginStart.class);
			registry.register(EnumProtocol.LOGIN, 0x01, EncryptionResponse.class);
			registry.register(EnumProtocol.STATUS, 0x00, ServerInfoRequest.class);
			registry.register(EnumProtocol.STATUS, 0x01, Ping.class);
			registry.register(EnumProtocol.PLAY, 0x00, KeepAlive.class);
			registry.register(EnumProtocol.PLAY, 0x01, Chat.class);
			registry.register(EnumProtocol.PLAY, 0x02, UseEntity.class);
			registry.register(EnumProtocol.PLAY, 0x03, Flying.class);
			registry.register(EnumProtocol.PLAY, 0x04, Position.class);
			registry.register(EnumProtocol.PLAY, 0x05, Look.class);
			registry.register(EnumProtocol.PLAY, 0x06, PositionLook.class);
			registry.register(EnumProtocol.PLAY, 0x07, BlockDig.class);
			registry.register(EnumProtocol.PLAY, 0x08, BlockPlace.class);
			registry.register(EnumProtocol.PLAY, 0x09, HeldSlot.class);
			registry.register(EnumProtocol.PLAY, 0x0A, Animation.class);
			registry.register(EnumProtocol.PLAY, 0x0B, EntityAction.class);
			registry.register(EnumProtocol.PLAY, 0x0C, SteerVehicle.class);
			registry.register(EnumProtocol.PLAY, 0x0D, InventoryClose.class);
			registry.register(EnumProtocol.PLAY, 0x0E, InventoryClick.class);
			registry.register(EnumProtocol.PLAY, 0x0F, InventoryTransaction.class);
			registry.register(EnumProtocol.PLAY, 0x10, CreativeSetSlot.class);
			registry.register(EnumProtocol.PLAY, 0x11, InventoryEnchant.class);
			registry.register(EnumProtocol.PLAY, 0x12, UpdateSign.class);
			registry.register(EnumProtocol.PLAY, 0x13, PlayerAbilities.class);
			registry.register(EnumProtocol.PLAY, 0x14, TabComplete.class);
			registry.register(EnumProtocol.PLAY, 0x15, ClientSettings.class);
			registry.register(EnumProtocol.PLAY, 0x16, ClientCommand.class);
			registry.register(EnumProtocol.PLAY, 0x17, CustomPayload.class);
		} catch (Throwable t) {
			SneakyThrow.sneaky(t);
		}
	}

	private final WrappingBuffer buffer = new WrappingBuffer();
	private final PacketDataSerializer serializer;
	public PacketDecoder(ProtocolVersion version) {
		this.serializer = new PacketDataSerializer(buffer, version);
	}

	@Override
	public void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
		if (!input.isReadable()) {
			return;
		}
		Channel channel = ctx.channel();
		EnumProtocol currentProtocol = channel.attr(currentStateAttrKey).get();
		buffer.setBuf(input);
		int packetId = serializer.readVarInt();
		ServerBoundMiddlePacket packetTransformer = registry.getTransformer(currentProtocol, packetId);
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
	}

}
