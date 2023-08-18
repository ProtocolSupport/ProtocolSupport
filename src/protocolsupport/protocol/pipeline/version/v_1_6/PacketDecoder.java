package protocolsupport.protocol.pipeline.version.v_1_6;

import protocolsupport.api.utils.NetworkState;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.serverbound.handshake.v_4_5_6.ClientLogin;
import protocolsupport.protocol.packet.middle.impl.serverbound.handshake.v_6.Ping;
import protocolsupport.protocol.packet.middle.impl.serverbound.login.v_4__7.EncryptionResponse;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__16r2.InventoryClick;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__16r2.InventoryClose;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__16r2.InventoryConfirmTransaction;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__20.CreativeSetSlot;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__20.Ground;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__20.HeldSlot;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__20.InventoryButton;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__20.Look;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__6.Chat;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__6.ClientCommand;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__6.ClientSettings;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__6.CustomPayload;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__6.KickDisconnect;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__6.TabComplete;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__6.UpdateSign;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__6.UseEntity;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__7.Animation;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__7.BlockDig;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__7.BlockPlace;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__7.KeepAlive;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__7.Move;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_6__15.PlayerAbilities;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_6__7.EntityAction;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_6__7.MoveLook;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_6__7.SteerVehicle;
import protocolsupport.protocol.pipeline.IPacketDataChannelIO;
import protocolsupport.protocol.pipeline.version.util.decoder.AbstractLegacyPacketDecoder;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;

public class PacketDecoder extends AbstractLegacyPacketDecoder<IServerboundMiddlePacketV6> {

	public PacketDecoder(IPacketDataChannelIO io, NetworkDataCache cache) {
		super(io, cache);
		registry.register(NetworkState.HANDSHAKING, 0x02, ClientLogin::new);
		registry.register(NetworkState.HANDSHAKING, 0xFE, Ping::new);
		registry.register(NetworkState.LOGIN, 0xFC, EncryptionResponse::new);
		registry.register(NetworkState.PLAY, 0x00, KeepAlive::new);
		registry.register(NetworkState.PLAY, 0x03, Chat::new);
		registry.register(NetworkState.PLAY, 0x07, UseEntity::new);
		registry.register(NetworkState.PLAY, 0x0A, Ground::new);
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
		registry.register(NetworkState.PLAY, 0x6A, InventoryConfirmTransaction::new);
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

}
