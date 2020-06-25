package com.letsbiz.salesapp.model;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class UserList {
    private static UserList singleton = null;
    private Map<String, User> mUserMap = new HashMap<>();
    private boolean isUpdating = false;
    private UserListUpdateListener listener;

    public static UserList getInstance() {
        if(singleton == null) {
            singleton = new UserList();
        }
        return singleton;
    }

    public UserList() {}

    public void updateList() {
        if(!isUpdating) {
            isUpdating = true;

            UserRepository uRep = new UserRepository();
            uRep.fetchUserList(new Callback() {
                @Override
                public void onSuccess(Object object) {
                    QuerySnapshot snapshots = (QuerySnapshot) object;
                    if(snapshots != null && !snapshots.isEmpty()) {
                        for(DocumentSnapshot snapshot : snapshots.getDocuments()) {
                            mUserMap.put(snapshot.getId(), snapshot.toObject(User.class));
                        }
                    }

                    isUpdating = false;

                    if(listener != null) listener.onUpdate();
                }

                @Override
                public void onError(Object object) {
                    isUpdating = false;
                }
            });
        }
    }

    public String getUserName(String forId) {
        if(isUpdating)
            return "Updating..";

        if(mUserMap.containsKey(forId)) {
            User user = mUserMap.get(forId);
            if(user != null) {
                return user.getUserName();
            }
        }

        return "Not set";
    }

    public void setListener(UserListUpdateListener listener) {
        this.listener = listener;
    }

    public interface UserListUpdateListener {
        void onUpdate();
    }
}
