package xyz.firstmeet.lblog.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.firstmeet.lblog.services.otherlogin.GiteeService;

@SpringBootTest
public class GiteeServiceTest {
    private GiteeService giteeService;

    @Autowired
    public void setGiteeLogin(GiteeService giteeService) {
        this.giteeService = giteeService;
    }

    @Test
    public void testGiteeLogin() {
        System.out.println(giteeService.getLocalToken("4c7bdb48805ab9e6d7b90e561a4cc06b4de6c689282a5a67a4b5d548b1b043b9", "http://192.168.10.105:3000/login"));
    }
}
