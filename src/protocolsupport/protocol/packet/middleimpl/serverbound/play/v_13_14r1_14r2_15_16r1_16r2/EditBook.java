package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13_14r1_14r2_15_16r1_16r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleEditBook;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.types.UsedHand;

public class EditBook extends MiddleEditBook {

	protected final ClientCache clientCache = cache.getClientCache();

	public EditBook(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		book = ItemStackCodec.readItemStack(clientdata, version);
		signing = clientdata.readBoolean();
		if (version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_16_4)) {
			slot = VarNumberCodec.readVarInt(clientdata);
		} else {
			UsedHand hand = MiscDataCodec.readVarIntEnum(clientdata, UsedHand.CONSTANT_LOOKUP);
			slot = hand == UsedHand.MAIN ? clientCache.getHeldSlot() : 40;
		}
	}

}
