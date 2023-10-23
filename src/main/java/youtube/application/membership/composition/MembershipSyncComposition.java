package youtube.application.membership.composition;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import youtube.application.membership.MembershipReader;
import youtube.application.membership.MembershipUpdater;
import youtube.domain.membership.persist.Membership;
import youtube.global.exception.BadRequestException;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static youtube.global.constant.AnnotationMessageConstant.SIX_AM;
import static youtube.global.constant.ExceptionMessageConstant.MEMBERSHIP_SCHEDULER_BAD_REQUEST;

@Service
public class MembershipSyncComposition {

    private final MembershipReader membershipReader;
    private final MembershipUpdater membershipUpdater;

    public MembershipSyncComposition(final MembershipReader membershipReader,
                                     final MembershipUpdater membershipUpdater) {
        this.membershipReader = membershipReader;
        this.membershipUpdater = membershipUpdater;
    }

    // 오전 6시에 가입 기간에 따른 멤버십 단계 업데이트
    @Async
    @Scheduled(cron = SIX_AM)
    public void syncMembershipLevel() {
        List<Membership> memberships = membershipReader.findAll();

        List<CompletableFuture<Void>> futures = memberships.stream()
                .map(
                        entity -> CompletableFuture.runAsync(
                                () -> membershipUpdater.update(entity.getId())
                        )
                )
                .toList();

        CompletableFuture<Void> future = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        validateResult(future);
    }

    private void validateResult(final CompletableFuture<Void> result) {
        try {
            result.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new BadRequestException(MEMBERSHIP_SCHEDULER_BAD_REQUEST.message);
        }
    }
}
