package backend.backend.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import backend.backend.dto.UBCom.UBComAddDTO;
import backend.backend.dto.UBCom.UBComDTO;
import backend.backend.dto.UBCom.UBComSearchDTO;
import backend.backend.dto.UBCom.UBComUpdateDTO;
import backend.backend.entity.BusCompany;
import backend.backend.entity.User;
import backend.backend.entity.User_BusCom;
import backend.backend.mapper.UBComMapper;
import backend.backend.repository.BusCompanyRepository;
import backend.backend.repository.UBComRepository;
import backend.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UBComService {

    private final UBComRepository ubComRepository;
    private final UserRepository userRepository;
    private final BusCompanyRepository busCompanyRepository;
    private final UBComMapper ubComMapper;

    @Transactional(readOnly = true)
    public List<UBComDTO> getAll() {
        return ubComRepository.findAll()
                .stream()
                .map(ubComMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public UBComDTO getById(Long id) {
        return ubComMapper.toDTO(findById(id));
    }

    @Transactional(readOnly = true)
    public List<UBComDTO> search(UBComSearchDTO dto) {
        String searchText = dto.getSearchText();
        if (searchText != null && searchText.isBlank()) {
            searchText = null;
        }

        return ubComRepository.search(
                        searchText,
                        dto.getUserId(),
                        dto.getBusCompanyId(),
                        dto.getPosition(),
                        dto.getActive())
                .stream()
                .map(ubComMapper::toDTO)
                .toList();
    }

    public UBComDTO add(UBComAddDTO dto) {
        User user = findUser(dto.getUserId());
        BusCompany busCompany = findBusCompany(dto.getBusCompanyId());
        ensureUnique(user.getId(), busCompany.getId(), null);

        User_BusCom entity = ubComMapper.toEntity(dto);
        entity.setUser(user);
        entity.setBusCompany(busCompany);
        if (entity.getActive() == null) {
            entity.setActive(true);
        }

        return ubComMapper.toDTO(ubComRepository.save(entity));
    }

    public UBComDTO update(Long id, UBComUpdateDTO dto) {
        User_BusCom entity = findById(id);
        User user = findUser(dto.getUserId());
        BusCompany busCompany = findBusCompany(dto.getBusCompanyId());
        ensureUnique(user.getId(), busCompany.getId(), id);

        entity.setUser(user);
        entity.setBusCompany(busCompany);
        entity.setPosition(dto.getPosition());
        entity.setActive(dto.getActive());

        return ubComMapper.toDTO(ubComRepository.save(entity));
    }

    public void delete(Long id) {
        ubComRepository.delete(findById(id));
    }

    private User_BusCom findById(Long id) {
        return ubComRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Không tìm thấy quan hệ người dùng - công ty xe"));
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Không tìm thấy người dùng"));
    }

    private BusCompany findBusCompany(Long busCompanyId) {
        return busCompanyRepository.findById(busCompanyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Không tìm thấy công ty xe"));
    }

    private void ensureUnique(Long userId, Long busCompanyId, Long currentId) {
        boolean exists = currentId == null
                ? ubComRepository.existsByUser_IdAndBusCompany_Id(userId, busCompanyId)
                : ubComRepository.existsByUser_IdAndBusCompany_IdAndIdNot(userId, busCompanyId, currentId);
        if (exists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Người dùng đã thuộc công ty xe này");
        }
    }
}
