package protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;

public class AdventureSettingsPacket implements ClientboundPEPacket {

	protected int flags = 0;
	protected int userPermissions = 2;
	protected int globalPermissions = 2;

	public AdventureSettingsPacket(boolean isCreative) {
		enableAutoJump();
		if (isCreative) {
			setMayFly();
		}
	}

	public void setAdventureMode() {
		flags |= 0x1;
	}

	public void setCantAttackPlayers() {
		flags |= 0x2;
	}

	public void setCantAttackMobs() {
		flags |= 0x4;
	}

	public void dontTickTime() {
		flags |= 0x10;
	}

	public void enableAutoJump() {
		flags |= 0x40;
	}

	public void setMayFly() {
		flags |= 0x80;
	}

	@Override
	public int getId() {
		return PEPacketIDs.ADVENTURE_SETTINGS_PACKET;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		buf.writeInt(flags);
		buf.writeInt(userPermissions);
		buf.writeInt(globalPermissions);
		return this;
	}

}
