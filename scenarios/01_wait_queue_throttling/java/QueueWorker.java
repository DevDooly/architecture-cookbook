import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Set;

@Component
public class QueueWorker {

    private final WaitingQueueService queueService;
    // gRPC 클라이언트 등이 이곳에 주입됨

    public QueueWorker(WaitingQueueService queueService) {
        this.queueService = queueService;
    }

    /**
     * 1초마다 실행 (Leaky Bucket 구현)
     * - 백엔드가 처리 가능한 양(예: 100명)만큼만 입장 시킴
     */
    @Scheduled(fixedDelay = 1000)
    public void processQueue() {
        int processingLimit = 100; // 이번 턴에 처리할 수 있는 양

        Set<String> enteredUsers = queueService.popUsers(processingLimit);

        if (enteredUsers != null && !enteredUsers.isEmpty()) {
            for (String userId : enteredUsers) {
                // TODO: 실제 비즈니스 로직 수행 (gRPC 호출 등)
                // grpcClient.processBooking(userId);
                System.out.println("User entered: " + userId);
            }
        }
    }
}