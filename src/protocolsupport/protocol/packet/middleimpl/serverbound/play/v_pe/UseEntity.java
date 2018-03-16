package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import org.bukkit.util.Vector;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUseEntity;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.protocol.utils.types.NetworkEntityType;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

public class UseEntity extends ServerBoundMiddlePacket {

	protected int targetId;
	protected int subTypeId = -1;
	protected ItemStackWrapper itemstack;
	protected int slot;
	protected float fromX, fromY, fromZ;
	protected float cX, cY, cZ;

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		targetId = (int) VarNumberSerializer.readVarLong(clientdata);
		subTypeId = VarNumberSerializer.readVarInt(clientdata);
		slot = VarNumberSerializer.readSVarInt(clientdata);
		itemstack = ItemStackSerializer.readItemStack(clientdata, connection.getVersion(), cache.getAttributesCache().getLocale(), true);
		fromX = clientdata.readFloatLE();
		fromY = clientdata.readFloatLE();
		fromZ = clientdata.readFloatLE();
		cX = clientdata.readFloatLE();
		cY = clientdata.readFloatLE();
		cZ = clientdata.readFloatLE();
	}

	protected static final int INTERACT_INTERACT = 0;
	protected static final int INTERACT_ATTACK = 1;
	protected static final int INTERACT_AT = 2;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		RecyclableArrayList<ServerBoundPacketData> packets = RecyclableArrayList.create();
		switch (subTypeId) {
			case INTERACT_INTERACT: {
				NetworkEntity target = cache.getWatchedEntityCache().getWatchedEntity(targetId);
				if((target == null) || !target.isOfType(NetworkEntityType.ARMOR_STAND)) {
					packets.add(MiddleUseEntity.create(targetId, MiddleUseEntity.Action.INTERACT, null, 0));
				}
				packets.add(MiddleUseEntity.create(targetId, MiddleUseEntity.Action.INTERACT_AT, new Vector(cX, cY, cZ), 0));
				break;
			}
			case INTERACT_ATTACK: {
				packets.add(MiddleUseEntity.create(targetId, MiddleUseEntity.Action.ATTACK, null, 0));
				break;
			}
			case INTERACT_AT: {
				packets.add(MiddleUseEntity.create(targetId, MiddleUseEntity.Action.INTERACT_AT, new Vector(cX, cY, cZ), 0));
				break;
			}
		}
		return packets;
	}

}
