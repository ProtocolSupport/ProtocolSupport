package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15;

import org.bukkit.util.Vector;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.MiddlePacketCancelException;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV10;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV11;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV12r1;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV12r2;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV13;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV14r1;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV14r2;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV15;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV9r1;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV9r2;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.AbstractSneakingCacheUseEntity;
import protocolsupport.protocol.storage.netcache.NetworkEntityCache;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.TameableNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.UsedHand;
import protocolsupport.protocol.types.networkentity.NetworkEntity;

public class UseEntity extends AbstractSneakingCacheUseEntity implements
IServerboundMiddlePacketV9r1,
IServerboundMiddlePacketV9r2,
IServerboundMiddlePacketV10,
IServerboundMiddlePacketV11,
IServerboundMiddlePacketV12r1,
IServerboundMiddlePacketV12r2,
IServerboundMiddlePacketV13,
IServerboundMiddlePacketV14r1,
IServerboundMiddlePacketV14r2,
IServerboundMiddlePacketV15 {

	public UseEntity(IMiddlePacketInit init) {
		super(init);
	}

	protected final NetworkEntityCache entityCache = cache.getEntityCache();

	@Override
	protected void read(ByteBuf clientdata) {
		entityId = VarNumberCodec.readVarInt(clientdata);
		action = MiscDataCodec.readVarIntEnum(clientdata, Action.CONSTANT_LOOKUP);
		switch (action) {
			case INTERACT: {
				hand = MiscDataCodec.readVarIntEnum(clientdata, UsedHand.CONSTANT_LOOKUP);
				break;
			}
			case INTERACT_AT: {
				interactedAt = new Vector(clientdata.readFloat(), clientdata.readFloat(), clientdata.readFloat());
				hand = MiscDataCodec.readVarIntEnum(clientdata, UsedHand.CONSTANT_LOOKUP);
				break;
			}
			case ATTACK: {
				break;
			}
		}
	}

	@Override
	protected void handle() {
		if ((hand == UsedHand.OFF) && version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_14_4)) {
			NetworkEntity entity = entityCache.getEntity(entityId);
			if ((entity != null) && clientCache.getUUID().equals(entity.getDataCache().getData(TameableNetworkEntityMetadataFormatTransformerFactory.DATA_KEY_OWNER))) {
				throw MiddlePacketCancelException.INSTANCE;
			}
		}

		super.handle();
	}

}
