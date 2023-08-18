package protocolsupport.protocol.pipeline.version.v_1_8;

import protocolsupport.api.utils.NetworkState;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.serverbound.handshake.v_7__20.SetProtocol;
import protocolsupport.protocol.packet.middle.impl.serverbound.login.v_7__18.LoginStart;
import protocolsupport.protocol.packet.middle.impl.serverbound.login.v_8__20.EncryptionResponse;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__16r2.InventoryClick;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__16r2.InventoryClose;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__16r2.InventoryConfirmTransaction;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__20.CreativeSetSlot;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__20.Ground;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__20.HeldSlot;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__20.InventoryButton;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__20.Look;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_6__15.PlayerAbilities;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_7__20.Chat;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_7__20.ClientCommand;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_8.Animation;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_8.BlockDig;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_8.BlockPlace;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_8.ClientSettings;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_8.PositionLook;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_8.TabComplete;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_8.UseEntity;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_8__12r2.CustomPayload;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_8__13.UpdateSign;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_8__20.EntityAction;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_8__20.KeepAlive;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_8__20.Move;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_8__20.Spectate;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_8__20.SteerVehicle;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_8__9r2.ResourcePackStatus;
import protocolsupport.protocol.packet.middle.impl.serverbound.status.v_7__20.Ping;
import protocolsupport.protocol.packet.middle.impl.serverbound.status.v_7__20.ServerInfoRequest;
import protocolsupport.protocol.pipeline.IPacketDataChannelIO;
import protocolsupport.protocol.pipeline.version.util.decoder.AbstractModernPacketDecoder;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;

public class PacketDecoder extends AbstractModernPacketDecoder<IServerboundMiddlePacketV8> {

	public PacketDecoder(IPacketDataChannelIO io, NetworkDataCache cache) {
		super(io, cache);
		registry.register(NetworkState.HANDSHAKING, 0x00, SetProtocol::new);
		registry.register(NetworkState.LOGIN, 0x00, LoginStart::new);
		registry.register(NetworkState.LOGIN, 0x01, EncryptionResponse::new);
		registry.register(NetworkState.STATUS, 0x00, ServerInfoRequest::new);
		registry.register(NetworkState.STATUS, 0x01, Ping::new);
		registry.register(NetworkState.PLAY, 0x00, KeepAlive::new);
		registry.register(NetworkState.PLAY, 0x01, Chat::new);
		registry.register(NetworkState.PLAY, 0x02, UseEntity::new);
		registry.register(NetworkState.PLAY, 0x03, Ground::new);
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
		registry.register(NetworkState.PLAY, 0x0F, InventoryConfirmTransaction::new);
		registry.register(NetworkState.PLAY, 0x10, CreativeSetSlot::new);
		registry.register(NetworkState.PLAY, 0x11, InventoryButton::new);
		registry.register(NetworkState.PLAY, 0x12, UpdateSign::new);
		registry.register(NetworkState.PLAY, 0x13, PlayerAbilities::new);
		registry.register(NetworkState.PLAY, 0x14, TabComplete::new);
		registry.register(NetworkState.PLAY, 0x15, ClientSettings::new);
		registry.register(NetworkState.PLAY, 0x16, ClientCommand::new);
		registry.register(NetworkState.PLAY, 0x17, CustomPayload::new);
		registry.register(NetworkState.PLAY, 0x18, Spectate::new);
		registry.register(NetworkState.PLAY, 0x19, ResourcePackStatus::new);
	}

}
