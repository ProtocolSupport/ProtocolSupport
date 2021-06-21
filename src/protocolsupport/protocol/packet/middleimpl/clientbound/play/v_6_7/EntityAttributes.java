package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_6_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityAttributes;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityAttribute;

public class EntityAttributes extends MiddleEntityAttributes {

	public EntityAttributes(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData entityattributes = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ENTITY_ATTRIBUTES);
		entityattributes.writeInt(entity.getId());
		entityattributes.writeInt(attributes.size());
		for (Attribute attribute : attributes.values()) {
			StringCodec.writeString(entityattributes, version, LegacyEntityAttribute.getLegacyId(attribute.getKey()));
			entityattributes.writeDouble(attribute.getValue());
			if (version != ProtocolVersion.MINECRAFT_1_6_1) {
				ArrayCodec.writeShortTArray(entityattributes, attribute.getModifiers(), (modifierTo, modifier) -> {
					UUIDCodec.writeUUID2L(modifierTo, modifier.getUUID());
					modifierTo.writeDouble(modifier.getAmount());
					modifierTo.writeByte(modifier.getOperation());
				});
			}
		}
		codec.writeClientbound(entityattributes);
	}

}
