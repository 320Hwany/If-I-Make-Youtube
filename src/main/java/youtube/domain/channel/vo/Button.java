package youtube.domain.channel.vo;

public enum Button {

    NORMAL(100000),
    SILVER(1000000),
    GOLD(10000000),
    DIAMOND(100000000),
    RED_DIAMOND(Integer.MAX_VALUE);

    private final int subscribersCount;

    Button(final int subscribersCount) {
        this.subscribersCount = subscribersCount;
    }

    public static Button from(final int subscribersCount) {
        for (Button button : Button.values()) {
            if (button.subscribersCount > subscribersCount) {
                return button;
            }
        }
        return Button.RED_DIAMOND;
    }
}
