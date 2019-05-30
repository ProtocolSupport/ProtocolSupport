package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityRelMove;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.NetworkEntityDataCache;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityRelMove extends MiddleEntityRelMove {

	private static final float REL_TO_FLOAT = 1f / (128f * 32f);

	public EntityRelMove(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		NetworkEntity entity = cache.getWatchedEntityCache().getWatchedEntity(entityId);
		if (entity == null) {
			return RecyclableEmptyList.get();
		}
		if (entity.getType() == NetworkEntityType.PLAYER) {
			updateRelMove(entity, relX, relY, relZ);
			return RecyclableSingletonList.create(SetPosition.createAll(entity));
		} else {
			ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.MOVE_ENTITY_DELTA);
			VarNumberSerializer.writeVarLong(serializer, entityId);
			serializer.writeByte(EntityLook.FLAG_HAS_COORDS | (onGround ? EntityLook.FLAG_ONGROUND : 0));
			writeAndUpdateRelMove(serializer, entity, relX, relY, relZ);
			return RecyclableSingletonList.create(serializer);
		}
	}

	public static void writeAndUpdateRelMove(ByteBuf serializer, NetworkEntity entity, short relX, short relY, short relZ) {
		final NetworkEntityDataCache cache = entity.getDataCache();
		final float oldX = cache.getPosX();
		final float oldY = cache.getPosY();
		final float oldZ = cache.getPosZ();
		final float newX = oldX + relX * REL_TO_FLOAT;
		final float newY = oldY + relY * REL_TO_FLOAT;
		final float newZ = oldZ + relZ * REL_TO_FLOAT;
		cache.setPos(newX, newY, newZ);
		if (serializer != null) {
			//signed integer difference of cast float. thanks MiNET!
			VarNumberSerializer.writeSVarInt(serializer, Float.floatToRawIntBits(newX) - Float.floatToRawIntBits(oldX));
			VarNumberSerializer.writeSVarInt(serializer, Float.floatToRawIntBits(newY) - Float.floatToRawIntBits(oldY));
			VarNumberSerializer.writeSVarInt(serializer, Float.floatToRawIntBits(newZ) - Float.floatToRawIntBits(oldZ));
		}
	}

	public static void updateRelMove(NetworkEntity entity, short relX, short relY, short relZ) {
		writeAndUpdateRelMove(null, entity, relX, relY, relZ);
	}

}
