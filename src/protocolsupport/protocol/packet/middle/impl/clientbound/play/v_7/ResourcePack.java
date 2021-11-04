package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_7;

import io.netty.buffer.ByteBufUtil;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleResourcePack;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;

public class ResourcePack extends MiddleResourcePack implements IClientboundMiddlePacketV7 {

	public ResourcePack(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData resourcepack = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_CUSTOM_PAYLOAD);
		StringCodec.writeVarIntUTF8String(resourcepack, "MC|RPack");
		MiscDataCodec.writeShortLengthPrefixedType(resourcepack, url, ByteBufUtil::writeUtf8);
		io.writeClientbound(resourcepack);
	}

}
