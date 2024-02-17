package youtube.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import youtube.domain.channel.persist.Channel;
import youtube.domain.channel.vo.ChannelDescription;
import youtube.domain.channel.vo.ChannelName;
import youtube.domain.member.persist.Member;
import youtube.domain.member.vo.Gender;
import youtube.domain.subscription.Subscription;
import youtube.domain.video.video_info.persist.VideoInfo;
import youtube.domain.video.video_watch_later.persist.VideoWatchLater;
import youtube.mapper.channel.ChannelMapper;
import youtube.mapper.member.dto.MemberSignupRequest;
import youtube.repository.channel.ChannelRepository;
import youtube.repository.comment.CommentRepository;
import youtube.repository.member.MemberRepository;
import youtube.domain.member.vo.LoginId;
import youtube.domain.member.vo.Nickname;
import youtube.domain.member.vo.Password;
import youtube.global.constant.StringConstant;
import youtube.mapper.member.dto.MemberLoginRequest;
import youtube.repository.subscription.SubscriptionRepository;
import youtube.repository.video.video_info.VideoInfoRepository;
import youtube.repository.video.video_watch_later.VideoWatchLaterRepository;

import java.time.LocalDate;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static youtube.util.TestConstant.*;

@AutoConfigureMockMvc
@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
public abstract class ControllerTest {

    @Autowired
    protected DatabaseCleaner databaseCleaner;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected ChannelRepository channelRepository;

    @Autowired
    protected SubscriptionRepository subscriptionRepository;

    @Autowired
    protected VideoInfoRepository videoInfoRepository;

    @Autowired
    protected VideoWatchLaterRepository videoWatchLaterRepository;

    @Autowired
    protected CommentRepository commentRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUpDatabase() {
        databaseCleaner.cleanUp();
    }

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint())
                        .and()
                        .uris()
                        .withScheme("http")
                        .withHost("localhost")
                        .withPort(8080))
                .build();
    }

    protected Member saveMember() {
        Password password = Password.from(TEST_PASSWORD.value);

        Member member = Member.builder()
                .nickname(Nickname.from(TEST_NICKNAME.value))
                .loginId(LoginId.from(TEST_LOGIN_ID.value))
                .password(password.encode(passwordEncoder))
                .build();

        memberRepository.save(member);
        return member;
    }

    protected Channel saveChannel() {
        Channel channel = Channel.builder()
                .channelName(ChannelName.from("채널 이름"))
                .channelDescription(ChannelDescription.from("채널 설명"))
                .videosCount(30)
                .subscribersCount(10000)
                .build();

        channelRepository.save(channel);
        return channel;
    }

    protected VideoInfo saveVideoInfo() {
        VideoInfo videoInfo = VideoInfo.builder()
                .videoTitle("동영상 제목")
                .videoDescription("동영상 설명")
                .views(10000)
                .likesCount(500)
                .dislikesCount(10)
                .build();

        videoInfoRepository.save(videoInfo);
        return videoInfo;
    }

    protected Subscription saveSubscription() {
        Member member = saveMember();

        Channel channel = ChannelMapper.toEntity(member);
        channelRepository.save(channel);

        Subscription subscription = Subscription.builder()
                .memberId(member.getId())
                .channelId(channel.getId())
                .build();

        subscriptionRepository.save(subscription);

        return subscription;
    }

    protected VideoWatchLater saveVideoWatchLater() {
        Member member = saveMember();
        VideoInfo videoInfo = saveVideoInfo();

        VideoWatchLater videoWatchLater = VideoWatchLater.builder()
                .memberId(member.getId())
                .videoInfoId(videoInfo.getId())
                .build();

        videoWatchLaterRepository.save(videoWatchLater);
        return videoWatchLater;
    }

    protected void signup() throws Exception {
        // given
        MemberSignupRequest dto = MemberSignupRequest.builder()
                .nickname(Nickname.from(TEST_NICKNAME.value))
                .loginId(LoginId.from(TEST_LOGIN_ID.value))
                .password(Password.from(TEST_PASSWORD.value))
                .gender(Gender.MALE)
                .birthDate(LocalDate.now())
                .build();

        // expected
        mockMvc.perform(post("/api/v1/signup")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    protected String login() throws Exception {
        MemberLoginRequest dto = new MemberLoginRequest(
                LoginId.from(TEST_LOGIN_ID.value),
                Password.from(TEST_PASSWORD.value)
        );

        // expected
        MockHttpServletResponse response = mockMvc.perform(post("/api/v1/login")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andReturn().getResponse();

        return response.getHeader(StringConstant.ACCESS_TOKEN.value);
    }
}
