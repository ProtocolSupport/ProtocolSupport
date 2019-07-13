package protocolsupport.protocol.pipeline.version.v_1_6;

import protocolsupport.api.utils.NetworkState;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_4_5_6.ClientLogin;
import protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_6.Ping;
import protocolsupport.protocol.packet.middleimpl.serverbound.login.v_4_5_6_7.EncryptionResponse;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6.ClientCommand;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6.ClientSettings;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6.CustomPayload;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6.KickDisconnect;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6.UseEntity;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7.Animation;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7.BlockDig;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7.BlockPlace;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7.KeepAlive;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7.Move;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7.TabComplete;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7.UpdateSign;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14.Chat;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14.CreativeSetSlot;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14.Flying;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14.HeldSlot;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14.InventoryButton;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14.InventoryClick;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14.InventoryClose;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14.InventoryTransaction;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14.Look;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_6_7.EntityAction;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_6_7.MoveLook;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_6_7.SteerVehicle;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14.PlayerAbilities;
import protocolsupport.protocol.pipeline.version.util.decoder.AbstractLegacyPacketDecoder;

public class PacketDecoder extends AbstractLegacyPacketDecoder {

	{
		registry.register(NetworkState.HANDSHAKING, 0x02, ClientLogin::new);
		registry.register(NetworkState.HANDSHAKING, 0xFE, Ping::new);
		registry.register(NetworkState.LOGIN, 0xFC, EncryptionResponse::new);
		registry.register(NetworkState.PLAY, 0x00, KeepAlive::new);
		registry.register(NetworkState.PLAY, 0x03, Chat::new);
		registry.register(NetworkState.PLAY, 0x07, UseEntity::new);
		registry.register(NetworkState.PLAY, 0x0A, Flying::new);
		registry.register(NetworkState.PLAY, 0x0B, Move::new);
		registry.register(NetworkState.PLAY, 0x0C, Look::new);
		registry.register(NetworkState.PLAY, 0x0D, MoveLook::new);
		registry.register(NetworkState.PLAY, 0x0E, BlockDig::new);
		registry.register(NetworkState.PLAY, 0x0F, BlockPlace::new);
		registry.register(NetworkState.PLAY, 0x10, HeldSlot::new);
		registry.register(NetworkState.PLAY, 0x12, Animation::new);
		registry.register(NetworkState.PLAY, 0x13, EntityAction::new);
		registry.register(NetworkState.PLAY, 0x1B, SteerVehicle::new);
		registry.register(NetworkState.PLAY, 0x65, InventoryClose::new);
		registry.register(NetworkState.PLAY, 0x66, InventoryClick::new);
		registry.register(NetworkState.PLAY, 0x6A, InventoryTransaction::new);
		registry.register(NetworkState.PLAY, 0x6B, CreativeSetSlot::new);
		registry.register(NetworkState.PLAY, 0x6C, InventoryButton::new);
		registry.register(NetworkState.PLAY, 0x82, UpdateSign::new);
		registry.register(NetworkState.PLAY, 0xCB, TabComplete::new);
		registry.register(NetworkState.PLAY, 0xCA, PlayerAbilities::new);
		registry.register(NetworkState.PLAY, 0xCC, ClientSettings::new);
		registry.register(NetworkState.PLAY, 0xCD, ClientCommand::new);
		registry.register(NetworkState.PLAY, 0xFA, CustomPayload::new);
		registry.register(NetworkState.PLAY, 0xFF, KickDisconnect::new);
	}

	public PacketDecoder(ConnectionImpl connection) {
		super(connection);
	}

}
