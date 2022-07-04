package hello.itemservice.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemRepository itemRepository;

    //생성자가 1개만 있으면 Autowired 생략 가능
    //@RequiredArgsConstructor를 사용하면 final 필드가 있는 itemRepository를 자동 생성
//    public BasicItemController(ItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    //@PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);

        return "basic/item";
    }

    //ModelAttribute(네임)의 '네임'은 model.addAttribute 역할을 해준다. view에서 보여줄 attributeName이라고 볼 수 있다.
    //@PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item) {
        itemRepository.save(item);
        //model.addAttribute("item", item); //ModelAttribute("item")으로 인해 생략 가능

        return "basic/item";
    }

    //ModelAttribute(네임)의 '네임'생략시 클래스네임 앞을 소문자로 바꿔서 model.addAttribute(네임,value)가 된다.
    //ex)ModelAttribute Item item은 model.addAttribute("item", value)와 같다.
    //@PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item) {
        itemRepository.save(item);
        //model.addAttribute("item", item); //ModelAttribute("item")으로 인해 생략 가능

        return "basic/item";
    }

    //ModelAttribute 생략 가능(String등 단순타입인 경우 생략하면 @RequestParam)
    //@PostMapping("/add")
    public String addItemV4(Item item) {
        itemRepository.save(item);

        return "basic/item";
    }

    //PRG 패턴 적용(저장로직 중복 방지)
    @PostMapping("/add")
    public String addItemV5(Item item) {
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }

    //상품수정폼
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    //상품수정
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);

        //뷰템플릿 호출 대신 상품상세로 리다이렉트(302)
        //@PathVariable값 사용 가능
        return "redirect:/basic/items/{itemId}";
    }

    //테스트용 데이터 추가
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));

    }
}
