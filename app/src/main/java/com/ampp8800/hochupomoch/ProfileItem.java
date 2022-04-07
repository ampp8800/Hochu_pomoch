package com.ampp8800.hochupomoch;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.util.List;

public class ProfileItem {
    private final ImageView ivProfile;
    private final String nameProfile;
    private final List<FriendListItem> frendsList;


    public ProfileItem(@NonNull String name, @NonNull ImageView imageView, @NonNull List<FriendListItem> list) {
        this.nameProfile = name;
        this.ivProfile = imageView;
        this.frendsList = list;
    }

    public ImageView getIvProfile() {
        return ivProfile;
    }

    public String getNameProfile() {
        return nameProfile;
    }

    public List<FriendListItem> getFrendsList() {
        return frendsList;
    }


    public class FriendListItem {
        private final ImageView imageView;
        private final String name;

        public FriendListItem(@NonNull String name, @NonNull ImageView imageView) {
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