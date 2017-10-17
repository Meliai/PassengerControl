package com.rudainc.passengercontrol.transport_layout;

public class Transport {
    private String title;
    private int image;

    Transport(String title, int image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    int getImage() {
        return image;
    }
}
