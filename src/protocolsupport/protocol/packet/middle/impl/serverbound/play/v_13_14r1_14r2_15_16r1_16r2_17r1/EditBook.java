package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_13_14r1_14r2_15_16r1_16r2_17r1;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleEditBook;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV13;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV14r1;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV14r2;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV15;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV16r1;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV16r2;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV17r1;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.types.UsedHand;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.CommonNBT;

public class EditBook extends MiddleEditBook implements
IServerboundMiddlePacketV13,
IServerboundMiddlePacketV14r1,
IServerboundMiddlePacketV14r2,
IServerboundMiddlePacketV15,
IServerboundMiddlePacketV16r1,
IServerboundMiddlePacketV16r2,
IServerboundMiddlePacketV17r1 {

	public EditBook(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void read(ByteBuf clientdata) {
		NBTCompound bookTag = ItemStackCodec.readItemStack(clientdata, version).getNBT();
		boolean signing = clientdata.readBoolean();
		if (version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_16_4)) {
			slot = VarNumberCodec.readVarInt(clientdata);
		} else {
			UsedHand hand = MiscDataCodec.readVarIntEnum(clientdata, UsedHand.CONSTANT_LOOKUP);
			slot = hand == UsedHand.MAIN ? clientCache.getHeldSlot() : 40;
		}
		pages = CommonNBT.getBookPages(bookTag);
		title = signing ? bookTag.getStringTagOrThrow(CommonNBT.BOOK_TITLE).getValue() : null;
	}

}
