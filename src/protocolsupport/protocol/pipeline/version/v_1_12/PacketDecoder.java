package protocolsupport.protocol.pipeline.version.v_1_12;

import protocolsupport.api.Connection;
import protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_1_7__1_8__1_9_r1__1_9_r2__1_10__1_11.SetProtocol;
import protocolsupport.protocol.packet.middleimpl.serverbound.login.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10__1_11.EncryptionResponse;
import protocolsupport.protocol.packet.middleimpl.serverbound.login.v_1_7__1_8__1_9_r1__1_9_r2__1_10__1_11.LoginStart;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v1_9_r1__1_9_r2__1_10__1_11.Animation;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v1_9_r1__1_9_r2__1_10__1_11.ClientSettings;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v1_9_r1__1_9_r2__1_10__1_11.MoveVehicle;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v1_9_r1__1_9_r2__1_10__1_11.SteerBoat;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v1_9_r1__1_9_r2__1_10__1_11.TabComplete;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v1_9_r1__1_9_r2__1_10__1_11.TeleportAccept;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v1_9_r1__1_9_r2__1_10__1_11.UseEntity;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v1_9_r1__1_9_r2__1_10__1_11.UseItem;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_10__1_11.ResourcePackStatus;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_11.BlockPlace;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10__1_11.Chat;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10__1_11.CreativeSetSlot;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10__1_11.Flying;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10__1_11.HeldSlot;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10__1_11.InventoryClick;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10__1_11.InventoryClose;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10__1_11.InventoryEnchant;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10__1_11.InventoryTransaction;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10__1_11.Look;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_6__1_7__1_8__1_9_r1__1_9_r2__1_10__1_11.PlayerAbilities;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_7__1_8__1_9_r1__1_9_r2__1_10__1_11.ClientCommand;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.BlockDig;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.CustomPayload;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.EntityAction;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.KeepAlive;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.Position;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.PositionLook;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.Spectate;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.SteerVehicle;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.UpdateSign;
import protocolsupport.protocol.packet.middleimpl.serverbound.status.v_1_7__1_8__1_9_r1__1_9_r2__1_10__1_11.Ping;
import protocolsupport.protocol.packet.middleimpl.serverbound.status.v_1_7__1_8__1_9_r1__1_9_r2__1_10__1_11.ServerInfoRequest;
import protocolsupport.protocol.pipeline.version.AbstractModernWithoutReorderPacketDecoder;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.zplatform.network.NetworkState;

public class PacketDecoder extends AbstractModernWithoutReorderPacketDecoder {

	{
		registry.register(NetworkState.HANDSHAKING, 0x00, SetProtocol.class);
		registry.register(NetworkState.LOGIN, 0x00, LoginStart.class);
		registry.register(NetworkState.LOGIN, 0x01, EncryptionResponse.class);
		registry.register(NetworkState.STATUS, 0x00, ServerInfoRequest.class);
		registry.register(NetworkState.STATUS, 0x01, Ping.class);
		registry.register(NetworkState.PLAY, 0x00, TeleportAccept.class);
		registry.register(NetworkState.PLAY, 0x02, TabComplete.class);
		registry.register(NetworkState.PLAY, 0x03, Chat.class);
		registry.register(NetworkState.PLAY, 0x04, ClientCommand.class);
		registry.register(NetworkState.PLAY, 0x05, ClientSettings.class);
		registry.register(NetworkState.PLAY, 0x06, InventoryTransaction.class);
		registry.register(NetworkState.PLAY, 0x07, InventoryEnchant.class);
		registry.register(NetworkState.PLAY, 0x08, InventoryClick.class);
		registry.register(NetworkState.PLAY, 0x09, InventoryClose.class);
		registry.register(NetworkState.PLAY, 0x0A, CustomPayload.class);
		registry.register(NetworkState.PLAY, 0x0B, UseEntity.class);
		registry.register(NetworkState.PLAY, 0x0C, KeepAlive.class);
		registry.register(NetworkState.PLAY, 0x0D, Position.class);
		registry.register(NetworkState.PLAY, 0x0E, PositionLook.class);
		registry.register(NetworkState.PLAY, 0x0F, Look.class);
		registry.register(NetworkState.PLAY, 0x10, Flying.class);
		registry.register(NetworkState.PLAY, 0x11, MoveVehicle.class);
		registry.register(NetworkState.PLAY, 0x12, SteerBoat.class);
		registry.register(NetworkState.PLAY, 0x13, PlayerAbilities.class);
		registry.register(NetworkState.PLAY, 0x14, BlockDig.class);
		registry.register(NetworkState.PLAY, 0x15, EntityAction.class);
		registry.register(NetworkState.PLAY, 0x16, SteerVehicle.class);
		registry.register(NetworkState.PLAY, 0x18, ResourcePackStatus.class);
		registry.register(NetworkState.PLAY, 0x19, HeldSlot.class);
		registry.register(NetworkState.PLAY, 0x1A, CreativeSetSlot.class);
		registry.register(NetworkState.PLAY, 0x1B, UpdateSign.class);
		registry.register(NetworkState.PLAY, 0x1C, Animation.class);
		registry.register(NetworkState.PLAY, 0x1D, Spectate.class);
		registry.register(NetworkState.PLAY, 0x1E, BlockPlace.class);
		registry.register(NetworkState.PLAY, 0x1F, UseItem.class);
	}

	public PacketDecoder(Connection connection, NetworkDataCache sharedstorage) {
		super(connection, sharedstorage);
	}

}
