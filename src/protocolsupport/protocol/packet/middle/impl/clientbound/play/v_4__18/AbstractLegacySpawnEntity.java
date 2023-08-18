package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__18;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;

import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__18.AbstractLegacyPaintingEntityMetadata.PaintingNetworkEntityData;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__20.AbstractRemappedSpawnEntity;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;

public abstract class AbstractLegacySpawnEntity extends AbstractRemappedSpawnEntity {

	protected static final Map<NetworkEntityType, Consumer<AbstractLegacySpawnEntity>> WRITER = new EnumMap<>(NetworkEntityType.class);
	static {
		for (NetworkEntityType type : NetworkEntityType.values()) {
			switch (type.getMetaType()) {
				case MOB, OBJECTMOB -> WRITER.put(type, AbstractLegacySpawnEntity::writeSpawnLiving);
				case OBJECT -> WRITER.put(type, AbstractLegacySpawnEntity::writeSpawnObject);
				default -> {}
			}
		}
		WRITER.put(NetworkEntityType.PAINTING, spawnEntity -> {
			spawnEntity.entity.getDataCache().setData(PaintingNetworkEntityData.DATA_KEY, new PaintingNetworkEntityData(spawnEntity.x, spawnEntity.y, spawnEntity.z, spawnEntity.objectdata));
		});
	}

	protected AbstractLegacySpawnEntity(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		WRITER.get(fType).accept(this);
	}

	protected abstract void writeSpawnLiving();

	protected abstract void writeSpawnObject();

}
