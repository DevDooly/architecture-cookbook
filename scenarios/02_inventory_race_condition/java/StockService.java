@Service
public class StockService {

    private final RedissonClient redissonClient;
    private final StockRepository stockRepository;

    // ... Constructor

    public void decreaseStock(Long productId) {
        // Lock Key 정의 (상품별로 락을 검)
        RLock lock = redissonClient.getLock("stock-lock:" + productId);

        try {
            // 10초 동안 락 획득 시도, 획득 후 1초간 점유
            boolean available = lock.tryLock(10, 1, TimeUnit.SECONDS);

            if (!available) {
                throw new RuntimeException("접속량이 많아 실패했습니다.");
            }

            // --- Critical Section (동시 진입 불가) ---
            Stock stock = stockRepository.findById(productId);
            if (stock.getQuantity() <= 0) {
                throw new RuntimeException("재고 소진");
            }
            stock.decrease(); // quantity = quantity - 1
            stockRepository.save(stock);
            // ----------------------------------------

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}