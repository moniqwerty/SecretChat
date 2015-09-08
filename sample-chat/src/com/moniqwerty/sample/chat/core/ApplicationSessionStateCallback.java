package com.moniqwerty.sample.chat.core;

/**
 * Created by moniqwerty on 4/29/15.
 */
public interface ApplicationSessionStateCallback {
    void onStartSessionRecreation();
    void onFinishSessionRecreation(boolean success);
}
