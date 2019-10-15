package protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_beta;

import org.bukkit.Bukkit;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class ClientHandshake extends ServerBoundMiddlePacket {

	public ClientHandshake(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		StringSerializer.readShortUTF16BEString(clientdata, Short.MAX_VALUE);
	}

	@Override
	public RecyclableCollection<IPacketData> toNative()  {
		RecyclableArrayList<IPacketData> packets = RecyclableArrayList.create();

		ClientBoundPacketData encryptionbegin = ClientBoundPacketData.create(PacketType.CLIENTBOUND_LOGIN_ENCRYPTION_BEGIN);
		StringSerializer.writeString(encryptionbegin, version, "-");
		packets.add(encryptionbegin);

		ServerBoundPacketData handshake = ServerBoundPacketData.create(PacketType.SERVERBOUND_HANDSHAKE_START);
		VarNumberSerializer.writeVarInt(handshake, ProtocolVersionsHelper.LATEST_PC.getId());
		StringSerializer.writeVarIntUTF8String(handshake, "");
		handshake.writeShort(Bukkit.getPort());
		VarNumberSerializer.writeVarInt(handshake, 2);
		packets.add(handshake);

		return packets;
	}

}
