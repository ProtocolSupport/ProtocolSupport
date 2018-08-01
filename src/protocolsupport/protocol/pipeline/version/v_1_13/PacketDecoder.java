package protocolsupport.protocol.pipeline.version.v_1_13;

import protocolsupport.api.Connection;
import protocolsupport.api.utils.NetworkState;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUpdateStructureBlock;
import protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_7_8_9r1_9r2_10_11_12r1_12r2_13.SetProtocol;
import protocolsupport.protocol.packet.middleimpl.serverbound.login.v_13.LoginCustomPayload;
import protocolsupport.protocol.packet.middleimpl.serverbound.login.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.EncryptionResponse;
import protocolsupport.protocol.packet.middleimpl.serverbound.login.v_7_8_9r1_9r2_10_11_12r1_12r2_13.LoginStart;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_10_11_12r1_12r2_13.ResourcePackStatus;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_11_12r1_12r2_13.BlockPlace;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_12r1_12r2_13.AdvancementTab;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13.CraftRecipeRequest;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13.CustomPayload;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13.EditBook;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13.NameItem;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13.PickItem;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13.QueryBlockNBT;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13.QueryEntityNBT;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13.RecipeBookData;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13.SelectTrade;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13.SetBeaconEffect;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13.TabComplete;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13.UpdateCommandBlock;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13.UpdateCommandMinecart;
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
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.EntityAction;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.KeepAlive;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.Move;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.Spectate;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.SteerVehicle;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.UpdateSign;
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
		registry.register(NetworkState.LOGIN, 0x02, LoginCustomPayload.class);
		registry.register(NetworkState.STATUS, 0x00, ServerInfoRequest.class);
		registry.register(NetworkState.STATUS, 0x01, Ping.class);
		registry.register(NetworkState.PLAY, 0x00, TeleportAccept.class);
		registry.register(NetworkState.PLAY, 0x01, QueryBlockNBT.class);
		registry.register(NetworkState.PLAY, 0x02, Chat.class);
		registry.register(NetworkState.PLAY, 0x03, ClientCommand.class);
		registry.register(NetworkState.PLAY, 0x04, ClientSettings.class);
		registry.register(NetworkState.PLAY, 0x05, TabComplete.class);
		registry.register(NetworkState.PLAY, 0x06, InventoryTransaction.class);
		registry.register(NetworkState.PLAY, 0x07, InventoryEnchant.class);
		registry.register(NetworkState.PLAY, 0x08, InventoryClick.class);
		registry.register(NetworkState.PLAY, 0x09, InventoryClose.class);
		registry.register(NetworkState.PLAY, 0x0A, CustomPayload.class);
		registry.register(NetworkState.PLAY, 0x0B, EditBook.class);
		registry.register(NetworkState.PLAY, 0x0C, QueryEntityNBT.class);
		registry.register(NetworkState.PLAY, 0x0D, UseEntity.class);
		registry.register(NetworkState.PLAY, 0x0E, KeepAlive.class);
		registry.register(NetworkState.PLAY, 0x0F, Flying.class);
		registry.register(NetworkState.PLAY, 0x10, Move.class);
		registry.register(NetworkState.PLAY, 0x11, MoveLook.class);
		registry.register(NetworkState.PLAY, 0x12, Look.class);
		registry.register(NetworkState.PLAY, 0x13, MoveVehicle.class);
		registry.register(NetworkState.PLAY, 0x14, SteerBoat.class);
		registry.register(NetworkState.PLAY, 0x15, PickItem.class);
		registry.register(NetworkState.PLAY, 0x16, CraftRecipeRequest.class);
		registry.register(NetworkState.PLAY, 0x17, PlayerAbilities.class);
		registry.register(NetworkState.PLAY, 0x18, BlockDig.class);
		registry.register(NetworkState.PLAY, 0x19, EntityAction.class);
		registry.register(NetworkState.PLAY, 0x1A, SteerVehicle.class);
		registry.register(NetworkState.PLAY, 0x1B, RecipeBookData.class);
		registry.register(NetworkState.PLAY, 0x1C, NameItem.class);
		registry.register(NetworkState.PLAY, 0x1D, ResourcePackStatus.class);
		registry.register(NetworkState.PLAY, 0x1E, AdvancementTab.class);
		registry.register(NetworkState.PLAY, 0x1F, SelectTrade.class);
		registry.register(NetworkState.PLAY, 0x20, SetBeaconEffect.class);
		registry.register(NetworkState.PLAY, 0x21, HeldSlot.class);
		registry.register(NetworkState.PLAY, 0x22, UpdateCommandBlock.class);
		registry.register(NetworkState.PLAY, 0x23, UpdateCommandMinecart.class);
		registry.register(NetworkState.PLAY, 0x24, CreativeSetSlot.class);
		registry.register(NetworkState.PLAY, 0x25, MiddleUpdateStructureBlock.class);
		registry.register(NetworkState.PLAY, 0x26, UpdateSign.class);
		registry.register(NetworkState.PLAY, 0x27, Animation.class);
		registry.register(NetworkState.PLAY, 0x28, Spectate.class);
		registry.register(NetworkState.PLAY, 0x29, BlockPlace.class);
		registry.register(NetworkState.PLAY, 0x2A, UseItem.class);
	}

	public PacketDecoder(Connection connection, NetworkDataCache sharedstorage) {
		super(connection, sharedstorage);
	}

}
