package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_16r1;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleChangeDimension;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r1;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.typeremapper.legacy.LegacyDimension;
import protocolsupport.utils.BitUtils;

public class ChangeDimension extends MiddleChangeDimension implements IClientboundMiddlePacketV16r1 {

	public ChangeDimension(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		ClientBoundPacketData changedimension = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_RESPAWN);
		StringCodec.writeVarIntUTF8String(changedimension, LegacyDimension.getStringId(clientCache.getDimension()));
		StringCodec.writeVarIntUTF8String(changedimension, worldName);
		changedimension.writeLong(hashedSeed);
		changedimension.writeByte(gamemodeCurrent.getId());
		changedimension.writeByte(gamemodePrevious.getId());
		changedimension.writeBoolean(worldDebug);
		changedimension.writeBoolean(worldFlat);
		changedimension.writeBoolean(BitUtils.isIBitSet(keepDataFlags, KEEP_DATA_FLAGS_BIT_METADATA));
		io.writeClientbound(changedimension);
	}

}
