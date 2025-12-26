package com.example.demo.service.impl;

import com.example.demo.dto.*;
import com.example.demo.entity.User;
import com.example.demo.entity.UserDetail;
import com.example.demo.repository.TodoRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.service.mail.MailService;
import com.example.demo.service.password.PasswordGenerator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TodoRepository todoRepository;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponseDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public UserResponseDto getDetail(Long id) {
        User user = getUser(id);
        return toDto(user);
    }

    @Override
    public void delete(Long id) {
        User user = getUser(id);
        userRepository.delete(user);
    }

    @Override
    public UserResponseDto update(Long id, UpdateUserRequestDto dto) {
        User user = getUser(id);

        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }

        if (dto.getDetail() != null) {
            updateDetail(user, dto.getDetail());
        }

        return toDto(userRepository.save(user));
    }

    @Override
    public void updateStatusAllTodos(Long userId, Boolean completed) {
        todoRepository.updateStatusByUserId(userId, completed);
    }

    /* ================= PRIVATE ================= */

    private User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    private void updateDetail(User user, UserDetailDto dto) {
        UserDetail detail = user.getDetail();
        if (detail == null) {
            detail = new UserDetail();
            detail.setUser(user);
            user.setDetail(detail);
        }

        detail.setFullName(dto.getFullName());
        detail.setDateOfBirth(dto.getDateOfBirth());
        detail.setGender(dto.getGender());
        detail.setPhone(dto.getPhone());
        detail.setCity(dto.getCity());
        detail.setAddress(dto.getAddress());
        detail.setMetadata(dto.getMetadata());
    }

    private UserResponseDto toDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());

        if (user.getDetail() != null) {
            UserDetailDto detailDto = new UserDetailDto();
            detailDto.setFullName(user.getDetail().getFullName());
            detailDto.setDateOfBirth(user.getDetail().getDateOfBirth());
            detailDto.setGender(user.getDetail().getGender());
            detailDto.setPhone(user.getDetail().getPhone());
            detailDto.setCity(user.getDetail().getCity());
            detailDto.setAddress(user.getDetail().getAddress());
            detailDto.setMetadata(user.getDetail().getMetadata());

            dto.setDetail(detailDto);
        }

        return dto;
    }

    @Override
    public UserResponseDto create(UserCreateRequestDto dto) {

        String rawPassword = PasswordGenerator.generate();

        User user = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(rawPassword))
                .build();

        if (dto.getDetail() != null) {
            UserDetail detail = new UserDetail();
            detail.setUser(user);
            user.setDetail(detail);

            updateDetail(user, dto.getDetail());
        }

        User savedUser = userRepository.save(user);

        mailService.sendNewUserPassword(
                savedUser.getEmail(),
                savedUser.getUsername(),
                rawPassword
        );

        return toDto(savedUser);
    }

}
