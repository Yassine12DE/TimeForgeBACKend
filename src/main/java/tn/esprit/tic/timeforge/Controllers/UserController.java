package tn.esprit.tic.timeforge.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tn.esprit.tic.timeforge.Entity.User;
import tn.esprit.tic.timeforge.Repository.UserRepository;
import tn.esprit.tic.timeforge.Service.security.JwtUtil;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @GetMapping("/user/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getallusers")
    public List<User> allusers() {
        return userRepository.findAll();
    }

    @GetMapping("/api/user")
    public Long retreiveIdfromtoken(@RequestHeader("Authorization") String authheader) {
        String token = authheader.substring(7);
        String username= jwtUtil.extractUsername(token);
        Optional<User> user =userRepository.findByUsername(username);
        return user.get().getIdUser();
        }

}
