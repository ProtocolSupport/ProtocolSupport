package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15;

import org.bukkit.util.Vector;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.CancelMiddlePacketException;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.AbstractSneakingCacheUseEntity;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.NetworkEntityCache;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.TameableEntityMetadataRemapper;
import protocolsupport.protocol.types.UsedHand;
import protocolsupport.protocol.types.networkentity.NetworkEntity;

public class UseEntity extends AbstractSneakingCacheUseEntity {

	public UseEntity(ConnectionImpl connection) {
		super(connection);
	}

	protected final NetworkEntityCache entityCache = cache.getEntityCache();

	@Override
	protected void readClientData(ByteBuf clientdata) {
		entityId = VarNumberSerializer.readVarInt(clientdata);
		action = MiscSerializer.readVarIntEnum(clientdata, Action.CONSTANT_LOOKUP);
		switch (action) {
			case INTERACT: {
				hand = MiscSerializer.readVarIntEnum(clientdata, UsedHand.CONSTANT_LOOKUP);
				break;
			}
			case INTERACT_AT: {
				interactedAt = new Vector(clientdata.readFloat(), clientdata.readFloat(), clientdata.readFloat());
				hand = MiscSerializer.readVarIntEnum(clientdata, UsedHand.CONSTANT_LOOKUP);
				break;
			}
			case ATTACK: {
				break;
			}
		}
	}

	@Override
	protected void handleReadData() {
		if ((hand == UsedHand.OFF) && version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_14_4)) {
			NetworkEntity entity = entityCache.getEntity(entityId);
			if ((entity != null) && clientCache.getUUID().equals(entity.getDataCache().getData(TameableEntityMetadataRemapper.DATA_KEY_OWNER))) {
				throw CancelMiddlePacketException.INSTANCE;
			}
		}

		super.handleReadData();
	}

}
