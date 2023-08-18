package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__15;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;

import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__18.AbstractLegacySpawnEntity;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;

public abstract class AbstractLegacyThunderboltSpawnEntity extends AbstractLegacySpawnEntity {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected static final Map<NetworkEntityType, Consumer<AbstractLegacyThunderboltSpawnEntity>> WRITER = new EnumMap<>((EnumMap) AbstractLegacySpawnEntity.WRITER);
	static {
		 WRITER.put(NetworkEntityType.THUNDERBOLT, AbstractLegacyThunderboltSpawnEntity::writeSpawnThunderbolt);
	}

	protected AbstractLegacyThunderboltSpawnEntity(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		WRITER.get(fType).accept(this);
	}

	protected abstract void writeSpawnThunderbolt();

}
