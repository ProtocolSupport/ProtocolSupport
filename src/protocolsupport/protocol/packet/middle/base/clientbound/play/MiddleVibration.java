package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;
import protocolsupport.protocol.types.VibrationPath;

public abstract class MiddleVibration extends ClientBoundMiddlePacket {

	protected MiddleVibration(IMiddlePacketInit init) {
		super(init);
	}

	protected VibrationPath path;

	@Override
	protected void decode(ByteBuf serverdata) {
		path = VibrationPath.fromNetworkData(serverdata);
	}

}
