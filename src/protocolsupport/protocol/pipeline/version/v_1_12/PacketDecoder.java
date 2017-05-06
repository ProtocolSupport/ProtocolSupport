package protocolsupport.protocol.pipeline.version.v_1_12;

import protocolsupport.api.Connection;
import protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_7_8_9r1_9r2_10_11_12.SetProtocol;
import protocolsupport.protocol.packet.middleimpl.serverbound.login.v_4_5_6_7_8_9r1_9r2_10_11_12.EncryptionResponse;
import protocolsupport.protocol.packet.middleimpl.serverbound.login.v_7_8_9r1_9r2_10_11_12.LoginStart;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_10_11_12.ResourcePackStatus;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_11_12.BlockPlace;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12.Chat;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12.CreativeSetSlot;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12.Flying;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12.HeldSlot;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12.InventoryClick;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12.InventoryClose;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12.InventoryEnchant;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12.InventoryTransaction;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12.Look;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_6_7_8_9r1_9r2_10_11_12.PlayerAbilities;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_7_8_9r1_9r2_10_11_12.ClientCommand;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12.BlockDig;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12.CustomPayload;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12.EntityAction;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12.KeepAlive;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12.Position;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12.PositionLook;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12.Spectate;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12.SteerVehicle;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12.UpdateSign;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11_12.Animation;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11_12.ClientSettings;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11_12.MoveVehicle;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11_12.SteerBoat;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11_12.TabComplete;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11_12.TeleportAccept;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11_12.UseEntity;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11_12.UseItem;
import protocolsupport.protocol.packet.middleimpl.serverbound.status.v_7_8_9r1_9r2_10_11_12.Ping;
import protocolsupport.protocol.packet.middleimpl.serverbound.status.v_7_8_9r1_9r2_10_11_12.ServerInfoRequest;
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
