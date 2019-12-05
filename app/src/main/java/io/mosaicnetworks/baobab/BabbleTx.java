package io.mosaicnetworks.baobab;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class BabbleTx implements io.mosaicnetworks.babble.node.BabbleTx {

    private final static Gson gson = new Gson();

    @SerializedName("from")
    public final String from;

    @SerializedName("type")
    public final String type;

    @SerializedName("text")
    public final String text;

    @SerializedName("amount")
    public final int amount;


    public BabbleTx(String from, String type, String text, int amount) {
        this.from = from;
        this.type = type;
        this.text = text;
        this.amount = amount;
    }

    public static BabbleTx fromJson(String txJson) {
        return gson.fromJson(txJson, BabbleTx.class);
    }

    @Override
    public byte[] toBytes() {
        return gson.toJson(this).getBytes();
    }
}
