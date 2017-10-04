package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.types.NetworkEntity;

import java.util.concurrent.ThreadLocalRandom;

public abstract class MiddleCollectEffect extends ClientBoundMiddlePacket {

	protected int entityId;
	protected int collectorId;
	protected int itemCount;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		entityId = VarNumberSerializer.readVarInt(serverdata);
		collectorId = VarNumberSerializer.readVarInt(serverdata);
		itemCount = VarNumberSerializer.readVarInt(serverdata);
	}

	@Override
	public boolean postFromServerRead() {
		if (connection.getVersion().isBefore(ProtocolVersion.MINECRAFT_1_9)) {
			Player player = connection.getPlayer();
			NetworkEntity entity = cache.getWatchedEntity(entityId);
			if ((entity != null && entity.getUUID() != null) && (player != null)) {
				Entity worldEntity = player.getServer().getEntity(entity.getUUID());
				switch (entity.getType()) {
					case ITEM: {
						player.playSound(
							worldEntity.getLocation(), Sound.ENTITY_ITEM_PICKUP,
							0.2F, (((ThreadLocalRandom.current().nextFloat() - ThreadLocalRandom.current().nextFloat()) * 0.7F) + 1.0F) * 2.0F
						);
						break;
					}
					case EXP_ORB: {
						player.playSound(
							worldEntity.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,
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
		return true;
	}

}
