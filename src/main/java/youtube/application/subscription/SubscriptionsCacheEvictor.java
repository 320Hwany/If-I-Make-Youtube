package youtube.application.subscription;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import static youtube.global.constant.AnnotationMessageConstant.SUBSCRIPTION_CHANNELS_CACHE;


@Slf4j
@Service
public class SubscriptionsCacheEvictor {


    // 스프링 이벤트로 호출됨 - SubscriptionsEventListener
    @CacheEvict(value = SUBSCRIPTION_CHANNELS_CACHE, key = "#memberId")
    public void clearCacheByMemberId(long memberId) {
        log.info("Subscription Channel Cache cleared for memberId: " + memberId);
    }
}
