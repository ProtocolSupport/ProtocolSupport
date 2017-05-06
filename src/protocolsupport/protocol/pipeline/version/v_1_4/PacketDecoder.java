package protocolsupport.protocol.pipeline.version.v_1_4;

import protocolsupport.api.Connection;
import protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_4_5_6.ClientLogin;
import protocolsupport.protocol.packet.middleimpl.serverbound.login.v_4_5_6_7_8_9r1_9r2_10_11_12.EncryptionResponse;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5.EntityAction;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5.PlayerAbilities;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5.PositionLook;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6.ClientCommand;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6.ClientSettings;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6.CustomPayload;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6.KickDisconnect;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6.UseEntity;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7.Animation;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7.BlockDig;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7.BlockPlace;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7.KeepAlive;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7.Position;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7.TabComplete;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7.UpdateSign;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12.Chat;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12.CreativeSetSlot;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12.Flying;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12.HeldSlot;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12.InventoryClick;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12.InventoryClose;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12.InventoryEnchant;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12.InventoryTransaction;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12.Look;
import protocolsupport.protocol.pipeline.version.AbstractLegacyPacketDecoder;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.zplatform.network.NetworkState;

public class PacketDecoder extends AbstractLegacyPacketDecoder {

	{
		registry.register(NetworkState.HANDSHAKING, 0x02, ClientLogin.class);
		registry.register(NetworkState.LOGIN, 0xFC, EncryptionResponse.class);
		registry.register(NetworkState.PLAY, 0x00, KeepAlive.class);
		registry.register(NetworkState.PLAY, 0x03, Chat.class);
		registry.register(NetworkState.PLAY, 0x07, UseEntity.class);
		registry.register(NetworkState.PLAY, 0x0A, Flying.class);
		registry.register(NetworkState.PLAY, 0x0B, Position.class);
		registry.register(NetworkState.PLAY, 0x0C, Look.class);
		registry.register(NetworkState.PLAY, 0x0D, PositionLook.class);
		registry.register(NetworkState.PLAY, 0x0E, BlockDig.class);
		registry.register(NetworkState.PLAY, 0x0F, BlockPlace.class);
		registry.register(NetworkState.PLAY, 0x10, HeldSlot.class);
		registry.register(NetworkState.PLAY, 0x12, Animation.class);
		registry.register(NetworkState.PLAY, 0x13, EntityAction.class);
		registry.register(NetworkState.PLAY, 0x65, InventoryClose.class);
		registry.register(NetworkState.PLAY, 0x66, InventoryClick.class);
		registry.register(NetworkState.PLAY, 0x6A, InventoryTransaction.class);
		registry.register(NetworkState.PLAY, 0x6B, CreativeSetSlot.class);
		registry.register(NetworkState.PLAY, 0x6C, InventoryEnchant.class);
		registry.register(NetworkState.PLAY, 0x82, UpdateSign.class);
		registry.register(NetworkState.PLAY, 0xCB, TabComplete.class);
		registry.register(NetworkState.PLAY, 0xCA, PlayerAbilities.class);
		registry.register(NetworkState.PLAY, 0xCC, ClientSettings.class);
		registry.register(NetworkState.PLAY, 0xCD, ClientCommand.class);
		registry.register(NetworkState.PLAY, 0xFA, CustomPayload.class);
		registry.register(NetworkState.PLAY, 0xFF, KickDisconnect.class);
	}

	public PacketDecoder(Connection connection, NetworkDataCache storage) {
		super(connection, storage);
	}

}
