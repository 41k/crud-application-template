package root.controller;

import root.dto.CreateUserDto;
import root.dto.UpdateUserDto;
import root.model.User;
import root.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public String createUser(@RequestBody @Valid CreateUserDto dto) {
        return userService.createUser(dto);
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable String id,
                           @RequestBody @Valid UpdateUserDto dto) {
        userService.updateUser(id, dto);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) {
        return userService.getUser(id);
    }

    @GetMapping
    public Collection<User> getUsers() {
        return userService.getUsers();
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }
}
