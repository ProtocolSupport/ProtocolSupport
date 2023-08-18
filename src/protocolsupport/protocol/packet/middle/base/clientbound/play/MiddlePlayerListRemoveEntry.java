package protocolsupport.protocol.packet.middle.base.clientbound.play;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;
import protocolsupport.protocol.storage.netcache.PlayerListCache;
import protocolsupport.protocol.storage.netcache.PlayerListCache.PlayerListEntryData;

public abstract class MiddlePlayerListRemoveEntry extends ClientBoundMiddlePacket {

	protected MiddlePlayerListRemoveEntry(IMiddlePacketInit init) {
		super(init);
	}

	protected final PlayerListCache playerlistCache = cache.getPlayerListCache();

	protected final Map<UUID, PlayerListEntryData> entries = new LinkedHashMap<>();

	@Override
	protected void decode(ByteBuf serverdata) {
		UUID[] uuids = ArrayCodec.readVarIntTArray(serverdata, UUID.class, UUIDCodec::readUUID);
		for (UUID uuid : uuids) {
			entries.put(uuid, playerlistCache.remove(uuid));
		}
	}

	@Override
	protected void cleanup() {
		entries.clear();
	}

}
