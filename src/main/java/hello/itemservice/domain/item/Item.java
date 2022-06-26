package hello.itemservice.domain.item;

import lombok.Data;

//@Data는 위험. @Getter, @Setter만 쓰는게 좋음. 핵심도메인 모델에서는 가능하면 안 사용
//data 왔다갔다용 dto에서는 어느정도 써도 됨
@Data
public class Item {

    private Long id;
    private String itemName;
    private Integer price; //price가 없을 수도 있으니 Integer
    private Integer quantity; //수량이 없는 경우 있을 수도 있으니 Integer

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
