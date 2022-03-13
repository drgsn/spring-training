package com.training.controller;

import com.training.auth.jwt.JwtTokenService;
import com.training.auth.jwt.JwtUserDetailsService;
import com.training.auth.jwt.UserDTO;
import com.training.model.Task;
import com.training.model.User;
import com.training.repository.TaskRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Tag(name = "tasks", description = "the tasks API")
@Controller
public class CrudController {

    private static final Logger log = LoggerFactory.getLogger(CrudController.class);

    @Autowired
    private TaskRepository repository;

    @Autowired
    private JwtUserDetailsService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenUtil;

    @PostMapping(value = "/login")
    @ResponseBody
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserDTO authenticationRequest) throws Exception {

        final Authentication auth = authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);
        return ResponseEntity.ok(jwtTokenUtil.generateToken(auth));
    }

    @Operation(summary = "user registration", description = "", tags = {"user"})
    @PostMapping("/register")
    @ResponseBody
    public void registration(@RequestBody UserDTO user) throws Exception {
        log.info("registered user {}", userService.save(user));
    }

    @Operation(summary = "get all tasks for user", description = "", tags = {"tasks"})
    @GetMapping("/task/{id}")
    public List<Task> getUserTasks(@PathVariable @NotNull Long id) {
        return repository.getUserTasks(id);
    }

    @Operation(summary = "get all tasks", description = "", tags = {"tasks"})
    @GetMapping("/tasks/all")
    public @ResponseBody
    Iterable<Task> getAllTasks() {
        return repository.findAll();
    }

    @Operation(summary = "insert a new task for user", description = "", tags = {"tasks"})
    @PostMapping("/task")
    public String addTasks(@ModelAttribute @Valid Task task, Errors errors) {
        if (null != errors && errors.getErrorCount() > 0) {
            return "add_task";
        }

        if (task.getDueDate() == null) {
            task.setDueDate(LocalDate.now().plusDays(1));
        }
        if (task.getUserId() == null) {
            task.setUserId(1l);
        }
        repository.save(task);
        return "redirect:/tasks";
    }

    @Operation(summary = "delete a task id", description = "", tags = {"tasks"})
    @PostMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PreAuthorize("permitAll()")
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
//    @RolesAllowed({"ROLE_ADMIN"})
//    @Secured("ROLE_ADMIN")
//    @Secured({"role1", "role2"}) // is treated as an OR
    public String delete(@ModelAttribute("taskId") @NotNull String id) {
        repository.deleteById(Long.valueOf(id));
        return "redirect:/tasks";
    }


    private Authentication authenticate(String username, String password) throws Exception {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
