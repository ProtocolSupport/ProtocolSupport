package protocolsupport.protocol.packet.middleimpl.clientbound.status.v_5_6;

import java.util.StringJoiner;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.status.MiddleServerInfo;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class ServerInfo extends MiddleServerInfo {

	public ServerInfo(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<? extends IPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_STATUS_SERVER_INFO);
		String response = new StringJoiner("\u0000")
		.add("§1")
		.add(String.valueOf(ping.getProtocolData().getVersion()))
		.add(ping.getProtocolData().getName())
		.add(ping.getMotd().toLegacyText(cache.getAttributesCache().getLocale()))
		.add(String.valueOf(ping.getPlayers().getOnline()))
		.add(String.valueOf(ping.getPlayers().getMax()))
		.toString();
		StringSerializer.writeShortUTF16BEString(serializer, response);
		return RecyclableSingletonList.create(serializer);
	}

}
