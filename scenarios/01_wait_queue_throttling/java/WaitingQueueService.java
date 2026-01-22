import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class WaitingQueueService {

    private final StringRedisTemplate redisTemplate;
    private static final String QUEUE_KEY = "ticket:waiting-queue";

    public WaitingQueueService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 1. 대기열 등록 (Enqueue)
     * - Key: UserID
     * - Score: 현재 시간 (먼저 온 사람이 낮은 점수를 가짐 -> FIFO)
     */
    public void addToQueue(String userId) {
        long score = System.currentTimeMillis();
        redisTemplate.opsForZSet().add(QUEUE_KEY, userId, score);
    }

    /**
     * 2. 내 대기 순번 조회
     * - 0부터 시작하므로 +1 처리하여 사용자에게 안내
     * - null인 경우 대기열에 없는 상태
     */
    public Long getMyRank(String userId) {
        Long rank = redisTemplate.opsForZSet().rank(QUEUE_KEY, userId);
        return (rank != null) ? rank + 1 : null;
    }

    /**
     * 3. 입장 처리 (Pop / Dequeue)
     * - 상위 N명을 가져온 뒤 대기열에서 제거
     * - Transaction 처리가 필요할 수 있음 (Lua Script 권장)
     */
    public Set<String> popUsers(long count) {
        // 0번부터 count-1번까지 조회 (상위 N명)
        Set<String> users = redisTemplate.opsForZSet().range(QUEUE_KEY, 0, count - 1);

        if (users != null && !users.isEmpty()) {
            // 조회된 유저들을 대기열에서 제거 (입장 시킴)
            redisTemplate.opsForZSet().remove(QUEUE_KEY, users.toArray());
        }
        return users;
    }
}