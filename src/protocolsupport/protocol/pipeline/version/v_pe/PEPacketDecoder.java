package protocolsupport.protocol.pipeline.version.v_pe;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import protocolsupport.api.Connection;
import protocolsupport.api.utils.NetworkState;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_pe.ClientLogin;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe.Animation;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe.BlockTileUpdate;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe.BookEdit;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe.Chat;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe.ClientSettings;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe.CommandRequest;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe.EntityStatus;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe.HeldSlot;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe.Interact;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe.InventoryClose;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe.GodPacket;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe.MapInfoRequest;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe.MoveVehicle;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe.PlayerAction;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe.PositionLook;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe.RiderJump;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe.SteerVehicle;
import protocolsupport.protocol.pipeline.version.AbstractPacketDecoder;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.zplatform.ServerPlatform;

public class PEPacketDecoder extends AbstractPacketDecoder {

	{
		for (int i = 0; i < 255; i++) {
			registry.register(NetworkState.PLAY, i, Noop.class);
		}
		registry.register(NetworkState.HANDSHAKING, PEPacketIDs.LOGIN, ClientLogin.class);
		registry.register(NetworkState.PLAY, PEPacketIDs.CLIENT_SETTINGS, ClientSettings.class);
		registry.register(NetworkState.PLAY, PEPacketIDs.PLAYER_MOVE, PositionLook.class);
		registry.register(NetworkState.PLAY, PEPacketIDs.PLAYER_ACTION, PlayerAction.class);
		registry.register(NetworkState.PLAY, PEPacketIDs.PLAYER_STEER, SteerVehicle.class);
		registry.register(NetworkState.PLAY, PEPacketIDs.ENTITY_TELEPORT, MoveVehicle.class);
		registry.register(NetworkState.PLAY, PEPacketIDs.CHAT, Chat.class);
		registry.register(NetworkState.PLAY, PEPacketIDs.ANIMATION, Animation.class);
		registry.register(NetworkState.PLAY, PEPacketIDs.INTERACT, Interact.class);
		registry.register(NetworkState.PLAY, PEPacketIDs.COMMAND_REQUEST, CommandRequest.class);
		registry.register(NetworkState.PLAY, PEPacketIDs.TILE_DATA_UPDATE, BlockTileUpdate.class);
		registry.register(NetworkState.PLAY, PEPacketIDs.MOB_EQUIPMENT, HeldSlot.class);
		registry.register(NetworkState.PLAY, PEPacketIDs.CONTAINER_CLOSE, InventoryClose.class);
		registry.register(NetworkState.PLAY, PEPacketIDs.ENTITY_EVENT, EntityStatus.class);
		registry.register(NetworkState.PLAY, PEPacketIDs.INVENTORY_TRANSACTION, GodPacket.class);
		registry.register(NetworkState.PLAY, PEPacketIDs.MAP_INFO_REQUEST, MapInfoRequest.class);
		registry.register(NetworkState.PLAY, PEPacketIDs.EDIT_BOOK, BookEdit.class);
		registry.register(NetworkState.PLAY, PEPacketIDs.RIDER_JUMP, RiderJump.class);
	}

	public PEPacketDecoder(Connection connection, NetworkDataCache cache) {
		super(connection, cache);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
		if (!input.isReadable()) {
			return;
		}
		ServerBoundMiddlePacket packetTransformer = null;
		try {
			packetTransformer = registry.getTransformer(
				connection.getNetworkState(),
				readPacketId(input)
			);
			packetTransformer.readFromClientData(input);
			if (input.isReadable()) {
				throw new DecoderException("Did not read all data from packet " + packetTransformer.getClass().getName() + ", bytes left: " + input.readableBytes());
			}
			RecyclableCollection<ServerBoundPacketData> packets = packetTransformer.toNative();
			if (cache.getPESendPacketMovementConfirmQueue().checkMovement(packets)) {
				ctx.channel().eventLoop().execute(() -> connection.sendPacket(ServerPlatform.get().getPacketFactory().createEmptyCustomPayloadPacket("PS|PushQueue")));
			}
			addPackets(packets, list);
		} catch (Exception e) {
			throwFailedTransformException(e, packetTransformer, input);
		}
	}

	protected int readPacketId(ByteBuf from) {
		int id = VarNumberSerializer.readVarInt(from);
		from.readByte();
		from.readByte();
		return id;
	}

	public static class Noop extends ServerBoundMiddlePacket {

		@Override
		public void readFromClientData(ByteBuf clientdata) {
			clientdata.skipBytes(clientdata.readableBytes());
		}

		@Override
		public RecyclableCollection<ServerBoundPacketData> toNative() {
			return RecyclableEmptyList.get();
		}

	}

}
