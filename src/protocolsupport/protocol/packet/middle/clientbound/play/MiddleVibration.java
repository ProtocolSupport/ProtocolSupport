package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.types.VibrationPath;

public abstract class MiddleVibration extends ClientBoundMiddlePacket {

	protected MiddleVibration(MiddlePacketInit init) {
		super(init);
	}

	protected VibrationPath path;

	@Override
	protected void decode(ByteBuf serverdata) {
		path = VibrationPath.fromNetworkData(serverdata);
	}

}
