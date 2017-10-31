package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import org.bukkit.Bukkit;

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
		viewDist = (int) Math.floor((pocketViewDistance - 1) / Math.sqrt(2));
		chatMode = 0;
		chatColors = true;
		skinFlags = 0x7F;
		mainHand = 1;
		if(viewDist > Bukkit.getViewDistance()) {
			viewDist = Bukkit.getViewDistance();
		}
		sendResponse();
	}
	
	public void sendResponse() {
		System.err.println("Viewdistance from client: " + pocketViewDistance + " to server: " + viewDist + " To client: " + (int) Math.ceil(viewDist * Math.sqrt(2)));
		
		ByteBuf chunkResponse = Unpooled.buffer();
		VarNumberSerializer.writeVarInt(chunkResponse, PEPacketIDs.CHUNK_RADIUS);
		chunkResponse.writeByte(0);
		chunkResponse.writeByte(0);
		//should exactly match the view distance that server uses to broadcast chunks. +1 because mcpe includes the chunk client is standing in in calculations, while pc does not
		VarNumberSerializer.writeSVarInt(chunkResponse, pocketViewDistance);
		connection.sendRawPacket(MiscSerializer.readAllBytes(chunkResponse));
	}

}
