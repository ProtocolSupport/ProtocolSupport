package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v1_5_1_6_1_7;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleInventoryOpen;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.protocol.transformer.middlepacketimpl.SupportedVersions;
import protocolsupport.protocol.transformer.utils.LegacyUtils;
import protocolsupport.protocol.typeskipper.id.IdSkipper;
import protocolsupportbuildprocessor.annotations.NeedsNoArgConstructor;

@NeedsNoArgConstructor
@SupportedVersions({ProtocolVersion.MINECRAFT_1_7_10, ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_6_4, ProtocolVersion.MINECRAFT_1_6_2, ProtocolVersion.MINECRAFT_1_5_2})
public class InventoryOpen extends MiddleInventoryOpen<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) throws IOException {
		int id = LegacyUtils.getInventoryId(invname);
		if (IdSkipper.INVENTORY.getTable(version).shouldSkip(id)) {
			player.closeInventory();
			return Collections.emptyList();
		}
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeByte(windowId);
		serializer.writeByte(id);
		serializer.writeString(LegacyUtils.toText(ChatSerializer.a(titleJson)));
		serializer.writeByte(slots);
		serializer.writeBoolean(true);
		if (id == 11) {
			serializer.writeInt(horseId);
		}
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_WINDOW_OPEN_ID, serializer));
	}

}
