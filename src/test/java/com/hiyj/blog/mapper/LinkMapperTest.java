package com.hiyj.blog.mapper;

import com.hiyj.blog.object.FriendLink;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LinkMapperTest {
    private LinkMapper linkMapper;

    @Autowired
    public void setLinkMapper(LinkMapper linkMapper) {
        this.linkMapper = linkMapper;
    }

    @Test
    public void testGetFriendLinks() {
        System.out.println(linkMapper.getFriendLinks(FriendLink.Status.ALL).size());
    }
}
