package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import org.bukkit.util.Vector;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUseEntity;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.UsedHand;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

//TODO: is this not actually hooked into anything?
public class UseEntity extends ServerBoundMiddlePacket {

	public UseEntity(ConnectionImpl connection) {
		super(connection);
	}

	protected int targetId;
	protected int subTypeId = -1;
	protected NetworkItemStack itemstack;
	protected int slot;
	protected float fromX, fromY, fromZ;
	protected float cX, cY, cZ;

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		targetId = (int) VarNumberSerializer.readVarLong(clientdata);
		subTypeId = VarNumberSerializer.readVarInt(clientdata);
		slot = VarNumberSerializer.readSVarInt(clientdata);
		itemstack = ItemStackSerializer.readItemStack(clientdata, connection.getVersion(), cache.getAttributesCache().getLocale());
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
				//TODO both armor stands?
				if ((target == null) || !target.getType().isOfType(NetworkEntityType.ARMOR_STAND_MOB)) {
					packets.add(MiddleUseEntity.create(targetId, MiddleUseEntity.Action.INTERACT, null, UsedHand.MAIN));
				}
				packets.add(MiddleUseEntity.create(targetId, MiddleUseEntity.Action.INTERACT_AT, new Vector(cX, cY, cZ), UsedHand.MAIN));
				break;
			}
			case INTERACT_ATTACK: {
				packets.add(MiddleUseEntity.create(targetId, MiddleUseEntity.Action.ATTACK, null, UsedHand.MAIN));
				break;
			}
			case INTERACT_AT: {
				packets.add(MiddleUseEntity.create(targetId, MiddleUseEntity.Action.INTERACT_AT, new Vector(cX, cY, cZ), UsedHand.MAIN));
				break;
			}
		}
		return packets;
	}

}
