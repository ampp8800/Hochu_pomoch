package com.ampp8800.hochupomoch.data;

public class NetworkNewsRepository {
    private static NetworkNewsRepository networkNewsRepository;

    private NetworkNewsRepository() {
    }

    public static NetworkNewsRepository newInstance() {
        if (networkNewsRepository == null) {
            networkNewsRepository = new NetworkNewsRepository();
        }
        return networkNewsRepository;
    }

}
