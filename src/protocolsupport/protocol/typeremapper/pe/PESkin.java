package protocolsupport.protocol.typeremapper.pe;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.mineskin.MineskinClient;
import org.mineskin.SkinOptions;
import org.mineskin.data.Skin;
import org.mineskin.data.SkinCallback;
import protocolsupport.ProtocolSupport;
import protocolsupport.protocol.storage.NetworkDataCache;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class PESkin {
    private static final MineskinClient client = new MineskinClient();
    private final String skinId;
    private final byte[] skinData;
    private final byte[] capeData;
    private NetworkDataCache cache;

    public PESkin(String skinId, String skinData, String capeData) {
        this.skinId = skinId;
        this.skinData = Base64.getDecoder().decode(skinData.getBytes());
        this.capeData = Base64.getDecoder().decode(capeData.getBytes());

        toDesktopSkin();
    }

    public PESkin(String skinId, String skinData) {
        this(skinId, skinData, "");
    }

    /// Converts Bedrock Skin Bitmap to PNG and uploads it to Mineskin
    private void toDesktopSkin() {
        if (!isValid()) return;

        DataBuffer buffer = new DataBufferByte(skinData, skinData.length);

        //4 bytes per pixel: red, green, blue, alpha
        WritableRaster raster = Raster.createInterleavedRaster(buffer, 64, isSmall() ? 32 : 64, 4 * 64, 4, new int[]{0, 1, 2, 3}, null);
        ColorModel cm = new ComponentColorModel(ColorModel.getRGBdefault().getColorSpace(), true, false, Transparency.TRANSLUCENT, DataBuffer.TYPE_BYTE);
        BufferedImage image = new BufferedImage(cm, raster, true, null);

        // Create a temporary file to upload our skin
        try {
            File temp = File.createTempFile("bedrock-skin", ".png");
            temp.deleteOnExit();
            ImageIO.write(image, "png", temp);

            client.generateUpload(temp, SkinOptions.name(skinId), new SkinCallback() {
                @Override
                public void error(String s) {
                    temp.delete();
                }

                @Override
                public void exception(Exception exception) {
                    ProtocolSupport.getInstance().getLogger().warning("Exception while generating skin: " + exception);
                }

                @Override
                public void done(Skin skin) {
                    temp.delete();
                    if (cache == null) return;

                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("id", skin.data.uuid.toString());
                    jsonObject.addProperty("name", "");

                    JsonObject property = new JsonObject();
                    property.addProperty("name", "textures");
                    property.addProperty("value", skin.data.texture.value);
                    property.addProperty("signature", skin.data.texture.signature);

                    JsonArray propertiesArray = new JsonArray();
                    propertiesArray.add(property);

                    jsonObject.add("properties", propertiesArray);

                    cache.setSkinData(new Gson().toJson(jsonObject));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isSmall() {
        return skinData.length == 8192;
    }

    public boolean isValid() {
        return (skinData.length == 16384 || skinData.length == 8192) &&
                (capeData.length == 8192 || capeData.length == 0);
    }

    public String getSkinId() {
        return skinId;
    }

    public byte[] getSkinData() {
        return skinData;
    }

    public byte[] getCapeData() {
        return capeData;
    }

    /// Apply when available the skin to a player
    public void apply(NetworkDataCache dataCache) {
        cache = dataCache;
    }
}
