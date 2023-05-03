package de.ithoc.billbeemock;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.ithoc.billbeemock.model.customers.Customers;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@RestController
@CrossOrigin
@RequestMapping("/customers")
public class CustomersController {

    @GetMapping
    public @ResponseBody Customers get() throws IOException {

        return loadCustomers();
    }


    private Customers loadCustomers() throws IOException {
        InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream("schema/customers/customers.json");
        assert inputStream != null;
        String json = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        inputStream.close();

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, Customers.class);
    }

}
