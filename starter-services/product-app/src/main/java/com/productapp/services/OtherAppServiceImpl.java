package com.productapp.services;

import com.productapp.exceptions.IntentionallyCausedException;
import org.springframework.stereotype.Service;

@Service
public class OtherAppServiceImpl implements OtherAppService {

    @Override
    public String receiveAndResponse() {
        return "Other app replies with message.";
    }

    @Override
    public String receiveAndResponseWithError(boolean b) {
        if (b) throw new IntentionallyCausedException("Other app replies with error.");
        return "void message";
    }
}
