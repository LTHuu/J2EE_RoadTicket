package backend.backend.controller;

import backend.backend.entity.BusCompany;
import backend.backend.service.BusCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bus-companies")
@CrossOrigin(origins = "*")
public class BusCompanyController {

    @Autowired
    private BusCompanyService busCompanyService;

    @GetMapping
    public List<BusCompany> getAllBusCompanies() {

        return busCompanyService.getAllBusCompanies();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusCompany> getBusCompanyById(@PathVariable Long id) {
        return busCompanyService.getBusCompanyById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public BusCompany createBusCompany(@RequestBody BusCompany busCompany) {
        return busCompanyService.createBusCompany(busCompany);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BusCompany> updateBusCompany(@PathVariable Long id, @RequestBody BusCompany companyDetails) {
        try {
            BusCompany updated = busCompanyService.updateBusCompany(id, companyDetails);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBusCompany(@PathVariable Long id) {
        busCompanyService.deleteBusCompany(id);
        return ResponseEntity.noContent().build();
    }
}