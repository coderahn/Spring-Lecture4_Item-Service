package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {
    //스프링 컨테이너 안에서 쓰면 싱글톤이라 static을 안 써도 되긴 함.
    //따로 ItemRepository를 new로 사용하면 static 필수
    //멀티스레드 환경에서 여러개가 동시에 store 접근시 HashMap이 아닌 ConcurrentHashMap 사용

    //HashMap은 멀티쓰레드 환경에서 사용하면 안됨.
    private static final Map<Long, Item> store = new HashMap<>();

    //멀티스레드 환경에서 long이 아닌 AtomicLong 사용
    private static long sequance = 0L;

    //아이템 저장
    public Item save(Item item) {
        item.setId(sequance++);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        //store.values()를 그대로 반환해도되지만, ArrayList를 감싸서 add작업을 해도 store에 영향 없도록 하기 위해 감쌈
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }
}
