package com.devForce.learning.service.impl;

import com.devForce.learning.model.dto.RespuestaDTO;
import com.devForce.learning.model.dto.UsuarioDTO;
import com.devForce.learning.model.dto.request.RegistroDTO;
import com.devForce.learning.model.entity.*;
import com.devForce.learning.repository.LicenciaRepository;
import com.devForce.learning.repository.RoleRepository;
import com.devForce.learning.repository.SolicitudRepository;
import com.devForce.learning.repository.UsuarioRepository;
import com.devForce.learning.security.jwt.JwtUtils;
import com.devForce.learning.service.AdminService;
import com.devForce.learning.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    SolicitudRepository solicitudRepository;

    @Autowired
    LicenciaRepository licenciaRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;


    /** Intenta crear un usuario a partir de un objeto usuario que viene del front
    @Param Usuario
     */
    @Override
    public ResponseEntity<RespuestaDTO> crearUsuario(RegistroDTO registroDTO) {
        if (usuarioRepository.existsByUsername(registroDTO.getUsername())) {
            return new ResponseEntity<>(new RespuestaDTO(false,"Usuario Ya Existe", null), HttpStatus.BAD_REQUEST);
        }

        if (usuarioRepository.existsByEmail(registroDTO.getEmail())) {
            return new ResponseEntity<>(new RespuestaDTO(false,"Email ya se encuentra en uso", null), HttpStatus.BAD_REQUEST);
        }

        // Create new user's account
        Usuario user = new Usuario(
                registroDTO.getNombre(),
                registroDTO.getApellido(),
                registroDTO.getUsername(),
                registroDTO.getEmail(),
                encoder.encode(registroDTO.getPassword()),
                registroDTO.getPhone(),
                registroDTO.getHasTeams(),
                registroDTO.getMentorArea());

        Set<String> strRoles = registroDTO.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USUARIO)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MENTOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USUARIO)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        usuarioRepository.save(user);
        UsuarioDTO contenido = usuarioService.crearUsuarioDTO(usuarioRepository.findByUsername(user.getUsername()).orElse(null));
        if (contenido == null){
            return new ResponseEntity<>(new RespuestaDTO(false,"Usuario no se registró correctamente", null), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new RespuestaDTO(true,"Usuario creado!",contenido),HttpStatus.OK);
    }



    /** Crea un Usuario nuevo en la base a partir de un objeto usuario
     @Param Usuario
     */
    @Override
    public void addUsuario(Usuario usuario) {
        Usuario newUsuario = new Usuario(
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getUsername(),
                usuario.getEmail(),
                usuario.getPassword(),
                usuario.getPhone(),
                //usuario.getRol(),
                usuario.getHasTeams(),
                usuario.getMentorArea()
        );
        usuarioRepository.save(newUsuario);
    }


    /**Intenta asignar una licencia a una solicitud.
     * Si el usuario ya tiene una solicitud con licencia activa del mismo tipo de solicitud, se le extiende el tiempo
     * de la licencia que ya tiene asignada
     @Param Solicitud
     */
    //TODO: Terminar asignarLicenciaMétodo (VERIFICAR que el usuario logueado sea admin)
    @Override
    public ResponseEntity<?> asignarLicencia(Solicitud solicitud) {

        // AGREGO ESTO PARA TESTING SOLAMENTE, DESPUES BORRAR
        solicitud.setEstado("PENDIENTE-ADMIN");
        System.out.println("El usuario de la solicitud es: " + solicitud.getUsuario());
        //

        if(solicitud.getEstado()!="PENDIENTE-ADMIN"){
            return new ResponseEntity<>(new RespuestaDTO(false,"Estado de solicitud incorrecto", null), HttpStatus.FORBIDDEN);
        }
        // Se buscan solicitudes aceptadas del mismo tipo de solicitud
        // verificar que la licencia no esté vencida. Si ya tiene una licencia activa, se extiende el tiempo de la misma
        List<Solicitud> solicitudesAceptadas = solicitudRepository.findByUsuarioAndTipoAndEstado(solicitud.getUsuario(), "Udemy", "ACEPTADA");
            for (Solicitud solicitudAux : solicitudesAceptadas){
                if (!solicitudAux.getLicencia().getVencimiento().isBefore(LocalDate.now())) {
                    return darLicencia(solicitud,solicitudAux);
                }
            }
        return darLicencia(solicitud,null);
    }

    /**Asigna una licencia a una solicitud
     * solicitudAux se utiliza para los casos donde hay que extender una licencia ya asignada al usuario.
     @Param Solicitud solicitud
     @Param Solicitud solicitudAux
     */
    private ResponseEntity<?> darLicencia (Solicitud solicitud, Solicitud solicitudAux){

        Licencia licencia;
        HttpStatus httpStatus = HttpStatus.CREATED;
        String mensaje;

        if (solicitudAux!=null){
            licencia = solicitudAux.getLicencia();
            httpStatus = HttpStatus.OK;
            mensaje = "Licencia extendida";
        }
        else {
            licencia = licenciaRepository.findFirstByEstadoOrderById("DISPONIBLE");
            mensaje = "Licencia asignada";
        }

        solicitud.setLicencia(licencia);
        solicitud.setEstado("ACEPTADA");
        solicitudRepository.save(solicitud);
        licencia.setEstado("ASIGNADA");
        asignarTiempo(solicitud);
        licenciaRepository.save(licencia);
        solicitud=solicitudRepository.findById(solicitud.getId());

        RespuestaDTO respuestaDTO = new RespuestaDTO();
        respuestaDTO.setMensaje(mensaje);
        respuestaDTO.setContenido(solicitud);
        respuestaDTO.setOk(true);

        return new ResponseEntity<>(respuestaDTO, httpStatus);
    }

    /** Le asigna el tiempo designado en la solicitud por el mentor a una licencia.
     * Si el usuario ya tiene una licencia que no expiró, se le extenderá el tiempo designado en la solicitud a la licencia
     * que ya posee asignada.
     @Param Solicitud solicitud
     */
    private void asignarTiempo(Solicitud solicitud){
        Licencia licenciaActual = licenciaRepository.findById(solicitud.getLicencia().getId());
        if(solicitud.getLicencia().getVencimiento()==null){
            licenciaActual.setVencimiento(LocalDate.now().plusDays(solicitud.getTiempoSolicitado()));
        }
        else{
            licenciaActual.setVencimiento(licenciaActual.getVencimiento().plusDays(solicitud.getTiempoSolicitado()));
        }
    }

}
