package youtube.domain.video_reaction.vo;

public enum Reaction {

   LIKE(1),
   DISLIKE(-1),
   NO_REACTION(0);

   public final int count;

   Reaction(final int count) {
      this.count = count;
   }
}
