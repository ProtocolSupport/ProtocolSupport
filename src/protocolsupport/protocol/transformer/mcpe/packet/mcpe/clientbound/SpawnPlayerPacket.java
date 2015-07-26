package protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound;

import io.netty.buffer.ByteBuf;

import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;

public class SpawnPlayerPacket implements ClientboundPEPacket {

	private int clientID;
	private String username;
	private int eid;
	private int x;
	private int y;
	private int z;
	private float speedX;
	private float speedY;
	private float speedZ;
	private float pitch;
	private float yaw;
	private int item;
	private int meta;
	private byte[] metadata;
	private boolean slim;
	private String skin;

	@Override
	public int getId() {
		return 0x88;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
