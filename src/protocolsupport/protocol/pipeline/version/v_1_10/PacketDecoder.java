package protocolsupport.protocol.pipeline.version.v_1_10;

import protocolsupport.api.utils.NetworkState;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_7_8_9r1_9r2_10_11_12r1_12r2_13.SetProtocol;
import protocolsupport.protocol.packet.middleimpl.serverbound.login.v_7_8_9r1_9r2_10_11_12r1_12r2_13.LoginStart;
import protocolsupport.protocol.packet.middleimpl.serverbound.login.v_8_9r1_9r2_10_11_12r1_12r2_13.EncryptionResponse;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_10_11_12r1_12r2_13.ResourcePackStatus;
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
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2.CustomPayload;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.EntityAction;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.KeepAlive;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.Move;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.Spectate;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.SteerVehicle;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.UpdateSign;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10.BlockPlace;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11_12r1_12r2.TabComplete;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11_12r1_12r2_13.Animation;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11_12r1_12r2_13.BlockDig;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11_12r1_12r2_13.ClientSettings;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11_12r1_12r2_13.MoveLook;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11_12r1_12r2_13.MoveVehicle;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11_12r1_12r2_13.SteerBoat;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11_12r1_12r2_13.TeleportAccept;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11_12r1_12r2_13.UseEntity;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11_12r1_12r2_13.UseItem;
import protocolsupport.protocol.packet.middleimpl.serverbound.status.v_7_8_9r1_9r2_10_11_12r1_12r2_13.Ping;
import protocolsupport.protocol.packet.middleimpl.serverbound.status.v_7_8_9r1_9r2_10_11_12r1_12r2_13.ServerInfoRequest;
import protocolsupport.protocol.pipeline.version.util.decoder.AbstractModernPacketDecoder;

public class PacketDecoder extends AbstractModernPacketDecoder {

	{
		registry.register(NetworkState.HANDSHAKING, 0x00, SetProtocol::new);
		registry.register(NetworkState.LOGIN, 0x00, LoginStart::new);
		registry.register(NetworkState.LOGIN, 0x01, EncryptionResponse::new);
		registry.register(NetworkState.STATUS, 0x00, ServerInfoRequest::new);
		registry.register(NetworkState.STATUS, 0x01, Ping::new);
		registry.register(NetworkState.PLAY, 0x00, TeleportAccept::new);
		registry.register(NetworkState.PLAY, 0x01, TabComplete::new);
		registry.register(NetworkState.PLAY, 0x02, Chat::new);
		registry.register(NetworkState.PLAY, 0x03, ClientCommand::new);
		registry.register(NetworkState.PLAY, 0x04, ClientSettings::new);
		registry.register(NetworkState.PLAY, 0x05, InventoryTransaction::new);
		registry.register(NetworkState.PLAY, 0x06, InventoryEnchant::new);
		registry.register(NetworkState.PLAY, 0x07, InventoryClick::new);
		registry.register(NetworkState.PLAY, 0x08, InventoryClose::new);
		registry.register(NetworkState.PLAY, 0x09, CustomPayload::new);
		registry.register(NetworkState.PLAY, 0x0A, UseEntity::new);
		registry.register(NetworkState.PLAY, 0x0B, KeepAlive::new);
		registry.register(NetworkState.PLAY, 0x0C, Move::new);
		registry.register(NetworkState.PLAY, 0x0D, MoveLook::new);
		registry.register(NetworkState.PLAY, 0x0E, Look::new);
		registry.register(NetworkState.PLAY, 0x0F, Flying::new);
		registry.register(NetworkState.PLAY, 0x10, MoveVehicle::new);
		registry.register(NetworkState.PLAY, 0x11, SteerBoat::new);
		registry.register(NetworkState.PLAY, 0x12, PlayerAbilities::new);
		registry.register(NetworkState.PLAY, 0x13, BlockDig::new);
		registry.register(NetworkState.PLAY, 0x14, EntityAction::new);
		registry.register(NetworkState.PLAY, 0x15, SteerVehicle::new);
		registry.register(NetworkState.PLAY, 0x16, ResourcePackStatus::new);
		registry.register(NetworkState.PLAY, 0x17, HeldSlot::new);
		registry.register(NetworkState.PLAY, 0x18, CreativeSetSlot::new);
		registry.register(NetworkState.PLAY, 0x19, UpdateSign::new);
		registry.register(NetworkState.PLAY, 0x1A, Animation::new);
		registry.register(NetworkState.PLAY, 0x1B, Spectate::new);
		registry.register(NetworkState.PLAY, 0x1C, BlockPlace::new);
		registry.register(NetworkState.PLAY, 0x1D, UseItem::new);
	}

	public PacketDecoder(ConnectionImpl connection) {
		super(connection);
	}

}
