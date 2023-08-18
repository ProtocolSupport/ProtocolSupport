package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_17r1__17r2;

import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleChangeDimension;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.utils.BitUtils;

public class ChangeDimension extends MiddleChangeDimension implements
IClientboundMiddlePacketV17r1,
IClientboundMiddlePacketV17r2 {

	public ChangeDimension(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		ClientBoundPacketData changedimension = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_RESPAWN);
		ItemStackCodec.writeDirectTag(changedimension, StartGame.toLegacyDimensionType(clientCache.getDimension()));
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
