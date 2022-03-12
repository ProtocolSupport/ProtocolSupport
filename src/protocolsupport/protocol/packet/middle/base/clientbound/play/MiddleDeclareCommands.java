package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.packet.middle.MiddlePacketCancelException;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public abstract class MiddleDeclareCommands extends ClientBoundMiddlePacket {

	protected MiddleDeclareCommands(IMiddlePacketInit init) {
		super(init);
	}

	//TODO: structure
	protected ByteBuf data;

	@Override
	protected void decode(ByteBuf serverdata) {
		data = MiscDataCodec.readAllBytesSlice(serverdata);

		//TODO: remove after implementing
		if (version.isBefore(ProtocolVersionsHelper.LATEST_PC)) {
			throw MiddlePacketCancelException.INSTANCE;
		}
	}

}
