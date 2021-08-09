package xyz.firstmeet.lblog.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.firstmeet.lblog.object.FriendLink;

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
