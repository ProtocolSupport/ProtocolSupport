package protocolsupport.protocol.transformer.mcpe.middlepacketimpl.clientbound;

import java.io.IOException;

import net.minecraft.server.v1_9_R1.NBTTagCompound;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.TileEntityDataPacket;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleBlockSignUpdate;
import protocolsupport.protocol.transformer.utils.LegacyUtils;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class BlockSignUpdate extends MiddleBlockSignUpdate<RecyclableCollection<? extends ClientboundPEPacket>> {

	@Override
	public RecyclableCollection<? extends ClientboundPEPacket> toData(ProtocolVersion version) throws IOException {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setString("id", "Sign");
		compound.setInt("x", position.getX());
		compound.setInt("y", position.getY());
		compound.setInt("z", position.getZ());
		for (int i = 0; i < 4; i++) {
			compound.setString("Text"+(i + 1), LegacyUtils.toText(ChatAPI.fromJSON(linesJson[i])));
		}
		return RecyclableSingletonList.create(new TileEntityDataPacket(position.getX(), position.getY(), position.getZ(), compound));
	}

}
