package com.ampp8800.hochupomoch;


import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;


public class ProfileRepository {
    private static ProfileRepository profileRepository;
    private static String imageViewURL;
    private static String nameProfile;
    private static String dateOfBirth;
    private static String fieldOfActivity;
    private static ArrayList<FriendListItem> frendsList = new ArrayList<FriendListItem>();


    private ProfileRepository() {
    }

    public static String getDateOfBirth() {
        return dateOfBirth;
    }

    public static String getFieldOfActivity() {
        return fieldOfActivity;
    }

    @NonNull
    public static ProfileRepository getInstance() {
        if (profileRepository == null) {
            profileRepository = new ProfileRepository();
            imageViewURL = "https://sun9-63.userapi.com/impf/c625318/v625318902/28050/-l1-yQ4qIQk.jpg?size=1365x2048&quality=96&sign=4a8023e5f2a744ec6004f35725341e88&type=album.png";
            nameProfile = "Сычёв Антон";
            dateOfBirth = "01 мая 1999";
            fieldOfActivity = "Эксперт, все области";
            newFrendsList();
        }
        return profileRepository;
    }

    public static String getImageViewURL() {
        return imageViewURL;
    }

    public String getNameProfile() {
        return nameProfile;
    }

    public List<FriendListItem> getFrendsList() {
        return frendsList;
    }

    public static void newFrendsList() {
        frendsList.add(new FriendListItem("Федос Новиков", "https://sun1-83.userapi.com/s/v1/if2/cMc3ol_SHekwMzkH25TGXKHl7lNN-Z0Zo1yV2mX2bbkBEBQc13XWSC042cf-fXxuqzqPdk4MhQDRnLFhj_UEi1dF.jpg?size=100x100&quality=96&crop=100,72,576,576&ava=1.png"));
        frendsList.add(new FriendListItem("Михаил Мк", "https://sun1-55.userapi.com/s/v1/if1/pmcCIlYiUTIu_o-FqEsjr3GQDZMtV2K4wRAtBDWvo2lk4ArnafWaqId97j2dcWBc2v0KSJjQ.jpg?size=100x100&quality=96&crop=30,4,815,815&ava=1.png"));
        frendsList.add(new FriendListItem("Валентин Лесной", "https://sun1-87.userapi.com/s/v1/ig2/6wlIHzNnoi2imvi3DWW3Ai3C59s9aWJlUyyR3Y3UHzF3E_Q2X6Fz5SQ3UCSCq_tNBne_jcxTTbTKTKwMWLoMHmAX.jpg?size=100x100&quality=95&crop=242,3,853,853&ava=1.png"));
        frendsList.add(new FriendListItem("Британский Кот", "https://www.sunny-cat.ru/datas/bigthumbs/1-vislouhij_shotlandec.jpg"));
    }


    public static class FriendListItem {
        private String imageViewURL;
        private String name;

        private FriendListItem(String name, String imageViewURL) {
            this.name = name;
            this.imageViewURL = imageViewURL;
        }

        public String getImageViewURL() {
            return imageViewURL;
        }

        public String getName() {
            return name;
        }

    }
}
