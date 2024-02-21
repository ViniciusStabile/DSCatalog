package DSCatolog.services;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import DSCatolog.DTO.EmailDTO;
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

}
