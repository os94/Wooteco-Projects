package com.woowacourse.dsgram.service;

import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DirectMessageService {

    public int makePrivateRoom(long id1, long id2) {
        return id1 > id2 ? Objects.hash(id1, id2) : Objects.hash(id2, id1);
    }
}
