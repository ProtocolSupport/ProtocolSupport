package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__7;

import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddlePlayerListRemoveEntry;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;
import protocolsupport.protocol.storage.netcache.PlayerListCache.PlayerListEntryData;

public class PlayerListRemoveEntry extends MiddlePlayerListRemoveEntry implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6,
IClientboundMiddlePacketV7
{

	public PlayerListRemoveEntry(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		for (PlayerListEntryData data : entries.values()) {
			io.writeClientbound(PlayerListSetEntry.createRemove(version, data.getLegacyDisplayName()));
		}
	}

}
