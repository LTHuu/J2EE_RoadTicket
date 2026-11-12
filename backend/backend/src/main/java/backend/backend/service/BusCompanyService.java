package backend.backend.service;

import backend.backend.entity.BusCompany;
import backend.backend.repository.BusCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusCompanyService {

    @Autowired
    private BusCompanyRepository busCompanyRepository;

    public List<BusCompany> getAllBusCompanies() {
        return busCompanyRepository.findAll();
    }

    public Optional<BusCompany> getBusCompanyById(Long id) {
        return busCompanyRepository.findById(id);
    }

    public BusCompany createBusCompany(BusCompany busCompany) {
        return busCompanyRepository.save(busCompany);
    }

    public BusCompany updateBusCompany(Long id, BusCompany companyDetails) {
        BusCompany company = busCompanyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhà xe với ID: " + id));
        
        company.setName(companyDetails.getName());
        company.setDescription(companyDetails.getDescription());
        company.setCode(companyDetails.getCode());
        company.setHotline(companyDetails.getHotline());
        company.setEmail(companyDetails.getEmail());
        company.setAddress(companyDetails.getAddress());
        company.setLogoUrl(companyDetails.getLogoUrl());
        company.setStatus(companyDetails.getStatus());

        return busCompanyRepository.save(company);
    }

    public void deleteBusCompany(Long id) {
        busCompanyRepository.deleteById(id);
    }
}