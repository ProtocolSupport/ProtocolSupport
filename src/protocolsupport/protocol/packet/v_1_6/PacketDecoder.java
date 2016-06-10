package protocolsupport.protocol.packet.v_1_6;

import java.util.List;

import org.spigotmc.SneakyThrow;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import net.minecraft.server.v1_10_R1.EnumProtocol;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.LegacyAnimatePacketReorderer;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.PacketCreator;
import protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_1_4__1_5__1_6.ClientLogin;
import protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_1_6.Ping;
import protocolsupport.protocol.packet.middleimpl.serverbound.login.v_1_4__1_5__1_6__1_7__1_8.EncryptionResponse;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6.ClientCommand;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6.ClientSettings;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6.CustomPayload;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6.KickDisconnect;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6.UseEntity;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7.Animation;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7.BlockDig;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7.BlockPlace;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7.KeepAlive;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7.Position;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7.TabComplete;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7.UpdateSign;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8.Chat;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8.CreativeSetSlot;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8.Flying;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8.HeldSlot;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8.InventoryClick;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8.InventoryClose;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8.InventoryEnchant;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8.InventoryTransaction;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8.Look;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_6__1_7.EntityAction;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_6__1_7.PositionLook;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_6__1_7.SteerVehicle;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_6__1_7__1_8.PlayerAbilities;
import protocolsupport.protocol.pipeline.IPacketDecoder;
import protocolsupport.protocol.serializer.ReplayingSupportPacketDataSerializer;
import protocolsupport.protocol.storage.SharedStorage;
import protocolsupport.protocol.utils.registry.MiddleTransformerRegistry;
import protocolsupport.protocol.utils.registry.MiddleTransformerRegistry.InitCallBack;
import protocolsupport.utils.netty.ChannelUtils;
import protocolsupport.utils.netty.ReplayingDecoderBuffer.EOFSignal;

public class PacketDecoder implements IPacketDecoder {

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
			dataRemapperRegistry.setCallBack(new InitCallBack<ServerBoundMiddlePacket>() {
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
	private final ReplayingSupportPacketDataSerializer serializer;
	public PacketDecoder(ProtocolVersion version, SharedStorage sharedstorage) {
		this.serializer = new ReplayingSupportPacketDataSerializer(version);
		this.sharedstorage = sharedstorage;
	}

	private final LegacyAnimatePacketReorderer reorderer = new LegacyAnimatePacketReorderer();

	@Override
	public void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
		if (!input.isReadable()) {
			return;
		}
		serializer.setBuf(input);
		serializer.markReaderIndex();
		Channel channel = ctx.channel();
		try {
			int packetId = serializer.readUnsignedByte();
			ServerBoundMiddlePacket packetTransformer = dataRemapperRegistry.getTransformer(channel.attr(ChannelUtils.CURRENT_PROTOCOL_KEY).get(), packetId);
			if (packetTransformer != null) {
				if (packetTransformer.needsPlayer()) {
					packetTransformer.setPlayer(ChannelUtils.getBukkitPlayer(channel));
				}
				packetTransformer.readFromClientData(serializer);
				PacketCreator.addAllTo(reorderer.orderPackets(packetTransformer.toNative()), list);
			} else {
				throw new DecoderException("Missing packet decoder for packet " + packetId);
			}
		} catch (EOFSignal ex) {
			serializer.resetReaderIndex();
		}
	}

}
