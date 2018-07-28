package protocolsupport.protocol.pipeline.version.v_1_12.r2;

import protocolsupport.api.Connection;
import protocolsupport.api.utils.NetworkState;
import protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_7_8_9r1_9r2_10_11_12r1_12r2_13.SetProtocol;
import protocolsupport.protocol.packet.middleimpl.serverbound.login.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.EncryptionResponse;
import protocolsupport.protocol.packet.middleimpl.serverbound.login.v_7_8_9r1_9r2_10_11_12r1_12r2_13.LoginStart;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_10_11_12r1_12r2_13.ResourcePackStatus;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_11_12r1_12r2_13.BlockPlace;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_12r1_12r2.RecipeBookData;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_12r1_12r2_13.AdvancementTab;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_12r2.CraftRecipeRequest;
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
import protocolsupport.protocol.storage.netcache.NetworkDataCache;

public class PacketDecoder extends AbstractModernPacketDecoder {

	{
		registry.register(NetworkState.HANDSHAKING, 0x00, SetProtocol.class);
		registry.register(NetworkState.LOGIN, 0x00, LoginStart.class);
		registry.register(NetworkState.LOGIN, 0x01, EncryptionResponse.class);
		registry.register(NetworkState.STATUS, 0x00, ServerInfoRequest.class);
		registry.register(NetworkState.STATUS, 0x01, Ping.class);
		registry.register(NetworkState.PLAY, 0x00, TeleportAccept.class);
		registry.register(NetworkState.PLAY, 0x01, TabComplete.class);
		registry.register(NetworkState.PLAY, 0x02, Chat.class);
		registry.register(NetworkState.PLAY, 0x03, ClientCommand.class);
		registry.register(NetworkState.PLAY, 0x04, ClientSettings.class);
		registry.register(NetworkState.PLAY, 0x05, InventoryTransaction.class);
		registry.register(NetworkState.PLAY, 0x06, InventoryEnchant.class);
		registry.register(NetworkState.PLAY, 0x07, InventoryClick.class);
		registry.register(NetworkState.PLAY, 0x08, InventoryClose.class);
		registry.register(NetworkState.PLAY, 0x09, CustomPayload.class);
		registry.register(NetworkState.PLAY, 0x0A, UseEntity.class);
		registry.register(NetworkState.PLAY, 0x0B, KeepAlive.class);
		registry.register(NetworkState.PLAY, 0x0C, Flying.class);
		registry.register(NetworkState.PLAY, 0x0D, Move.class);
		registry.register(NetworkState.PLAY, 0x0E, MoveLook.class);
		registry.register(NetworkState.PLAY, 0x0F, Look.class);
		registry.register(NetworkState.PLAY, 0x10, MoveVehicle.class);
		registry.register(NetworkState.PLAY, 0x11, SteerBoat.class);
		registry.register(NetworkState.PLAY, 0x12, CraftRecipeRequest.class);
		registry.register(NetworkState.PLAY, 0x13, PlayerAbilities.class);
		registry.register(NetworkState.PLAY, 0x14, BlockDig.class);
		registry.register(NetworkState.PLAY, 0x15, EntityAction.class);
		registry.register(NetworkState.PLAY, 0x16, SteerVehicle.class);
		registry.register(NetworkState.PLAY, 0x17, RecipeBookData.class);
		registry.register(NetworkState.PLAY, 0x18, ResourcePackStatus.class);
		registry.register(NetworkState.PLAY, 0x19, AdvancementTab.class);
		registry.register(NetworkState.PLAY, 0x1A, HeldSlot.class);
		registry.register(NetworkState.PLAY, 0x1B, CreativeSetSlot.class);
		registry.register(NetworkState.PLAY, 0x1C, UpdateSign.class);
		registry.register(NetworkState.PLAY, 0x1D, Animation.class);
		registry.register(NetworkState.PLAY, 0x1E, Spectate.class);
		registry.register(NetworkState.PLAY, 0x1F, BlockPlace.class);
		registry.register(NetworkState.PLAY, 0x20, UseItem.class);
	}

	public PacketDecoder(Connection connection, NetworkDataCache sharedstorage) {
		super(connection, sharedstorage);
	}

}
