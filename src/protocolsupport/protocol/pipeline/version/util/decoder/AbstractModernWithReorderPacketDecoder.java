package protocolsupport.protocol.pipeline.version.util.decoder;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.pipeline.IPacketCodec;
import protocolsupport.protocol.typeremapper.packet.AnimatePacketReorderer;

public class AbstractModernWithReorderPacketDecoder extends AbstractModernPacketDecoder {

	protected final AnimatePacketReorderer animateReorderer = new AnimatePacketReorderer(connection);

	public AbstractModernWithReorderPacketDecoder(ConnectionImpl connection, IPacketCodec codec) {
		super(connection, codec);
		connection.addServerboundPacketProcessor(animateReorderer);
	}

}
