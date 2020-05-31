package protocolsupport.protocol.pipeline.version.util.decoder;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.PacketDataCodecImpl;
import protocolsupport.protocol.typeremapper.packet.AnimatePacketReorderer;

public class AbstractModernWithReorderPacketDecoder extends AbstractModernPacketDecoder {

	public AbstractModernWithReorderPacketDecoder(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void init(PacketDataCodecImpl codec) {
		super.init(codec);
		codec.addServerboundPacketProcessor(new AnimatePacketReorderer());
	}

}
