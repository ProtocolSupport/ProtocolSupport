package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17r1_17r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleAdvancementTab;

public class AdvancementTab extends MiddleAdvancementTab {

	public AdvancementTab(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		action = MiscDataCodec.readVarIntEnum(clientdata, Action.CONSTANT_LOOKUP);
		if (action == Action.OPEN) {
			identifier = StringCodec.readVarIntUTF8String(clientdata, Short.MAX_VALUE);
		}
	}

}
