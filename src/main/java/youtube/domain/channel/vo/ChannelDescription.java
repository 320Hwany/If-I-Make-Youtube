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
    @Column(name = "channelDescription", nullable = false)
    private String value;

    private ChannelDescription(final String value) {
        this.value = value;
    }

    public static ChannelDescription from(final String value) {
        return new ChannelDescription(value);
    }

    public void update(final ChannelDescription channelDescription) {
        this.value = channelDescription.value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChannelDescription that = (ChannelDescription) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
