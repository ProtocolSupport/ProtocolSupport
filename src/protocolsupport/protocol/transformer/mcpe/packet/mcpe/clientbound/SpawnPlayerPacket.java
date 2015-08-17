package protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound;

import java.util.UUID;

import net.minecraft.server.v1_8_R3.ItemStack;
import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;

public class SpawnPlayerPacket implements ClientboundPEPacket {

	protected UUID uuid;
	protected String username;
	protected int eid;
	protected int x;
	protected int y;
	protected int z;
	protected float speedX;
	protected float speedY;
	protected float speedZ;
	protected float pitch;
	protected float yaw;
	protected ItemStack item;
	protected byte[] metadata;

	@Override
	public int getId() {
		return PEPacketIDs.ADD_PLAYER_PACKET;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
