package backend.backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.backend.dto.UBCom.UBComAddDTO;
import backend.backend.dto.UBCom.UBComDTO;
import backend.backend.dto.UBCom.UBComSearchDTO;
import backend.backend.dto.UBCom.UBComUpdateDTO;
import backend.backend.service.UBComService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ubcoms")
@RequiredArgsConstructor
public class UBComController {

    private final UBComService ubComService;

    @GetMapping
    public ResponseEntity<List<UBComDTO>> getAll() {
        return ResponseEntity.ok(ubComService.getAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<UBComDTO>> search(@ModelAttribute UBComSearchDTO search) {
        return ResponseEntity.ok(ubComService.search(search));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UBComDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ubComService.getById(id));
    }

    @PostMapping
    public ResponseEntity<UBComDTO> add(@RequestBody @Valid UBComAddDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ubComService.add(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UBComDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid UBComUpdateDTO dto) {
        return ResponseEntity.ok(ubComService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        ubComService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Xóa quan hệ người dùng - công ty xe thành công"));
    }
}
