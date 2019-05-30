package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.listeners.InternalPluginMessageRequest;
import protocolsupport.listeners.internal.BlockUpdateRequest;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockDig;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockPlace;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.inventory.PEInventory;
import protocolsupport.protocol.types.BlockFace;
import protocolsupport.protocol.types.GameMode;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.UsedHand;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class UseItem extends ServerBoundMiddlePacket {

	public UseItem(ConnectionImpl connection) {
		super(connection);
	}

	protected int subTypeId = -1;
	protected NetworkItemStack itemstack;
	protected int slot;
	protected Position position = new Position(0, 0, 0);
	protected float fromX, fromY, fromZ;
	protected float cX, cY, cZ;
	protected int face;

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		subTypeId = VarNumberSerializer.readVarInt(clientdata);
		PositionSerializer.readPEPositionTo(clientdata, position);
		face = VarNumberSerializer.readSVarInt(clientdata);
		slot = VarNumberSerializer.readSVarInt(clientdata);
		itemstack = ItemStackSerializer.readItemStack(clientdata, connection.getVersion(), cache.getAttributesCache().getLocale());
		fromX = clientdata.readFloatLE();
		fromY = clientdata.readFloatLE();
		fromZ = clientdata.readFloatLE();
		cX = clientdata.readFloatLE();
		cY = clientdata.readFloatLE();
		cZ = clientdata.readFloatLE();
	}

	protected static final int USE_CLICK_BLOCK = 0;
	protected static final int USE_CLICK_AIR = 1;
	protected static final int USE_DIG_BLOCK = 2;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		RecyclableArrayList<ServerBoundPacketData> packets = RecyclableArrayList.create();
		switch (subTypeId) {
			case USE_CLICK_AIR: {
				face = -1;
				packets.add(MiddleBlockPlace.create(position, face, UsedHand.MAIN, cX, cY, cZ, false));
				break;
			}
			case USE_CLICK_BLOCK: {
				packets.add(MiddleBlockPlace.create(position, face, UsedHand.MAIN, cX, cY, cZ, false));
				if (PEInventory.shouldDoClickUpdate(itemstack)) {
					packets.add(MiddleBlockPlace.create(Position.ZERO, -1, UsedHand.MAIN, cX, cY, cZ, false));
				}
				//Modify position to request server update for the correct block.
				BlockFace.getById(face).modPosition(position);
				break;
			}
			case USE_DIG_BLOCK: {
				//instabreak
				if (cache.getAttributesCache().getPEGameMode() == GameMode.CREATIVE) {
					packets.add(MiddleBlockDig.create(MiddleBlockDig.Action.START_DIG, position, 0));
					packets.add(MiddleBlockDig.create(MiddleBlockDig.Action.FINISH_DIG, position, 0));
				}
				break;
			}
		}
		//Whenever the player places or breaks a block far away we want the server to update it, because PE might not be allowed to do it.
		if (
			(Math.abs(cache.getMovementCache().getPEClientX() - position.getX()) > 4) ||
			(Math.abs(cache.getMovementCache().getPEClientY() - position.getY()) > 4) ||
			(Math.abs(cache.getMovementCache().getPEClientZ() - position.getZ()) > 4)
		) {
			packets.add(InternalPluginMessageRequest.newPluginMessageRequest(new BlockUpdateRequest(position)));
		}
		return packets;
	}

}
