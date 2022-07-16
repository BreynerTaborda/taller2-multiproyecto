package co.com.poli.shoppingservice.service;

import co.com.poli.shoppingservice.clientFeign.CustomerClient;
import co.com.poli.shoppingservice.clientFeign.ProductClient;
import co.com.poli.shoppingservice.model.Customer;
import co.com.poli.shoppingservice.model.Product;
import co.com.poli.shoppingservice.persistence.entity.Invoice;
import co.com.poli.shoppingservice.persistence.entity.InvoiceItem;
import co.com.poli.shoppingservice.persistence.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;

    @Override
    public void save(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    @Override
    public void delete(Invoice invoice) {
        invoiceRepository.delete(invoice);
    }

    @Override
    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    @Override
    public Invoice findById(Long id) {
        return invoiceRepository.findById(id).orElse(null);
    }

    @Override
    public Invoice findByNumberInvoice(String numberInvoice) {
        Invoice invoice = invoiceRepository.findByNumberInvoice(numberInvoice);
        ModelMapper modelMapper = new ModelMapper();
        Customer customer = modelMapper.map(customerClient.findByID(invoice.getCustomerId()).getData(),Customer.class);
        invoice.setCustomer(customer);
        List<InvoiceItem> items = invoice.getItems().stream()
                .map(invoiceItem -> {
                    Product product = modelMapper.map(productClient.findById(invoiceItem.getProductId()).getData(),Product.class);
                    invoiceItem.setProduct(product);
                    return invoiceItem;
                }).collect(Collectors.toList());
        invoice.setItems(items);
        return invoice;
    }
}