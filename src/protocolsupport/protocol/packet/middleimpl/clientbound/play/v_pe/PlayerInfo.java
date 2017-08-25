package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePlayerInfo;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.typeremapper.pe.PESkin;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zplatform.impl.spigot.itemstack.SpigotItemStackWrapper;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.pe.PECraftingManager;

public class PlayerInfo extends MiddlePlayerInfo {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		switch (action) {
			case ADD: {
				ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.PLAYER_INFO, version);
				serializer.writeByte(0);
				VarNumberSerializer.writeVarInt(serializer, infos.length);
				for (Info info : infos) {
					MiscSerializer.writeUUID(serializer, info.uuid);
					VarNumberSerializer.writeVarInt(serializer, 0); //entity id
					StringSerializer.writeString(serializer, version, info.getName(cache.getLocale()));
					StringSerializer.writeString(serializer, version, "Standard_Steve");
					ArraySerializer.writeByteArray(serializer, version, PESkin.STEVE);
				}
				return RecyclableSingletonList.create(serializer);
			}
			case REMOVE: {
				ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.PLAYER_INFO, version);
				serializer.writeByte(1);
				VarNumberSerializer.writeVarInt(serializer, infos.length);
				for (Info info : infos) {
					MiscSerializer.writeUUID(serializer, info.uuid);
				}
				return RecyclableSingletonList.create(serializer);
			}
			default: {
				return RecyclableEmptyList.get();
			}
		}

		packets.add(PECraftingManager.getInstance().getAllRecipies());
		return packets;
	}

}
