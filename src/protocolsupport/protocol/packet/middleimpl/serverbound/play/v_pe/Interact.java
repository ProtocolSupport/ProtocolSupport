package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import org.bukkit.util.Vector;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUseEntity;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.SpecificRemapper;
import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedEntity;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class Interact extends ServerBoundMiddlePacket{
	
	protected short peAction;
	protected int targetId;
	
	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		peAction = clientdata.readUnsignedByte();
		targetId = ((int) VarNumberSerializer.readVarLong(clientdata) /2); //We should look into why pocket doubles the entity ids. For now I just divide it back.
	}
	
	private static final int INTERACT = 1; //I think MCW10 does this on every right click. Pocket can only right click when button appears.
	private static final int ATTACK = 2;
	private static final int LEAVE_VEHICLE = 3;
//	private static final int HOVER = 4; //TODO: send the interact button back to PE when looking at entity. Perhaps with update trade packet?

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		RecyclableArrayList<ServerBoundPacketData> packets = RecyclableArrayList.create();
		WatchedEntity target = cache.getWatchedEntity(targetId);
		switch(peAction){
			case INTERACT: {
				if(target.getType() != SpecificRemapper.ARMOR_STAND){
					packets.add(MiddleUseEntity.create(targetId, MiddleUseEntity.Action.INTERACT, null, 0));
				}
				packets.add(MiddleUseEntity.create(targetId, MiddleUseEntity.Action.INTERACT_AT, new Vector(), 0)); //TODO: Send where the entity is clicked (will probably be implemented with armorstands.)
			}
			case ATTACK: {
				packets.add(MiddleUseEntity.create(targetId, MiddleUseEntity.Action.ATTACK, null, 0));
			}
			case LEAVE_VEHICLE: {
				//packets.add(MiddleEntityAction.create(cache.getSelfPlayerEntityId(), 0, 0)); //Exit vehicle by sneaking.
			}
		}
		return packets;
	}

}
