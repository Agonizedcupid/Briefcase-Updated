package com.aariyan.briefcase.Interface;


import com.aariyan.briefcase.Model.MessageModel;

import java.util.List;

public interface MessageListInterface {

    void messageList(List<MessageModel> list);
    void error(String error);
}
