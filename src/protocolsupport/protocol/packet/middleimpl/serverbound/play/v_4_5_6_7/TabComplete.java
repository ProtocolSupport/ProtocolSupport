package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleTabComplete;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class TabComplete extends MiddleTabComplete {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		string = StringSerializer.readString(clientdata, connection.getVersion(), 256);
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		String string = this.string;
		if (string.startsWith("/"))
			string = string.substring(1);
		return RecyclableSingletonList.create(MiddleTabComplete.create(0, string));
	}
}
