package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_17r1_17r2;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleEntityAttributes;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;

public class EntityAttributes extends MiddleEntityAttributes implements
IClientboundMiddlePacketV17r1,
IClientboundMiddlePacketV17r2 {

	public EntityAttributes(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData entityattributes = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ENTITY_ATTRIBUTES);
		VarNumberCodec.writeVarInt(entityattributes, entity.getId());
		VarNumberCodec.writeVarInt(entityattributes, attributes.size());
		for (Attribute attribute : attributes.values()) {
			StringCodec.writeVarIntUTF8String(entityattributes, attribute.getKey());
			entityattributes.writeDouble(attribute.getValue());
			ArrayCodec.writeVarIntTArray(entityattributes, attribute.getModifiers(), (modifierTo, modifier) -> {
				UUIDCodec.writeUUID2L(modifierTo, modifier.getUUID());
				modifierTo.writeDouble(modifier.getAmount());
				modifierTo.writeByte(modifier.getOperation());
			});
		}
		io.writeClientbound(entityattributes);
	}

}
