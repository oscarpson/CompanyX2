package joslabs.companyx.chatsreal;

/**
 * Created by OSCAR on 8/4/2017.
 */

public class ChatsReal {
    String username,profilepic,chatimg,time,chattext,myId;

    public ChatsReal() {
    }

    public ChatsReal( String username, String chatimg, String time, String chattext) {
        this.username=username;
        this.chatimg = chatimg;
        this.time = time;
        this.chattext = chattext;
    }

    public String getUsername() {
        return username;
    }

    public String getMyId() {
        return myId;
    }

    public void setMyId(String myId) {
        this.myId = myId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getChatimg() {
        return chatimg;
    }

    public void setChatimg(String chatimg) {
        this.chatimg = chatimg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getChattext() {
        return chattext;
    }

    public void setChattext(String chattext) {
        this.chattext = chattext;
    }
}
