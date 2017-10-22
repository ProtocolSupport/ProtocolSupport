package protocolsupport.protocol.pipeline.version.v_1_8;

import protocolsupport.api.Connection;
import protocolsupport.api.utils.NetworkState;
import protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_7_8_9r1_9r2_10_11_12r1_12r2.SetProtocol;
import protocolsupport.protocol.packet.middleimpl.serverbound.login.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2.EncryptionResponse;
import protocolsupport.protocol.packet.middleimpl.serverbound.login.v_7_8_9r1_9r2_10_11_12r1_12r2.LoginStart;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2.Chat;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2.CreativeSetSlot;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2.Flying;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2.HeldSlot;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2.InventoryClick;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2.InventoryClose;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2.InventoryEnchant;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2.InventoryTransaction;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2.Look;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_6_7_8_9r1_9r2_10_11_12r1_12r2.PlayerAbilities;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_7_8_9r1_9r2_10_11_12r1_12r2.ClientCommand;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8.Animation;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8.BlockDig;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8.BlockPlace;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8.ClientSettings;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8.PositionLook;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8.TabComplete;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8.UseEntity;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2.ResourcePackStatus;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2.CustomPayload;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2.EntityAction;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2.KeepAlive;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2.Position;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2.Spectate;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2.SteerVehicle;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2.UpdateSign;
import protocolsupport.protocol.packet.middleimpl.serverbound.status.v_7_8_9r1_9r2_10_11_12r1_12r2.Ping;
import protocolsupport.protocol.packet.middleimpl.serverbound.status.v_7_8_9r1_9r2_10_11_12r1_12r2.ServerInfoRequest;
import protocolsupport.protocol.pipeline.version.AbstractModernWithReorderPacketDecoder;
import protocolsupport.protocol.storage.NetworkDataCache;

public class PacketDecoder extends AbstractModernWithReorderPacketDecoder {

	{
		registry.register(NetworkState.HANDSHAKING, 0x00, SetProtocol.class);
		registry.register(NetworkState.LOGIN, 0x00, LoginStart.class);
		registry.register(NetworkState.LOGIN, 0x01, EncryptionResponse.class);
		registry.register(NetworkState.STATUS, 0x00, ServerInfoRequest.class);
		registry.register(NetworkState.STATUS, 0x01, Ping.class);
		registry.register(NetworkState.PLAY, 0x00, KeepAlive.class);
		registry.register(NetworkState.PLAY, 0x01, Chat.class);
		registry.register(NetworkState.PLAY, 0x02, UseEntity.class);
		registry.register(NetworkState.PLAY, 0x03, Flying.class);
		registry.register(NetworkState.PLAY, 0x04, Position.class);
		registry.register(NetworkState.PLAY, 0x05, Look.class);
		registry.register(NetworkState.PLAY, 0x06, PositionLook.class);
		registry.register(NetworkState.PLAY, 0x07, BlockDig.class);
		registry.register(NetworkState.PLAY, 0x08, BlockPlace.class);
		registry.register(NetworkState.PLAY, 0x09, HeldSlot.class);
		registry.register(NetworkState.PLAY, 0x0A, Animation.class);
		registry.register(NetworkState.PLAY, 0x0B, EntityAction.class);
		registry.register(NetworkState.PLAY, 0x0C, SteerVehicle.class);
		registry.register(NetworkState.PLAY, 0x0D, InventoryClose.class);
		registry.register(NetworkState.PLAY, 0x0E, InventoryClick.class);
		registry.register(NetworkState.PLAY, 0x0F, InventoryTransaction.class);
		registry.register(NetworkState.PLAY, 0x10, CreativeSetSlot.class);
		registry.register(NetworkState.PLAY, 0x11, InventoryEnchant.class);
		registry.register(NetworkState.PLAY, 0x12, UpdateSign.class);
		registry.register(NetworkState.PLAY, 0x13, PlayerAbilities.class);
		registry.register(NetworkState.PLAY, 0x14, TabComplete.class);
		registry.register(NetworkState.PLAY, 0x15, ClientSettings.class);
		registry.register(NetworkState.PLAY, 0x16, ClientCommand.class);
		registry.register(NetworkState.PLAY, 0x17, CustomPayload.class);
		registry.register(NetworkState.PLAY, 0x18, Spectate.class);
		registry.register(NetworkState.PLAY, 0x19, ResourcePackStatus.class);
	}

	public PacketDecoder(Connection connection, NetworkDataCache sharedstorage) {
		super(connection, sharedstorage);
	}

}
