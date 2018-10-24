package protocolsupport.protocol.pipeline.version.v_1_13;

import protocolsupport.api.utils.NetworkState;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_7_8_9r1_9r2_10_11_12r1_12r2_13.SetProtocol;
import protocolsupport.protocol.packet.middleimpl.serverbound.login.v_13.LoginCustomPayload;
import protocolsupport.protocol.packet.middleimpl.serverbound.login.v_7_8_9r1_9r2_10_11_12r1_12r2_13.LoginStart;
import protocolsupport.protocol.packet.middleimpl.serverbound.login.v_8_9r1_9r2_10_11_12r1_12r2_13.EncryptionResponse;
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
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13.UpdateStructureBlock;
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

public class PacketDecoder extends AbstractModernPacketDecoder {

	{
		registry.register(NetworkState.HANDSHAKING, 0x00, SetProtocol::new);
		registry.register(NetworkState.LOGIN, 0x00, LoginStart::new);
		registry.register(NetworkState.LOGIN, 0x01, EncryptionResponse::new);
		registry.register(NetworkState.LOGIN, 0x02, LoginCustomPayload::new);
		registry.register(NetworkState.STATUS, 0x00, ServerInfoRequest::new);
		registry.register(NetworkState.STATUS, 0x01, Ping::new);
		registry.register(NetworkState.PLAY, 0x00, TeleportAccept::new);
		registry.register(NetworkState.PLAY, 0x01, QueryBlockNBT::new);
		registry.register(NetworkState.PLAY, 0x02, Chat::new);
		registry.register(NetworkState.PLAY, 0x03, ClientCommand::new);
		registry.register(NetworkState.PLAY, 0x04, ClientSettings::new);
		registry.register(NetworkState.PLAY, 0x05, TabComplete::new);
		registry.register(NetworkState.PLAY, 0x06, InventoryTransaction::new);
		registry.register(NetworkState.PLAY, 0x07, InventoryEnchant::new);
		registry.register(NetworkState.PLAY, 0x08, InventoryClick::new);
		registry.register(NetworkState.PLAY, 0x09, InventoryClose::new);
		registry.register(NetworkState.PLAY, 0x0A, CustomPayload::new);
		registry.register(NetworkState.PLAY, 0x0B, EditBook::new);
		registry.register(NetworkState.PLAY, 0x0C, QueryEntityNBT::new);
		registry.register(NetworkState.PLAY, 0x0D, UseEntity::new);
		registry.register(NetworkState.PLAY, 0x0E, KeepAlive::new);
		registry.register(NetworkState.PLAY, 0x0F, Flying::new);
		registry.register(NetworkState.PLAY, 0x10, Move::new);
		registry.register(NetworkState.PLAY, 0x11, MoveLook::new);
		registry.register(NetworkState.PLAY, 0x12, Look::new);
		registry.register(NetworkState.PLAY, 0x13, MoveVehicle::new);
		registry.register(NetworkState.PLAY, 0x14, SteerBoat::new);
		registry.register(NetworkState.PLAY, 0x15, PickItem::new);
		registry.register(NetworkState.PLAY, 0x16, CraftRecipeRequest::new);
		registry.register(NetworkState.PLAY, 0x17, PlayerAbilities::new);
		registry.register(NetworkState.PLAY, 0x18, BlockDig::new);
		registry.register(NetworkState.PLAY, 0x19, EntityAction::new);
		registry.register(NetworkState.PLAY, 0x1A, SteerVehicle::new);
		registry.register(NetworkState.PLAY, 0x1B, RecipeBookData::new);
		registry.register(NetworkState.PLAY, 0x1C, NameItem::new);
		registry.register(NetworkState.PLAY, 0x1D, ResourcePackStatus::new);
		registry.register(NetworkState.PLAY, 0x1E, AdvancementTab::new);
		registry.register(NetworkState.PLAY, 0x1F, SelectTrade::new);
		registry.register(NetworkState.PLAY, 0x20, SetBeaconEffect::new);
		registry.register(NetworkState.PLAY, 0x21, HeldSlot::new);
		registry.register(NetworkState.PLAY, 0x22, UpdateCommandBlock::new);
		registry.register(NetworkState.PLAY, 0x23, UpdateCommandMinecart::new);
		registry.register(NetworkState.PLAY, 0x24, CreativeSetSlot::new);
		registry.register(NetworkState.PLAY, 0x25, UpdateStructureBlock::new);
		registry.register(NetworkState.PLAY, 0x26, UpdateSign::new);
		registry.register(NetworkState.PLAY, 0x27, Animation::new);
		registry.register(NetworkState.PLAY, 0x28, Spectate::new);
		registry.register(NetworkState.PLAY, 0x29, BlockPlace::new);
		registry.register(NetworkState.PLAY, 0x2A, UseItem::new);
	}

	public PacketDecoder(ConnectionImpl connection) {
		super(connection);
	}

}
