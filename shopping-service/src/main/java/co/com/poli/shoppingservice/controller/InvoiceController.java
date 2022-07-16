package co.com.poli.shoppingservice.controller;

import co.com.poli.shoppingservice.helpers.Response;
import co.com.poli.shoppingservice.helpers.ResponseBuild;
import co.com.poli.shoppingservice.persistence.entity.Invoice;
import co.com.poli.shoppingservice.service.InvoiceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/shoppings")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final ResponseBuild builder;

    @PostMapping()
    public Response save(@Valid @RequestBody Invoice invoice, BindingResult result) {
        if (result.hasErrors()) {
            return builder.failed(formatMessage(result));
        }
        invoiceService.save(invoice);
        return builder.success(invoice);
    }

    @DeleteMapping("/{numberId}")
    public Response delete(@PathVariable("numberId") String numberId) {
        Invoice invoice = invoiceService.findByNumberInvoice(numberId);
        if (invoice == null) {
            return builder.failed(invoice);
        }
        return builder.success(invoice);
    }

    @GetMapping("/{numberInvoice}")
    public Response getByNumberInvoice(@PathVariable("numberInvoice") String numberInvoice) {
        Invoice invoice = invoiceService.findByNumberInvoice(numberInvoice);
        if (invoice == null) {
            return builder.success();
        }
        return builder.success(invoice);
    }

    private List<Map<String, String>> formatMessage(BindingResult result) {
        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(error -> {
                    Map<String, String> err = new HashMap<>();
                    err.put(error.getField(), error.getDefaultMessage());
                    return err;
                }).collect(Collectors.toList());
        return errors;
    }
}
