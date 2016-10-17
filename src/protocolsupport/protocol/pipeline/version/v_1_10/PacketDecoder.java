package protocolsupport.protocol.pipeline.version.v_1_10;

import net.minecraft.server.v1_10_R1.EnumProtocol;
import protocolsupport.api.unsafe.Connection;
import protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_1_7__1_8__1_9_r1__1_9_r2__1_10.SetProtocol;
import protocolsupport.protocol.packet.middleimpl.serverbound.login.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10.EncryptionResponse;
import protocolsupport.protocol.packet.middleimpl.serverbound.login.v_1_7__1_8__1_9_r1__1_9_r2__1_10.LoginStart;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v1_9_r1__1_9_r2__1_10.Animation;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v1_9_r1__1_9_r2__1_10.BlockPlace;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v1_9_r1__1_9_r2__1_10.ClientSettings;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v1_9_r1__1_9_r2__1_10.MoveVehicle;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v1_9_r1__1_9_r2__1_10.SteerBoat;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v1_9_r1__1_9_r2__1_10.TabComplete;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v1_9_r1__1_9_r2__1_10.TeleportAccept;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v1_9_r1__1_9_r2__1_10.UseEntity;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v1_9_r1__1_9_r2__1_10.UseItem;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_10.ResourcePackStatus;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10.Chat;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10.CreativeSetSlot;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10.Flying;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10.HeldSlot;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10.InventoryClick;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10.InventoryClose;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10.InventoryEnchant;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10.InventoryTransaction;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10.Look;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_6__1_7__1_8__1_9_r1__1_9_r2__1_10.PlayerAbilities;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_7__1_8__1_9_r1__1_9_r2__1_10.ClientCommand;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2__1_10.BlockDig;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2__1_10.CustomPayload;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2__1_10.EntityAction;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2__1_10.KeepAlive;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2__1_10.Position;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2__1_10.PositionLook;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2__1_10.Spectate;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2__1_10.SteerVehicle;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2__1_10.UpdateSign;
import protocolsupport.protocol.packet.middleimpl.serverbound.status.v_1_7__1_8__1_9_r1__1_9_r2__1_10.Ping;
import protocolsupport.protocol.packet.middleimpl.serverbound.status.v_1_7__1_8__1_9_r1__1_9_r2__1_10.ServerInfoRequest;
import protocolsupport.protocol.pipeline.version.AbstractModernWithoutReorderPacketDecoder;
import protocolsupport.protocol.storage.NetworkDataCache;

public class PacketDecoder extends AbstractModernWithoutReorderPacketDecoder {

	{
		registry.register(EnumProtocol.HANDSHAKING, 0x00, SetProtocol.class);
		registry.register(EnumProtocol.LOGIN, 0x00, LoginStart.class);
		registry.register(EnumProtocol.LOGIN, 0x01, EncryptionResponse.class);
		registry.register(EnumProtocol.STATUS, 0x00, ServerInfoRequest.class);
		registry.register(EnumProtocol.STATUS, 0x01, Ping.class);
		registry.register(EnumProtocol.PLAY, 0x00, TeleportAccept.class);
		registry.register(EnumProtocol.PLAY, 0x01, TabComplete.class);
		registry.register(EnumProtocol.PLAY, 0x02, Chat.class);
		registry.register(EnumProtocol.PLAY, 0x03, ClientCommand.class);
		registry.register(EnumProtocol.PLAY, 0x04, ClientSettings.class);
		registry.register(EnumProtocol.PLAY, 0x05, InventoryTransaction.class);
		registry.register(EnumProtocol.PLAY, 0x06, InventoryEnchant.class);
		registry.register(EnumProtocol.PLAY, 0x07, InventoryClick.class);
		registry.register(EnumProtocol.PLAY, 0x08, InventoryClose.class);
		registry.register(EnumProtocol.PLAY, 0x09, CustomPayload.class);
		registry.register(EnumProtocol.PLAY, 0x0A, UseEntity.class);
		registry.register(EnumProtocol.PLAY, 0x0B, KeepAlive.class);
		registry.register(EnumProtocol.PLAY, 0x0C, Position.class);
		registry.register(EnumProtocol.PLAY, 0x0D, PositionLook.class);
		registry.register(EnumProtocol.PLAY, 0x0E, Look.class);
		registry.register(EnumProtocol.PLAY, 0x0F, Flying.class);
		registry.register(EnumProtocol.PLAY, 0x10, MoveVehicle.class);
		registry.register(EnumProtocol.PLAY, 0x11, SteerBoat.class);
		registry.register(EnumProtocol.PLAY, 0x12, PlayerAbilities.class);
		registry.register(EnumProtocol.PLAY, 0x13, BlockDig.class);
		registry.register(EnumProtocol.PLAY, 0x14, EntityAction.class);
		registry.register(EnumProtocol.PLAY, 0x15, SteerVehicle.class);
		registry.register(EnumProtocol.PLAY, 0x16, ResourcePackStatus.class);
		registry.register(EnumProtocol.PLAY, 0x17, HeldSlot.class);
		registry.register(EnumProtocol.PLAY, 0x18, CreativeSetSlot.class);
		registry.register(EnumProtocol.PLAY, 0x19, UpdateSign.class);
		registry.register(EnumProtocol.PLAY, 0x1A, Animation.class);
		registry.register(EnumProtocol.PLAY, 0x1B, Spectate.class);
		registry.register(EnumProtocol.PLAY, 0x1C, BlockPlace.class);
		registry.register(EnumProtocol.PLAY, 0x1D, UseItem.class);
	}

	public PacketDecoder(Connection connection, NetworkDataCache sharedstorage) {
		super(connection, sharedstorage);
	}

}
