package DSCatolog.services;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import DSCatolog.DTO.EmailDTO;
import DSCatolog.DTO.NewPasswordDTO;
import DSCatolog.entities.PasswordRecover;
import DSCatolog.entities.User;
import DSCatolog.repositories.PasswordRecoverRepository;
import DSCatolog.repositories.UserRepository;
import DSCatolog.services.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class AuthService {

	@Value("${email.password-recover.token.minutes}")
	private Long tokenMinutes;
	
	@Value("${email.password-recover.uri}")
	private String recoverUri;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	PasswordRecoverRepository passwordRecoverRepository;
	
	@Autowired
	private EmailService emailService;
	
	
	
	@Transactional
	public void createRecoverToken(@Valid EmailDTO body) {
		
		User user = userRepository.findByEmail(body.getEmail());
		if (user == null) {
			throw new ResourceNotFoundException("Email não encontrado");
		}
		
		String token = UUID.randomUUID().toString();
		
		PasswordRecover entity = new PasswordRecover();
		entity.setEmail(body.getEmail());
		entity.setToken(token);
		entity.setExpiration(Instant.now().plusSeconds(tokenMinutes * 60L));
		entity = passwordRecoverRepository.save(entity);
		
		String text = "Acesse o link para definirr uma nova senha\n\n" + recoverUri + token;
		
		emailService.sendEmail(body.getEmail(), "Recupração de senha", text);
	}


	@Transactional
	public void saveNewPassword(NewPasswordDTO body) {
		
		List<PasswordRecover> result = passwordRecoverRepository.searchValidTokens(body.getToken(), Instant.now());
		if(result.size() == 0) {
			throw new ResourceNotFoundException("Token invalido");
		}
		
		User user = userRepository.findByEmail(result.get(0).getEmail());
		user.setPassword(passwordEncoder.encode(body.getPassword()));
		user = userRepository.save(user);
		
	}

}
