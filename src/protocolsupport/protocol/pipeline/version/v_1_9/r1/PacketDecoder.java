package protocolsupport.protocol.pipeline.version.v_1_9.r1;

import protocolsupport.api.utils.NetworkState;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV9r1;
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
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_8__12r2.CustomPayload;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_8__13.UpdateSign;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_8__20.EntityAction;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_8__20.KeepAlive;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_8__20.Move;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_8__20.Spectate;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_8__20.SteerVehicle;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_8__9r2.ResourcePackStatus;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_9r1__10.BlockPlace;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_9r1__12r2.TabComplete;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_9r1__13.BlockDig;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_9r1__15.UseEntity;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_9r1__16r2.ClientSettings;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_9r1__18.UseItem;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_9r1__20.Animation;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_9r1__20.MoveLook;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_9r1__20.MoveVehicle;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_9r1__20.SteerBoat;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_9r1__20.TeleportAccept;
import protocolsupport.protocol.packet.middle.impl.serverbound.status.v_7__20.Ping;
import protocolsupport.protocol.packet.middle.impl.serverbound.status.v_7__20.ServerInfoRequest;
import protocolsupport.protocol.pipeline.IPacketDataChannelIO;
import protocolsupport.protocol.pipeline.version.util.decoder.AbstractModernPacketDecoder;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;

public class PacketDecoder extends AbstractModernPacketDecoder<IServerboundMiddlePacketV9r1> {

	public PacketDecoder(IPacketDataChannelIO io, NetworkDataCache cache) {
		super(io, cache);
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
		registry.register(NetworkState.PLAY, 0x05, InventoryConfirmTransaction::new);
		registry.register(NetworkState.PLAY, 0x06, InventoryButton::new);
		registry.register(NetworkState.PLAY, 0x07, InventoryClick::new);
		registry.register(NetworkState.PLAY, 0x08, InventoryClose::new);
		registry.register(NetworkState.PLAY, 0x09, CustomPayload::new);
		registry.register(NetworkState.PLAY, 0x0A, UseEntity::new);
		registry.register(NetworkState.PLAY, 0x0B, KeepAlive::new);
		registry.register(NetworkState.PLAY, 0x0C, Move::new);
		registry.register(NetworkState.PLAY, 0x0D, MoveLook::new);
		registry.register(NetworkState.PLAY, 0x0E, Look::new);
		registry.register(NetworkState.PLAY, 0x0F, Ground::new);
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

}
