package protocolsupport.zplatform.impl.pe;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PEUserData {
    @SerializedName("CapeData")
    @Expose
    private String capeData;
    @SerializedName("ClientRandomId")
    @Expose
    private long clientRandomId;
    @SerializedName("CurrentInputMode")
    @Expose
    private int currentInputMode;
    @SerializedName("DefaultInputMode")
    @Expose
    private int defaultInputMode;
    @SerializedName("DeviceModel")
    @Expose
    private String deviceModel;
    @SerializedName("DeviceOS")
    @Expose
    private int deviceOS;
    @SerializedName("GameVersion")
    @Expose
    private String gameVersion;
    @SerializedName("GuiScale")
    @Expose
    private int guiScale;
    @SerializedName("LanguageCode")
    @Expose
    private String languageCode;
    @SerializedName("ServerAddress")
    @Expose
    private String serverAddress;
    @SerializedName("SkinData")
    @Expose
    private String skinData;
    @SerializedName("SkinGeometry")
    @Expose
    private String skinGeometry;
    @SerializedName("SkinGeometryName")
    @Expose
    private String skinGeometryName;
    @SerializedName("SkinId")
    @Expose
    private String skinId;
    @SerializedName("UIProfile")
    @Expose
    private int uIProfile;

    public String getCapeData() {
        return capeData;
    }

    public void setCapeData(String capeData) {
        this.capeData = capeData;
    }

    public long getClientRandomId() {
        return clientRandomId;
    }

    public void setClientRandomId(long clientRandomId) {
        this.clientRandomId = clientRandomId;
    }

    public int getCurrentInputMode() {
        return currentInputMode;
    }

    public void setCurrentInputMode(int currentInputMode) {
        this.currentInputMode = currentInputMode;
    }

    public int getDefaultInputMode() {
        return defaultInputMode;
    }

    public void setDefaultInputMode(int defaultInputMode) {
        this.defaultInputMode = defaultInputMode;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public int getDeviceOS() {
        return deviceOS;
    }

    public void setDeviceOS(int deviceOS) {
        this.deviceOS = deviceOS;
    }

    public String getGameVersion() {
        return gameVersion;
    }

    public void setGameVersion(String gameVersion) {
        this.gameVersion = gameVersion;
    }

    public int getGuiScale() {
        return guiScale;
    }

    public void setGuiScale(int guiScale) {
        this.guiScale = guiScale;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getSkinData() {
        return skinData;
    }

    public void setSkinData(String skinData) {
        this.skinData = skinData;
    }

    public String getSkinGeometry() {
        return skinGeometry;
    }

    public void setSkinGeometry(String skinGeometry) {
        this.skinGeometry = skinGeometry;
    }

    public String getSkinGeometryName() {
        return skinGeometryName;
    }

    public void setSkinGeometryName(String skinGeometryName) {
        this.skinGeometryName = skinGeometryName;
    }

    public String getSkinId() {
        return skinId;
    }

    public void setSkinId(String skinId) {
        this.skinId = skinId;
    }

    public int getUIProfile() {
        return uIProfile;
    }

    public void setUIProfile(int uIProfile) {
        this.uIProfile = uIProfile;
    }
}