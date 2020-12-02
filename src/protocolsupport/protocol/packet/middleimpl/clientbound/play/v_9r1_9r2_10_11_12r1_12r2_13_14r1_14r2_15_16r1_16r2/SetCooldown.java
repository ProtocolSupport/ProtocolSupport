package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetCooldown;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackRemapper;

public class SetCooldown extends MiddleSetCooldown {

	public SetCooldown(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData setcooldown = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SET_COOLDOWN);
		VarNumberSerializer.writeVarInt(setcooldown, ItemStackRemapper.remapItemIdClientbound(version, itemId));
		VarNumberSerializer.writeVarInt(setcooldown, cooldown);
		codec.write(setcooldown);
	}

}
