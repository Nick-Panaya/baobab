package io.mosaicnetworks.baobab;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.Date;

public final class Message implements IMessage {

    public final static class Author implements IUser {
        private final String mName;

        public Author(String name) {
            mName = name;
        }

        @Override
        public String getId() {
            return mName;
        }

        @Override

        public String getName() {
            return mName;
        }

        @Override
        public String getAvatar() {
            return null;
        }
    }

    private final String mType;
    private final String mText;
    private final int mAmount;
    private final String mAuthor;
    private final Date mDate;

    public Message(String text, String author, String type, int amount) {
        mText = text;
        mAuthor = author;
        mType = type;
        mAmount = amount;
        mDate = new Date();
    }

    public static Message fromBabbleTx(BabbleTx babbleTx) {
        return new Message(babbleTx.text, babbleTx.from, babbleTx.type, babbleTx.amount);
    }

    public BabbleTx toBabbleTx() {
        return new BabbleTx(mAuthor, mType, mText, mAmount);
    }

    @Override
    public String getId() {
        return mAuthor;
    }

    @Override
    public String getText() {

        switch (mType) {
            case "INIT":
                return mText;
            case "BID":
                return String.valueOf(mAmount);
            case "CLOSE":
                return "CLOSED";
        }

        return "";
    }

    @Override
    public Author getUser() {
        return new Author(mAuthor);
    }

    @Override
    public Date getCreatedAt() {
        return mDate;
    }
}
