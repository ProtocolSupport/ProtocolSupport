package protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound;

import java.io.IOException;
import java.util.ArrayList;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.clientbound.PlayerListPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.clientbound.PlayerListPacket.PEPlayerListEntry;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePlayerInfo;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class PlayerInfo extends MiddlePlayerInfo<RecyclableCollection<? extends ClientboundPEPacket>> {

	@Override
	public RecyclableCollection<? extends ClientboundPEPacket> toData(ProtocolVersion version) throws IOException {
		switch (action) {
			case REMOVE: {
				ArrayList<PEPlayerListEntry> entries = new ArrayList<>();
				for (Info info : infos) {
					entries.add(new PEPlayerListEntry(info.uuid));
				}
				return RecyclableSingletonList.create(new PlayerListPacket(false, entries));
			}
			case ADD: {
				ArrayList<PEPlayerListEntry> entries = new ArrayList<>();
				for (Info info : infos) {
					entries.add(new PEPlayerListEntry(info.uuid, info.getName()));
				}
				return RecyclableSingletonList.create(new PlayerListPacket(true, entries));
			}
			default: {
				return RecyclableEmptyList.get();
			}
		}
	}

}
