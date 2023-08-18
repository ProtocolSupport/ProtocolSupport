package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_18;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleChangeDimension;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV18;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.utils.BitUtils;

public class ChangeDimension extends MiddleChangeDimension implements IClientboundMiddlePacketV18 {

	public ChangeDimension(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		NBTCompound dimension = clientCache.getDimension();

		ClientBoundPacketData changedimension = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_RESPAWN);
		ItemStackCodec.writeDirectTag(changedimension, StartGame.toLegacyDimensionType(dimension, version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_18)));
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
