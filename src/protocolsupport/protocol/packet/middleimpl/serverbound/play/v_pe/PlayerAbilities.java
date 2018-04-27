package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddlePlayerAbilities;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEAdventureSettings;

public class PlayerAbilities extends MiddlePlayerAbilities {

	protected int peFlags;

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		peFlags = VarNumberSerializer.readVarInt(clientdata);
		flags = (((peFlags & PEAdventureSettings.FLYING) == PEAdventureSettings.FLYING) ? flagOffsetCanFly : 0) |
				(((peFlags & PEAdventureSettings.ALLOW_FLIGHT) == PEAdventureSettings.ALLOW_FLIGHT) ? flagOffsetIsFlying : 0);
		VarNumberSerializer.readVarInt(clientdata);
		VarNumberSerializer.readVarInt(clientdata);
		VarNumberSerializer.readVarInt(clientdata);
		VarNumberSerializer.readVarInt(clientdata);
		clientdata.readLongLE();
	}

}
