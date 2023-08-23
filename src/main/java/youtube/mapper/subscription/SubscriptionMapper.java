package youtube.mapper.subscription;

import youtube.domain.subscription.Subscription;
import youtube.mapper.subscription.dto.SubscriptionChannelDto;
import youtube.mapper.subscription.dto.SubscriptionChannelsCache;
import youtube.mapper.subscription.dto.SubscriptionResult;

import java.util.List;
import java.util.stream.Collectors;

public class SubscriptionMapper {

    private SubscriptionMapper() {
    }

    public static Subscription toEntity(final long memberId, final long channelId) {
        return Subscription.builder()
                .memberId(memberId)
                .channelId(channelId)
                .isNotification(false)
                .build();
    }

    public static List<SubscriptionChannelsCache> toCaches(final List<SubscriptionChannelDto> dtos) {
        return dtos
                .stream()
                .map(SubscriptionMapper::toCache)
                .collect(Collectors.toList());
    }

    private static SubscriptionChannelsCache toCache(final SubscriptionChannelDto dto) {
        return new SubscriptionChannelsCache(dto.channelId(), dto.channelName());
    }

    public static SubscriptionResult toResult(final List<SubscriptionChannelsCache> caches,
                                              final int channelCount) {
        return new SubscriptionResult(caches, channelCount);
    }
}
