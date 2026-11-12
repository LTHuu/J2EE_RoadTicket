package backend.backend.service;

import backend.backend.dto.buscompany.BusCompanyCreateDTO;
import backend.backend.dto.buscompany.BusCompanyDTO;
import backend.backend.dto.buscompany.BusCompanyUpdateDTO;
import backend.backend.entity.BusCompany;
import backend.backend.mapper.BusCompanyMapper;
import backend.backend.repository.BusCompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BusCompanyService {

    private final BusCompanyRepository busCompanyRepository;
    private final BusCompanyMapper busCompanyMapper;

    @Transactional(readOnly = true)
    public List<BusCompanyDTO> getAllBusCompanies() {
        return busCompanyRepository.findAll()
                .stream()
                .map(busCompanyMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public BusCompanyDTO getBusCompanyById(Long id) {
        return busCompanyMapper.toDTO(findBusCompany(id));
    }

    public BusCompanyDTO createBusCompany(BusCompanyCreateDTO dto) {
        BusCompany busCompany = busCompanyMapper.toEntity(dto);
        return busCompanyMapper.toDTO(busCompanyRepository.save(busCompany));
    }

    public BusCompanyDTO updateBusCompany(Long id, BusCompanyUpdateDTO dto) {
        BusCompany busCompany = findBusCompany(id);
        busCompanyMapper.updateEntity(dto, busCompany);
        return busCompanyMapper.toDTO(busCompanyRepository.save(busCompany));
    }

    public void deleteBusCompany(Long id) {
        busCompanyRepository.delete(findBusCompany(id));
    }

    private BusCompany findBusCompany(Long id) {
        return busCompanyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Không tìm thấy nhà xe với ID: " + id));
    }
}