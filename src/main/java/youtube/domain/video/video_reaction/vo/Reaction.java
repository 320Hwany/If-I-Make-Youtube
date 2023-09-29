package youtube.domain.video.video_reaction.vo;

import youtube.global.constant.NumberConstant;

import static youtube.global.constant.NumberConstant.*;

public enum Reaction {

   LIKE(1),
   DISLIKE(-1),
   NO_REACTION(0);

   public final int count;

   Reaction(final int count) {
      this.count = count;
   }

   public static int updateLikesCount(final Reaction originalReaction, final Reaction updateReaction) {
      int result = ZERO.value;

      if (LIKE.equals(originalReaction)) {
         result += MINUS_ONE.value;
      }

      if (LIKE.equals(updateReaction)) {
         result += ONE.value;
      }

      return result;
   }

   public static int updateDislikesCount(final Reaction originalReaction, final Reaction updateReaction) {
      int result = ZERO.value;

      if (DISLIKE.equals(originalReaction)) {
         result += MINUS_ONE.value;
      }

      if (DISLIKE.equals(updateReaction)) {
         result += ONE.value;
      }

      return result;
   }
}
