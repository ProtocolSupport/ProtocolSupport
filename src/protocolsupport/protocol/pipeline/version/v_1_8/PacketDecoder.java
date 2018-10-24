package protocolsupport.protocol.pipeline.version.v_1_8;

import protocolsupport.api.utils.NetworkState;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_7_8_9r1_9r2_10_11_12r1_12r2_13.SetProtocol;
import protocolsupport.protocol.packet.middleimpl.serverbound.login.v_7_8_9r1_9r2_10_11_12r1_12r2_13.LoginStart;
import protocolsupport.protocol.packet.middleimpl.serverbound.login.v_8_9r1_9r2_10_11_12r1_12r2_13.EncryptionResponse;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.Chat;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.CreativeSetSlot;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.Flying;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.HeldSlot;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.InventoryClick;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.InventoryClose;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.InventoryEnchant;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.InventoryTransaction;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.Look;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_6_7_8_9r1_9r2_10_11_12r1_12r2_13.PlayerAbilities;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_7_8_9r1_9r2_10_11_12r1_12r2_13.ClientCommand;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8.Animation;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8.BlockDig;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8.BlockPlace;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8.ClientSettings;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8.PositionLook;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8.TabComplete;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8.UseEntity;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2.ResourcePackStatus;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2.CustomPayload;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.EntityAction;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.KeepAlive;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.Move;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.Spectate;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.SteerVehicle;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.UpdateSign;
import protocolsupport.protocol.packet.middleimpl.serverbound.status.v_7_8_9r1_9r2_10_11_12r1_12r2_13.Ping;
import protocolsupport.protocol.packet.middleimpl.serverbound.status.v_7_8_9r1_9r2_10_11_12r1_12r2_13.ServerInfoRequest;
import protocolsupport.protocol.pipeline.version.util.decoder.AbstractModernWithReorderPacketDecoder;

public class PacketDecoder extends AbstractModernWithReorderPacketDecoder {

	{
		registry.register(NetworkState.HANDSHAKING, 0x00, SetProtocol::new);
		registry.register(NetworkState.LOGIN, 0x00, LoginStart::new);
		registry.register(NetworkState.LOGIN, 0x01, EncryptionResponse::new);
		registry.register(NetworkState.STATUS, 0x00, ServerInfoRequest::new);
		registry.register(NetworkState.STATUS, 0x01, Ping::new);
		registry.register(NetworkState.PLAY, 0x00, KeepAlive::new);
		registry.register(NetworkState.PLAY, 0x01, Chat::new);
		registry.register(NetworkState.PLAY, 0x02, UseEntity::new);
		registry.register(NetworkState.PLAY, 0x03, Flying::new);
		registry.register(NetworkState.PLAY, 0x04, Move::new);
		registry.register(NetworkState.PLAY, 0x05, Look::new);
		registry.register(NetworkState.PLAY, 0x06, PositionLook::new);
		registry.register(NetworkState.PLAY, 0x07, BlockDig::new);
		registry.register(NetworkState.PLAY, 0x08, BlockPlace::new);
		registry.register(NetworkState.PLAY, 0x09, HeldSlot::new);
		registry.register(NetworkState.PLAY, 0x0A, Animation::new);
		registry.register(NetworkState.PLAY, 0x0B, EntityAction::new);
		registry.register(NetworkState.PLAY, 0x0C, SteerVehicle::new);
		registry.register(NetworkState.PLAY, 0x0D, InventoryClose::new);
		registry.register(NetworkState.PLAY, 0x0E, InventoryClick::new);
		registry.register(NetworkState.PLAY, 0x0F, InventoryTransaction::new);
		registry.register(NetworkState.PLAY, 0x10, CreativeSetSlot::new);
		registry.register(NetworkState.PLAY, 0x11, InventoryEnchant::new);
		registry.register(NetworkState.PLAY, 0x12, UpdateSign::new);
		registry.register(NetworkState.PLAY, 0x13, PlayerAbilities::new);
		registry.register(NetworkState.PLAY, 0x14, TabComplete::new);
		registry.register(NetworkState.PLAY, 0x15, ClientSettings::new);
		registry.register(NetworkState.PLAY, 0x16, ClientCommand::new);
		registry.register(NetworkState.PLAY, 0x17, CustomPayload::new);
		registry.register(NetworkState.PLAY, 0x18, Spectate::new);
		registry.register(NetworkState.PLAY, 0x19, ResourcePackStatus::new);
	}

	public PacketDecoder(ConnectionImpl connection) {
		super(connection);
	}

}
