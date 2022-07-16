package co.com.poli.shoppingservice.persistence.repository;

import co.com.poli.shoppingservice.persistence.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Long> {

    List<Invoice> findByCustomerId(Long customertId);
    Invoice findByNumberInvoice(String numberInvoice);
}

