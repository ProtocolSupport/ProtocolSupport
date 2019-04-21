package protocolsupport.protocol.pipeline.version.v_beta;

import protocolsupport.api.utils.NetworkState;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_beta.ClientHandshake;
import protocolsupport.protocol.packet.middleimpl.serverbound.login.v_beta.LoginStart;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_beta.Animation;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_beta.BlockDig;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_beta.BlockPlace;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_beta.Chat;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_beta.ClientCommand;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_beta.EntityAction;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_beta.Flying;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_beta.HeldSlot;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_beta.InventoryClick;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_beta.InventoryClose;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_beta.InventoryTransaction;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_beta.KeepAlive;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_beta.KickDisconnect;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_beta.Look;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_beta.Move;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_beta.MoveLook;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_beta.UseEntity;
import protocolsupport.protocol.pipeline.version.util.decoder.AbstractLegacyPacketDecoder;

public class PacketDecoder extends AbstractLegacyPacketDecoder {

	{
		registry.register(NetworkState.HANDSHAKING, 0x02, ClientHandshake::new);
		registry.register(NetworkState.LOGIN, 0x01, LoginStart::new);
		registry.register(NetworkState.PLAY, 0x00, KeepAlive::new);
		registry.register(NetworkState.PLAY, 0x03, Chat::new);
		registry.register(NetworkState.PLAY, 0x07, UseEntity::new);
		registry.register(NetworkState.PLAY, 0x09, ClientCommand::new);
		registry.register(NetworkState.PLAY, 0x0A, Flying::new);
		registry.register(NetworkState.PLAY, 0x0B, Move::new);
		registry.register(NetworkState.PLAY, 0x0C, Look::new);
		registry.register(NetworkState.PLAY, 0x0D, MoveLook::new);
		registry.register(NetworkState.PLAY, 0x0E, BlockDig::new);
		registry.register(NetworkState.PLAY, 0x0F, BlockPlace::new);
		registry.register(NetworkState.PLAY, 0x10, HeldSlot::new);
		registry.register(NetworkState.PLAY, 0x12, Animation::new);
		registry.register(NetworkState.PLAY, 0x13, EntityAction::new);
		registry.register(NetworkState.PLAY, 0x65, InventoryClose::new);
		registry.register(NetworkState.PLAY, 0x66, InventoryClick::new);
		registry.register(NetworkState.PLAY, 0x6A, InventoryTransaction::new);
//		registry.register(NetworkState.PLAY, 0x82, UpdateSign::new);
		registry.register(NetworkState.PLAY, 0xFF, KickDisconnect::new);
	}

	public PacketDecoder(ConnectionImpl connection) {
		super(connection);
	}

}
