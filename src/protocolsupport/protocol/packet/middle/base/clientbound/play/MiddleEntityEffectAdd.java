package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.OptionalCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.MiddlePacketCancelException;
import protocolsupport.protocol.typeremapper.basic.GenericIdSkipper;
import protocolsupport.protocol.typeremapper.utils.SkippingTable.ArrayBasedIntSkippingTable;
import protocolsupport.protocol.types.nbt.NBTCompound;

public abstract class MiddleEntityEffectAdd extends MiddleEntityData {

	protected MiddleEntityEffectAdd(IMiddlePacketInit init) {
		super(init);
	}

	protected final ArrayBasedIntSkippingTable effectSkipTable = GenericIdSkipper.EFFECT.getTable(version);

	protected int effectId;
	protected int amplifier;
	protected int duration;
	protected int flags;
	protected NBTCompound factor;

	@Override
	protected void decodeData(ByteBuf serverdata) {
		effectId = VarNumberCodec.readVarInt(serverdata);
		amplifier = serverdata.readByte();
		duration = VarNumberCodec.readVarInt(serverdata);
		flags = serverdata.readByte();
		factor = OptionalCodec.readOptional(serverdata, ItemStackCodec::readDirectTag);
	}

	@Override
	protected void decodeDataLast(ByteBuf serverdata) {
		if (effectSkipTable.isSet(effectId)) {
			throw MiddlePacketCancelException.INSTANCE;
		}
	}

}
