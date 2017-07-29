package com.javonlee.sample;

/**
 * Created by lijinfeng on 2017/7/26.
 */

public class ConversationEntity {

    public static final String TEST_JSON = "[{\"username\":\"1\",\"nickname\":\"Jam\",\"avatar\":" +
            "\"http://img3.imgtn.bdimg.com/it/u=2113581648,1740074228&fm=214&gp=0.jpg\",\"messageN" +
            "um\":6,\"lastMessage\":\"It's a nice day today.\",\"lastTime\":\"13:50\",\"isRead\":fa" +
            "lse},{\"username\":\"2\",\"nickname\":\"Aaron\",\"avatar\":\"http://dynamic-image.yes" +
            "ky.com/600x-/uploadImages/upload/20140909/upload/201409/0txy3iz3byujpg.jpg\",\"messa" +
            "geNum\":13,\"lastMessage\":\"Do you have time today?\",\"lastTime\":\"13:50\",\"isRea" +
            "d\":false},{\"username\":\"3\",\"nickname\":\"Yannik\",\"avatar\":\"http://pic.qqtn.c" +
            "om/up/2016-3/2016031510554162452.jpg\",\"messageNum\":22,\"lastMessage\":\"Why didn't" +
            " you come to the party?\",\"lastTime\":\"13:10\",\"isRead\":false},{\"username\":\"4\"" +
            ",\"nickname\":\"Cadence\",\"avatar\":\"http://p.3761.com/pic/52311409792413.jpg\",\"mes" +
            "sageNum\":3,\"lastMessage\":\"What was the homework assigned by the teacher yesterday?\"" +
            ",\"lastTime\":\"12:05\",\"isRead\":false},{\"username\":\"5\",\"nickname\":\"Fairfax\",\"" +
            "avatar\":\"http://img2.touxiang.cn/file/20160311/4a20942690f10bdf8fb71c98db274fd2.jpg\"," +
            "\"messageNum\":6,\"lastMessage\":\"Okay, i know.\",\"lastTime\":\"11:28\",\"isRead\":fal" +
            "se},{\"username\":\"6\",\"nickname\":\"Wafa\",\"avatar\":\"http://img4.imgtn.bdimg.com/it" +
            "/u=3925914968,3821812098&fm=214&gp=0.jpg\",\"messageNum\":9,\"lastMessage\":\"Happy birth" +
            "day to you!\",\"lastTime\":\"8:30\",\"isRead\":false},{\"username\":\"7\",\"nickname\":" +
            "\"Pable\",\"avatar\":\"http://www.qq745.com/uploads/allimg/150201/1-150201114300-50.jpg\"," +
            "\"messageNum\":0,\"lastMessage\":\"Has your sister graduated yet?\",\"lastTime\":\"8:29\"," +
            "\"isRead\":false},{\"username\":\"8\",\"nickname\":\"Oakes\",\"avatar\":\"http://www.ld12." +
            "com/upimg358/allimg/c141115/1416041b31B30-43BA.jpg\",\"messageNum\":50,\"lastMessage\":\"I'" +
            "d like to see the Titanic.\",\"lastTime\":\"00:10\",\"isRead\":false},{\"username\":\"9\"," +
            "\"nickname\":\"Mabel\",\"avatar\":\"http://img.qqtouxiang8.net/uploads/allimg/c140510/13cH6" +
            "2461JF-35508.jpg\",\"messageNum\":38,\"lastMessage\":\"See you at 8 tomorrow.\",\"lastTime" +
            "\":\"昨天\",\"isRead\":false},{\"username\":\"10\",\"nickname\":\"Alima\",\"avatar\":\"http" +
            "://www.itouxiang.net/uploads/allimg/20160616/gpzzlp54qic243089.jpg\",\"messageNum\":1,\"las" +
            "tMessage\":\"The cake is delicious, thank you.\",\"lastTime\":\"昨天\",\"isRead\":false},{" +
            "\"username\":\"11\",\"nickname\":\"Tacy\",\"avatar\":\"http://img0.imgtn.bdimg.com/it/u=41" +
            "09618721,269328371&fm=214&gp=0.jpg\",\"messageNum\":76,\"lastMessage\":\"Uh huh.\",\"lastTi" +
            "me\":\"前天\",\"isRead\":false},{\"username\":\"12\",\"nickname\":\"Ulema\",\"avatar\":\"htt" +
            "p://www.qq745.com/uploads/allimg/141128/1-14112Q14035-51.jpg\",\"messageNum\":99,\"lastMes" +
            "sage\":\"You look smart today and look young.\",\"lastTime\":\"星期日\",\"isRead\":false}," +
            "{\"username\":\"13\",\"nickname\":\"Queena\",\"avatar\":\"http://img1.imgtn.bdimg.com/it/u=" +
            "2315043706,2270288250&fm=214&gp=0.jpg\",\"messageNum\":109,\"lastMessage\":\"Let's go see t" +
            "he sunrise.\",\"lastTime\":\"星期六\",\"isRead\":false}]";

    private String username;
    private String nickname;
    private String avatar;
    private int messageNum;
    private String lastMessage;
    private String lastTime;
    private boolean isRead;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getMessageNum() {
        return messageNum;
    }

    public void setMessageNum(int messageNum) {
        this.messageNum = messageNum;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public void setRead(){
        setRead(true);
        setMessageNum(0);
    }
}
