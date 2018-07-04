package protocolsupport.protocol.typeremapper.pe.inventory.fakes;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityDestroy;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.SpawnLiving;
import protocolsupport.protocol.serializer.MerchantDataSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.types.MerchantData;
import protocolsupport.protocol.utils.types.networkentity.NetworkEntity;
import protocolsupport.protocol.utils.types.networkentity.NetworkEntityType;

public class PEFakeVillager {

	private final NetworkEntity villager = new NetworkEntity(UUID.randomUUID(), Integer.MAX_VALUE - 10, NetworkEntityType.VILLAGER);
	private BaseComponent title;
	private boolean spawned = false;

	public boolean isSpawned() {
		return spawned;
	}

	public void setTitle(BaseComponent title) {
		this.title = title;
	}

	public ClientBoundPacketData spawnVillager(NetworkDataCache cache, ProtocolVersion version) {
		spawned = true;
		return SpawnLiving.createSimple(version, 
				cache.getAttributesCache().getLocale(),
				villager,
				cache.getMovementCache().getPEClientX(), 
				cache.getMovementCache().getPEClientY() - 2, 
				cache.getMovementCache().getPEClientZ() 
		);
	}

	public ClientBoundPacketData updateTrade(NetworkDataCache cache, ProtocolVersion version, MerchantData data) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.TRADE_UPDATE);
		MerchantDataSerializer.writePEMerchantData(serializer, 
				version, cache, villager.getId(), title.toLegacyText(cache.getAttributesCache().getLocale()), data
		);
		return serializer;
	}

	public void despawnVillager(Connection connection) {
		ByteBuf serializer = Unpooled.buffer();
		VarNumberSerializer.writeVarInt(serializer, PEPacketIDs.ENTITY_DESTROY);
		serializer.writeByte(0);
		serializer.writeByte(0);
		serializer.writeBytes(EntityDestroy.create(villager.getId()));
		spawned = false;
		connection.sendRawPacket(MiscSerializer.readAllBytes(serializer));
	}

}
