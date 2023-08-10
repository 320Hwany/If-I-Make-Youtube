package youtube.domain.channel.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public final class ChannelDescription {

    @Lob
    @Column(nullable = false)
    private String channelDescription;

    private ChannelDescription(final String channelDescription) {
        this.channelDescription = channelDescription;
    }

    public static ChannelDescription from(final String channelDescription) {
        return new ChannelDescription(channelDescription);
    }

    public void update(final ChannelDescription channelDescriptionUpdate) {
        this.channelDescription = channelDescriptionUpdate.channelDescription;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChannelDescription that = (ChannelDescription) o;
        return Objects.equals(channelDescription, that.channelDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(channelDescription);
    }
}
