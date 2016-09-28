package protocolsupport.protocol.packet.v_1_9.r1;

import java.util.List;

import org.spigotmc.SneakyThrow;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import net.minecraft.server.v1_10_R1.EnumProtocol;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.PacketCreator;
import protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_1_7__1_8__1_9_r1__1_9_r2.SetProtocol;
import protocolsupport.protocol.packet.middleimpl.serverbound.login.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2.EncryptionResponse;
import protocolsupport.protocol.packet.middleimpl.serverbound.login.v_1_7__1_8__1_9_r1__1_9_r2.LoginStart;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v1_9_r1__1_9_r2.MoveVehicle;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v1_9_r1__1_9_r2.SteerBoat;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v1_9_r1__1_9_r2.TeleportAccept;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v1_9_r1__1_9_r2.UseItem;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2.Chat;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2.CreativeSetSlot;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2.Flying;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2.HeldSlot;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2.InventoryClick;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2.InventoryClose;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2.InventoryEnchant;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2.InventoryTransaction;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2.Look;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_6__1_7__1_8__1_9_r1__1_9_r2.PlayerAbilities;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_7__1_8__1_9_r1__1_9_r2.ClientCommand;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2.Animation;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2.BlockDig;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2.BlockPlace;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2.ClientSettings;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2.CustomPayload;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2.EntityAction;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2.KeepAlive;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2.Position;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2.PositionLook;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2.ResourcePackStatus;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2.Spectate;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2.SteerVehicle;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2.TabComplete;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2.UpdateSign;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2.UseEntity;
import protocolsupport.protocol.packet.middleimpl.serverbound.status.v_1_7__1_8__1_9_r1__1_9_r2.Ping;
import protocolsupport.protocol.packet.middleimpl.serverbound.status.v_1_7__1_8__1_9_r1__1_9_r2.ServerInfoRequest;
import protocolsupport.protocol.pipeline.IPacketDecoder;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.protocol.storage.SharedStorage;
import protocolsupport.protocol.utils.registry.MiddleTransformerRegistry;
import protocolsupport.protocol.utils.registry.MiddleTransformerRegistry.InitCallBack;
import protocolsupport.utils.netty.ChannelUtils;

public class PacketDecoder implements IPacketDecoder {

	private final MiddleTransformerRegistry<ServerBoundMiddlePacket> registry = new MiddleTransformerRegistry<>();
	{
		try {
			registry.register(EnumProtocol.HANDSHAKING, 0x00, SetProtocol.class);
			registry.register(EnumProtocol.LOGIN, 0x00, LoginStart.class);
			registry.register(EnumProtocol.LOGIN, 0x01, EncryptionResponse.class);
			registry.register(EnumProtocol.STATUS, 0x00, ServerInfoRequest.class);
			registry.register(EnumProtocol.STATUS, 0x01, Ping.class);
			registry.register(EnumProtocol.PLAY, 0x00, TeleportAccept.class);
			registry.register(EnumProtocol.PLAY, 0x01, TabComplete.class);
			registry.register(EnumProtocol.PLAY, 0x02, Chat.class);
			registry.register(EnumProtocol.PLAY, 0x03, ClientCommand.class);
			registry.register(EnumProtocol.PLAY, 0x04, ClientSettings.class);
			registry.register(EnumProtocol.PLAY, 0x05, InventoryTransaction.class);
			registry.register(EnumProtocol.PLAY, 0x06, InventoryEnchant.class);
			registry.register(EnumProtocol.PLAY, 0x07, InventoryClick.class);
			registry.register(EnumProtocol.PLAY, 0x08, InventoryClose.class);
			registry.register(EnumProtocol.PLAY, 0x09, CustomPayload.class);
			registry.register(EnumProtocol.PLAY, 0x0A, UseEntity.class);
			registry.register(EnumProtocol.PLAY, 0x0B, KeepAlive.class);
			registry.register(EnumProtocol.PLAY, 0x0C, Position.class);
			registry.register(EnumProtocol.PLAY, 0x0D, PositionLook.class);
			registry.register(EnumProtocol.PLAY, 0x0E, Look.class);
			registry.register(EnumProtocol.PLAY, 0x0F, Flying.class);
			registry.register(EnumProtocol.PLAY, 0x10, MoveVehicle.class);
			registry.register(EnumProtocol.PLAY, 0x11, SteerBoat.class);
			registry.register(EnumProtocol.PLAY, 0x12, PlayerAbilities.class);
			registry.register(EnumProtocol.PLAY, 0x13, BlockDig.class);
			registry.register(EnumProtocol.PLAY, 0x14, EntityAction.class);
			registry.register(EnumProtocol.PLAY, 0x15, SteerVehicle.class);
			registry.register(EnumProtocol.PLAY, 0x16, ResourcePackStatus.class);
			registry.register(EnumProtocol.PLAY, 0x17, HeldSlot.class);
			registry.register(EnumProtocol.PLAY, 0x18, CreativeSetSlot.class);
			registry.register(EnumProtocol.PLAY, 0x19, UpdateSign.class);
			registry.register(EnumProtocol.PLAY, 0x1A, Animation.class);
			registry.register(EnumProtocol.PLAY, 0x1B, Spectate.class);
			registry.register(EnumProtocol.PLAY, 0x1C, BlockPlace.class);
			registry.register(EnumProtocol.PLAY, 0x1D, UseItem.class);
			registry.setCallBack(new InitCallBack<ServerBoundMiddlePacket>() {
				@Override
				public void onInit(ServerBoundMiddlePacket object) {
					object.setSharedStorage(sharedstorage);
				}
			});
		} catch (Throwable t) {
			SneakyThrow.sneaky(t);
		}
	}

	protected final SharedStorage sharedstorage;
	private final ProtocolSupportPacketDataSerializer serializer;
	public PacketDecoder(ProtocolVersion version, SharedStorage sharedstorage) {
		this.sharedstorage = sharedstorage;
		this.serializer = new ProtocolSupportPacketDataSerializer(null, version);
	}

	@Override
	public void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
		if (!input.isReadable()) {
			return;
		}
		serializer.setBuf(input);
		int packetId = serializer.readVarInt();
		Channel channel = ctx.channel();
		ServerBoundMiddlePacket packetTransformer = registry.getTransformer(channel.attr(ChannelUtils.CURRENT_PROTOCOL_KEY).get(), packetId);
		if (packetTransformer != null) {
			if (packetTransformer.needsPlayer()) {
				packetTransformer.setPlayer(ChannelUtils.getBukkitPlayer(channel));
			}
			packetTransformer.readFromClientData(serializer);
			PacketCreator.addAllToR(packetTransformer.toNative(), list);
		} else {
			throw new DecoderException("Missing packet decoder for packet " + packetId);
		}
		if (serializer.isReadable()) {
			throw new DecoderException("Did not read all data from packet " + packetId+ ", bytes left: " + serializer.readableBytes());
		}
	}

}
