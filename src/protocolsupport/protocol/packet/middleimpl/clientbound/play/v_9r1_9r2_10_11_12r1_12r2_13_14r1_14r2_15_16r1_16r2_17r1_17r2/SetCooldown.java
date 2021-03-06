package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17r1_17r2;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetCooldown;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackRemappingHelper;

public class SetCooldown extends MiddleSetCooldown {

	public SetCooldown(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData setcooldown = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SET_COOLDOWN);
		VarNumberCodec.writeVarInt(setcooldown, ItemStackRemappingHelper.toLegacyItemDataFormat(version, itemId));
		VarNumberCodec.writeVarInt(setcooldown, cooldown);
		codec.writeClientbound(setcooldown);
	}

}
