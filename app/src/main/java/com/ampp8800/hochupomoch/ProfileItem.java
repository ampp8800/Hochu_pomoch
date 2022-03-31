package com.ampp8800.hochupomoch;

import android.widget.ImageView;

import java.util.List;

public class ProfileItem {
    private ImageView ivProfile;
    private String nameProfile;
    private List<FriendListItem> frendsList;

    public ImageView getIvProfile() {
        return ivProfile;
    }

    public String getNameProfile() {
        return nameProfile;
    }

    public List<FriendListItem> getFrendsList() {
        return frendsList;
    }

    public ProfileItem(String name, ImageView imageView, List<FriendListItem> list) {
        this.nameProfile = name;
        this.ivProfile = imageView;
        this.frendsList = list;
    }


    public class FriendListItem {
        private ImageView imageView;
        private String name;

        public FriendListItem(String name, ImageView imageView) {
            this.name = name;
            this.imageView = imageView;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public String getName() {
            return name;
        }
    }
}