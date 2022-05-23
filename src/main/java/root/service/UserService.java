package root.service;

import root.dto.CreateUserDto;
import root.dto.UpdateUserDto;
import root.exception.ResourceNotFoundException;
import root.model.User;
import root.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@RequiredArgsConstructor
public class UserService {

    private final IdGenerator idGenerator;
    private final UserRepository userRepository;

    public String createUser(CreateUserDto dto) {
        var id = idGenerator.generate();
        var name = dto.getName();
        userRepository.save(User.builder().id(id).name(name).build());
        return id;
    }

    @Transactional
    public void updateUser(String id, UpdateUserDto dto) {
        userRepository.findById(id)
                .map(user -> userRepository.save(user.toBuilder().name(dto.getName()).build()))
                .orElseThrow(() -> userNotFoundException(id));
    }

    public Collection<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(String id) {
        return userRepository.findById(id).orElseThrow(() -> userNotFoundException(id));
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    private ResourceNotFoundException userNotFoundException(String id) {
        return new ResourceNotFoundException(String.format("User with id=%s is not found", id));
    }
}
