package youtube.application.subscription.query;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.mapper.subscription.SubscriptionMapper;
import youtube.mapper.subscription.dto.SubscriptionChannelDto;
import youtube.mapper.subscription.dto.SubscriptionChannelsCache;
import youtube.repository.subscription.SubscriptionRepository;

import java.util.List;

import static youtube.global.constant.CacheConstant.SUBSCRIPTION_CHANNELS_CACHE;

@Service
public class QuerySubscriptionsByMemberId {

    private final SubscriptionRepository subscriptionRepository;

    public QuerySubscriptionsByMemberId(final SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = SUBSCRIPTION_CHANNELS_CACHE, key = "#memberId")
    public List<SubscriptionChannelsCache> query(final long memberId) {
        List<SubscriptionChannelDto> dtos = subscriptionRepository.findSubscriptionChannelsByMemberId(memberId);
        return SubscriptionMapper.toCaches(dtos);
    }
}
