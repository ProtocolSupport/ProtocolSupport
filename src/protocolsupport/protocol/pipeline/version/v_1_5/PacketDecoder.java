package protocolsupport.protocol.pipeline.version.v_1_5;

import protocolsupport.api.Connection;
import protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_1_4__1_5__1_6.ClientLogin;
import protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_1_5.Ping;
import protocolsupport.protocol.packet.middleimpl.serverbound.login.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10.EncryptionResponse;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5.EntityAction;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5.PlayerAbilities;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5.PositionLook;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6.ClientCommand;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6.ClientSettings;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6.CustomPayload;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6.KickDisconnect;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6.UseEntity;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7.Animation;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7.BlockDig;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7.BlockPlace;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7.KeepAlive;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7.Position;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7.TabComplete;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7.UpdateSign;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10.Chat;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10.CreativeSetSlot;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10.Flying;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10.HeldSlot;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10.InventoryClick;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10.InventoryClose;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10.InventoryEnchant;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10.InventoryTransaction;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10.Look;
import protocolsupport.protocol.pipeline.version.AbstractLegacyPacketDecoder;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.protocol.utils.types.NetworkListenerState;

public class PacketDecoder extends AbstractLegacyPacketDecoder {

	{
		registry.register(NetworkListenerState.HANDSHAKING, 0x02, ClientLogin.class);
		registry.register(NetworkListenerState.HANDSHAKING, 0xFE, Ping.class);
		registry.register(NetworkListenerState.LOGIN, 0xFC, EncryptionResponse.class);
		registry.register(NetworkListenerState.PLAY, 0x00, KeepAlive.class);
		registry.register(NetworkListenerState.PLAY, 0x03, Chat.class);
		registry.register(NetworkListenerState.PLAY, 0x07, UseEntity.class);
		registry.register(NetworkListenerState.PLAY, 0x0A, Flying.class);
		registry.register(NetworkListenerState.PLAY, 0x0B, Position.class);
		registry.register(NetworkListenerState.PLAY, 0x0C, Look.class);
		registry.register(NetworkListenerState.PLAY, 0x0D, PositionLook.class);
		registry.register(NetworkListenerState.PLAY, 0x0E, BlockDig.class);
		registry.register(NetworkListenerState.PLAY, 0x0F, BlockPlace.class);
		registry.register(NetworkListenerState.PLAY, 0x10, HeldSlot.class);
		registry.register(NetworkListenerState.PLAY, 0x12, Animation.class);
		registry.register(NetworkListenerState.PLAY, 0x13, EntityAction.class);
		registry.register(NetworkListenerState.PLAY, 0x65, InventoryClose.class);
		registry.register(NetworkListenerState.PLAY, 0x66, InventoryClick.class);
		registry.register(NetworkListenerState.PLAY, 0x6A, InventoryTransaction.class);
		registry.register(NetworkListenerState.PLAY, 0x6B, CreativeSetSlot.class);
		registry.register(NetworkListenerState.PLAY, 0x6C, InventoryEnchant.class);
		registry.register(NetworkListenerState.PLAY, 0x82, UpdateSign.class);
		registry.register(NetworkListenerState.PLAY, 0xCB, TabComplete.class);
		registry.register(NetworkListenerState.PLAY, 0xCA, PlayerAbilities.class);
		registry.register(NetworkListenerState.PLAY, 0xCC, ClientSettings.class);
		registry.register(NetworkListenerState.PLAY, 0xCD, ClientCommand.class);
		registry.register(NetworkListenerState.PLAY, 0xFA, CustomPayload.class);
		registry.register(NetworkListenerState.PLAY, 0xFF, KickDisconnect.class);
	}

	public PacketDecoder(Connection connection, NetworkDataCache storage) {
		super(connection, storage);
	}

}
