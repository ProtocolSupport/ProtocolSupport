package protocolsupport.protocol.typeremapper.particle.legacy;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackRemapper;
import protocolsupport.protocol.utils.types.particle.Particle;
import protocolsupport.zplatform.itemstack.NetworkItemStack;

public class LegacyParticleIconCrack extends Particle {

	protected NetworkItemStack item;
	protected ProtocolVersion version;
	protected String locale;

	public LegacyParticleIconCrack(int id, String name, NetworkItemStack item) {
		super(id, name);
		this.item = item;
	}

	@Override
	public void remap(Connection connection, NetworkDataCache cache) {
		version = connection.getVersion();
		locale = cache.getAttributesCache().getLocale();
		item = ItemStackRemapper.remapToClient(version, locale, item);
		name += "_" + item.getTypeId() + "_" + item.getLegacyData();
	}

	@Override
	public void writeData(ByteBuf buf) {
		VarNumberSerializer.writeVarInt(buf, item.getTypeId());
		VarNumberSerializer.writeVarInt(buf, item.getLegacyData());
	}

}
