package protocolsupport.protocol.pipeline.version.v_1_14;

import protocolsupport.api.utils.NetworkState;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_7_8_9r1_9r2_10_11_12r1_12r2_13_14.SetProtocol;
import protocolsupport.protocol.packet.middleimpl.serverbound.login.v_13_14.LoginCustomPayload;
import protocolsupport.protocol.packet.middleimpl.serverbound.login.v_7_8_9r1_9r2_10_11_12r1_12r2_13_14.LoginStart;
import protocolsupport.protocol.packet.middleimpl.serverbound.login.v_8_9r1_9r2_10_11_12r1_12r2_13_14.EncryptionResponse;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_10_11_12r1_12r2_13_14.ResourcePackStatus;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_12r1_12r2_13_14.AdvancementTab;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13_14.CraftRecipeRequest;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13_14.CustomPayload;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13_14.EditBook;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13_14.NameItem;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13_14.PickItem;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13_14.QueryEntityNBT;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13_14.SelectTrade;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13_14.SetBeaconEffect;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13_14.TabComplete;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13_14.UpdateCommandMinecart;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_14.BlockDig;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_14.BlockPlace;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_14.QueryBlockNBT;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_14.RecipeBookData;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_14.UpdateCommandBlock;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_14.UpdateSign;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_14.UpdateStructureBlock;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14.Chat;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14.CreativeSetSlot;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14.Flying;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14.HeldSlot;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14.InventoryButton;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14.InventoryClick;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14.InventoryClose;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14.InventoryTransaction;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14.Look;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14.PlayerAbilities;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_7_8_9r1_9r2_10_11_12r1_12r2_13_14.ClientCommand;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14.EntityAction;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14.KeepAlive;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14.Move;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14.Spectate;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14.SteerVehicle;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11_12r1_12r2_13_14.Animation;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11_12r1_12r2_13_14.ClientSettings;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11_12r1_12r2_13_14.MoveLook;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11_12r1_12r2_13_14.MoveVehicle;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11_12r1_12r2_13_14.SteerBoat;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11_12r1_12r2_13_14.TeleportAccept;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11_12r1_12r2_13_14.UseEntity;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11_12r1_12r2_13_14.UseItem;
import protocolsupport.protocol.packet.middleimpl.serverbound.status.v_7_8_9r1_9r2_10_11_12r1_12r2_13_14.Ping;
import protocolsupport.protocol.packet.middleimpl.serverbound.status.v_7_8_9r1_9r2_10_11_12r1_12r2_13_14.ServerInfoRequest;
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
		registry.register(NetworkState.PLAY, 0x03, Chat::new);
		registry.register(NetworkState.PLAY, 0x04, ClientCommand::new);
		registry.register(NetworkState.PLAY, 0x05, ClientSettings::new);
		registry.register(NetworkState.PLAY, 0x06, TabComplete::new);
		registry.register(NetworkState.PLAY, 0x07, InventoryTransaction::new);
		registry.register(NetworkState.PLAY, 0x08, InventoryButton::new);
		registry.register(NetworkState.PLAY, 0x09, InventoryClick::new);
		registry.register(NetworkState.PLAY, 0x0A, InventoryClose::new);
		registry.register(NetworkState.PLAY, 0x0B, CustomPayload::new);
		registry.register(NetworkState.PLAY, 0x0C, EditBook::new);
		registry.register(NetworkState.PLAY, 0x0D, QueryEntityNBT::new);
		registry.register(NetworkState.PLAY, 0x0E, UseEntity::new);
		registry.register(NetworkState.PLAY, 0x0F, KeepAlive::new);
		registry.register(NetworkState.PLAY, 0x11, Move::new);
		registry.register(NetworkState.PLAY, 0x12, MoveLook::new);
		registry.register(NetworkState.PLAY, 0x13, Look::new);
		registry.register(NetworkState.PLAY, 0x14, Flying::new);
		registry.register(NetworkState.PLAY, 0x15, MoveVehicle::new);
		registry.register(NetworkState.PLAY, 0x16, SteerBoat::new);
		registry.register(NetworkState.PLAY, 0x17, PickItem::new);
		registry.register(NetworkState.PLAY, 0x18, CraftRecipeRequest::new);
		registry.register(NetworkState.PLAY, 0x19, PlayerAbilities::new);
		registry.register(NetworkState.PLAY, 0x1A, BlockDig::new);
		registry.register(NetworkState.PLAY, 0x1B, EntityAction::new);
		registry.register(NetworkState.PLAY, 0x1C, SteerVehicle::new);
		registry.register(NetworkState.PLAY, 0x1D, RecipeBookData::new);
		registry.register(NetworkState.PLAY, 0x1E, NameItem::new);
		registry.register(NetworkState.PLAY, 0x1F, ResourcePackStatus::new);
		registry.register(NetworkState.PLAY, 0x20, AdvancementTab::new);
		registry.register(NetworkState.PLAY, 0x21, SelectTrade::new);
		registry.register(NetworkState.PLAY, 0x22, SetBeaconEffect::new);
		registry.register(NetworkState.PLAY, 0x23, HeldSlot::new);
		registry.register(NetworkState.PLAY, 0x24, UpdateCommandBlock::new);
		registry.register(NetworkState.PLAY, 0x25, UpdateCommandMinecart::new);
		registry.register(NetworkState.PLAY, 0x26, CreativeSetSlot::new);
		registry.register(NetworkState.PLAY, 0x28, UpdateStructureBlock::new);
		registry.register(NetworkState.PLAY, 0x29, UpdateSign::new);
		registry.register(NetworkState.PLAY, 0x2A, Animation::new);
		registry.register(NetworkState.PLAY, 0x2B, Spectate::new);
		registry.register(NetworkState.PLAY, 0x2C, BlockPlace::new);
		registry.register(NetworkState.PLAY, 0x2D, UseItem::new);
	}

	public PacketDecoder(ConnectionImpl connection) {
		super(connection);
	}

}
