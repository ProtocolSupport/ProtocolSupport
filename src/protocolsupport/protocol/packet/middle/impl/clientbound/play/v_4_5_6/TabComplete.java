package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6;

import java.util.Arrays;
import java.util.stream.Collectors;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleTabComplete;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.utils.MiscUtils;

public class TabComplete extends MiddleTabComplete implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6
{

	public TabComplete(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		String prefix = start <= 1 ? "/" : "";
		ClientBoundPacketData tabcomplete = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_TAB_COMPLETE);
		StringCodec.writeShortUTF16BEString(tabcomplete, MiscUtils.clampString(String.join("\u0000", Arrays.stream(matches).map(input -> prefix + input.getMatch()).collect(Collectors.toList())), Short.MAX_VALUE));
		io.writeClientbound(tabcomplete);
	}

}
