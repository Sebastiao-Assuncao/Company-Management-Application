package woo;

import java.io.Serializable;

public abstract class DeliveryMethod implements Serializable {

    private Notification _notification;

    public DeliveryMethod(Notification notification) {
        _notification = notification;
    }
}
