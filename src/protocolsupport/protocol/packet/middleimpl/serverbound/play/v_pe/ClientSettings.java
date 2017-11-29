package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import org.bukkit.Bukkit;
import org.bukkit.inventory.MainHand;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleClientSettings;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;

public class ClientSettings extends MiddleClientSettings {

	protected int pocketViewDistance;
	
	@Override
	public void readFromClientData(ByteBuf clientdata) {
		pocketViewDistance = VarNumberSerializer.readSVarInt(clientdata);
		locale = cache.getLocale();
		viewDist = Bukkit.getViewDistance();
		chatMode = ChatMode.NORMAL;
		chatColors = true;
		skinFlags = 0x7F;
		mainHand = MainHand.RIGHT;
		sendResponse();
	}
	
	private void sendResponse() {
		ByteBuf chunkResponse = Unpooled.buffer();
		VarNumberSerializer.writeVarInt(chunkResponse, PEPacketIDs.CHUNK_RADIUS);
		chunkResponse.writeByte(0);
		chunkResponse.writeByte(0);
		//should exactly match the view distance that server uses to broadcast chunks. +1 because mcpe includes the chunk client is standing in in calculations, while pc does not
		VarNumberSerializer.writeSVarInt(chunkResponse, (int) Math.ceil(viewDist * Math.sqrt(2)));
		connection.sendRawPacket(MiscSerializer.readAllBytes(chunkResponse));
	}

}
