package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.networkentity.NetworkEntity;

public abstract class MiddleCollectEffect extends ClientBoundMiddlePacket {

	public MiddleCollectEffect(ConnectionImpl connection) {
		super(connection);
	}

	protected int entityId;
	protected int collectorId;
	protected int itemCount;

	@Override
	public void readServerData(ByteBuf serverdata) {
		entityId = VarNumberSerializer.readVarInt(serverdata);
		collectorId = VarNumberSerializer.readVarInt(serverdata);
		itemCount = VarNumberSerializer.readVarInt(serverdata);
	}

	@Override
	public void handleReadData() {
		//TODO: send needed collect packets from middle packet itself after implementing serverside entity position cache
		if (
			(collectorId == cache.getWatchedEntityCache().getSelfPlayerEntityId()) &&
			(version.getProtocolType() == ProtocolType.PC) &&
			version.isBefore(ProtocolVersion.MINECRAFT_1_9)
		) {
			Player player = connection.getPlayer();
			NetworkEntity entity = cache.getWatchedEntityCache().getWatchedEntity(entityId);
			if ((entity != null) && (player != null)) {
				switch (entity.getType()) {
					case ITEM: {
						player.playSound(
							player.getLocation(), Sound.ENTITY_ITEM_PICKUP,
							0.2F, (((ThreadLocalRandom.current().nextFloat() - ThreadLocalRandom.current().nextFloat()) * 0.7F) + 1.0F) * 2.0F
						);
						break;
					}
					case EXP_ORB: {
						player.playSound(
							player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,
							0.2F, (((ThreadLocalRandom.current().nextFloat() - ThreadLocalRandom.current().nextFloat()) * 0.7F) + 1.0F) * 2.0F
						);
						break;
					}
					default: {
						break;
					}
				}
			}
		}
	}

}
