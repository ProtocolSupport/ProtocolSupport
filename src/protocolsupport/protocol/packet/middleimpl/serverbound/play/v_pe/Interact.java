package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import org.bukkit.util.Vector;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUseEntity;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.protocol.utils.types.NetworkEntityType;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class Interact extends ServerBoundMiddlePacket {

	protected short peAction;
	protected int targetId;

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		peAction = clientdata.readUnsignedByte();
		targetId = (int) VarNumberSerializer.readVarLong(clientdata);
	}

	private static final int INTERACT = 1;
	private static final int ATTACK = 2;
	private static final int LEAVE_VEHICLE = 3;
	private static final int HOVER = 4;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		RecyclableArrayList<ServerBoundPacketData> packets = RecyclableArrayList.create();
		NetworkEntity target = cache.getWatchedEntity(targetId);
		switch (peAction) {
			case INTERACT: {
				if (target != null && target.getType() != NetworkEntityType.ARMOR_STAND) {
					packets.add(MiddleUseEntity.create(targetId, MiddleUseEntity.Action.INTERACT_AT, new Vector(), 0));
				} else {
					packets.add(MiddleUseEntity.create(targetId, MiddleUseEntity.Action.INTERACT, null, 0));
				}
				break;
			}
			case ATTACK: {
				packets.add(MiddleUseEntity.create(targetId, MiddleUseEntity.Action.ATTACK, null, 0));
				break;
			}
			case LEAVE_VEHICLE: {
				// packets.add(MiddleEntityAction.create(cache.getSelfPlayerEntityId(),
				// 0, 0)); //TODO: Exit vehicle by sneaking.
				break;
			}
			case HOVER: {
				break;
			}
		}
		return packets;
	}

}
