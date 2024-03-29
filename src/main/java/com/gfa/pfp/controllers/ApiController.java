package com.gfa.pfp.controllers;

import com.gfa.pfp.exception.PfpException;
import com.gfa.pfp.models.dto.admin.AdminGetIdDTO;
import com.gfa.pfp.models.dto.admin.GetEmailDTO;
import com.gfa.pfp.models.dto.authentication.AuthenticationResponseDTO;
import com.gfa.pfp.models.dto.authentication.LoginDTO;
import com.gfa.pfp.models.dto.authentication.RegisterDTO;
import com.gfa.pfp.security.auth.AuthenticationService;
import com.gfa.pfp.services.UserService;
import com.gfa.pfp.models.entities.user.UserResponseDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

  private final UserService userService;

  private final AuthenticationService authService;

  @PostMapping("/serv/register")
  public ResponseEntity<AuthenticationResponseDTO> registerUser(@RequestBody RegisterDTO request)
      throws PfpException {
    return ResponseEntity.ok(authService.createUser(request));
  }

  @DeleteMapping("/user/delete/{email}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<?> deleteUserByEmail(@PathVariable("email") String email,
                                             @RequestHeader String authorization)
      throws PfpException {
    userService.deleteUserByEmail(email, authorization);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/admin/delete/{adminId}/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> deleteUserByAdmin(@PathVariable("id") Long id,
                                             @PathVariable("adminId") Long adminId)
      throws PfpException {
    userService.deleteUserByIdByAdmin(id, adminId);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/serv/login")
  public ResponseEntity<AuthenticationResponseDTO> loginUser(@RequestBody LoginDTO loginDto)
      throws PfpException {
    return ResponseEntity.ok(authService.authenticate(loginDto));
  }

  @PostMapping("/admin/findId")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<AdminGetIdDTO> findUserId(@RequestBody GetEmailDTO emailDto) {
    return ResponseEntity.ok(userService.findUserIdByEmail(emailDto));
  }

  @GetMapping("/users")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<List<UserResponseDTO>> showAllUsers(@RequestHeader String authorization)
      throws PfpException {
    return ResponseEntity.ok().body(userService.findAllUsers(authorization));
  }

  @DeleteMapping("/logout")
  public ResponseEntity<?> logout(@RequestHeader String authorization) {
    return ResponseEntity.ok().body(authService.logout(authorization));
  }
}