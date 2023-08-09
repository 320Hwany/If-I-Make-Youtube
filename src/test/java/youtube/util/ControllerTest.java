package youtube.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import youtube.domain.channel.persist.Channel;
import youtube.domain.member.persist.Member;
import youtube.mapper.channel.ChannelMapper;
import youtube.repository.channel.ChannelRepository;
import youtube.repository.member.MemberRepository;
import youtube.domain.member.vo.LoginId;
import youtube.domain.member.vo.Nickname;
import youtube.domain.member.vo.Password;
import youtube.global.constant.JwtConstant;
import youtube.mapper.member.dto.MemberLoginRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static youtube.util.TestConstant.*;

@AutoConfigureMockMvc
@AcceptanceTest
public class ControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected ChannelRepository channelRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    protected void signup() {
        Password password = Password.from(TEST_PASSWORD.value);

        Member member = Member.builder()
                .nickname(Nickname.from(TEST_NICKNAME.value))
                .loginId(LoginId.from(TEST_LOGIN_ID.value))
                .password(password.encode(passwordEncoder))
                .build();

        memberRepository.save(member);
        Channel channel = ChannelMapper.toEntity(member);
        channelRepository.save(channel);
    }

    protected String login() throws Exception {
        MemberLoginRequest dto = new MemberLoginRequest(
                LoginId.from(TEST_LOGIN_ID.value),
                Password.from(TEST_PASSWORD.value)
        );

        // expected
        MockHttpServletResponse response = mockMvc.perform(post("/api/login")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andReturn().getResponse();

        return response.getHeader(JwtConstant.ACCESS_TOKEN.value);
    }
}
