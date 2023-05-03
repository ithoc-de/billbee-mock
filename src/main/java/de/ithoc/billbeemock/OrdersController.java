package de.ithoc.billbeemock;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.ithoc.billbeemock.model.customers.Customers;
import de.ithoc.billbeemock.model.orders.Datum;
import de.ithoc.billbeemock.model.orders.Datum__3;
import de.ithoc.billbeemock.model.orders.Orders;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/orders")
public class OrdersController {

    @GetMapping
    public @ResponseBody Orders get(@RequestParam String minOrderDate, @RequestParam Long page) throws IOException {

        Orders orders = load(page);
        List<Datum__3> collect = orders.getData().stream()
                .filter(data -> data.getCreatedAt().compareTo(minOrderDate) > 0)
                .toList();
        orders.setData(collect);

        return orders;
    }


    private Orders load(Long page) throws IOException {
        InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream("schema/orders/orders-" + page + ".json");
        assert inputStream != null;
        String json = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        inputStream.close();

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, Orders.class);
    }

}
