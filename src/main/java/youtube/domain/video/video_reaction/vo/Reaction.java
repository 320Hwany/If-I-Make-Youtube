package youtube.domain.video.video_reaction.vo;


public enum Reaction {

   LIKE(1),
   DISLIKE(-1),
   NO_REACTION(0);

   public final int value;

   Reaction(final int value) {
      this.value = value;
   }
}
