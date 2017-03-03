package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6;

import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;

import net.minecraft.server.v1_10_R1.ChatComponentText;
import net.minecraft.server.v1_10_R1.EntityPlayer;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;

public class KickDisconnect extends ServerBoundMiddlePacket {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		// Based on: https://github.com/ProtocolSupport/ProtocolSupport/pull/36
		EntityPlayer ep = ((CraftPlayer) player).getHandle();
		ep.playerConnection.networkManager.close(new ChatComponentText(serializer.readString()));
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		return RecyclableEmptyList.get();
	}

}
