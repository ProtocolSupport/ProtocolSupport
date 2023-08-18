package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__8;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;

import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__15.AbstractLegacyThunderboltSpawnEntity;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__18.AbstractLegacySpawnEntity;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__8.AbstractLegacyPotionItemEntityMetadata.PotionNetworkEntityData;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;

public abstract class AbstractLegacyPotionItemSpawnEntiy extends AbstractLegacyThunderboltSpawnEntity {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected static final Map<NetworkEntityType, Consumer<AbstractLegacyPotionItemSpawnEntiy>> WRITER = new EnumMap<>((EnumMap) AbstractLegacySpawnEntity.WRITER);
	static {
		 WRITER.put(NetworkEntityType.POTION, spawnEntity -> {
			PotionNetworkEntityData potiondata = new PotionNetworkEntityData();
			potiondata.updateVelocity(spawnEntity.motX, spawnEntity.motY, spawnEntity.motZ);
			spawnEntity.entity.getDataCache().setData(PotionNetworkEntityData.DATA_KEY, potiondata);
			spawnEntity.writeSpawnObject();
		 });
	}

	protected AbstractLegacyPotionItemSpawnEntiy(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		WRITER.get(fType).accept(this);
	}

}
