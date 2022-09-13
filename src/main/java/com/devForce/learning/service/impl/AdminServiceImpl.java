package com.devForce.learning.service.impl;

import com.devForce.learning.model.dto.RespuestaDTO;
import com.devForce.learning.model.entity.Licencia;
import com.devForce.learning.model.entity.Solicitud;
import com.devForce.learning.model.entity.Usuario;
import com.devForce.learning.repository.LicenciaRepository;
import com.devForce.learning.repository.SolicitudRepository;
import com.devForce.learning.repository.UsuarioRepository;
import com.devForce.learning.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    SolicitudRepository solicitudRepository;

    @Autowired
    LicenciaRepository licenciaRepository;


    /** Intenta crear un usuario a partir de un objeto usuario que viene del front
    @Param Usuario
     */
    @Override
    public ResponseEntity<RespuestaDTO> crearUsuario(Usuario usuario) {
        log.info("Intentando guardar usuario...");
        //TODO: Chequear que soy un usuario Admin
        Usuario usuarioYaExiste = usuarioRepository.findByNombreAndApellido(usuario.getNombre(), usuario.getApellido());
        if(usuarioYaExiste==null) {
            addUsuario(usuario);
            log.info("Usuario creado");
            return new ResponseEntity<>(
                    new RespuestaDTO(
                            true,
                            "Usuario Creado", usuarioRepository.findByNombreAndApellido(usuario.getNombre(),
                            usuario.getApellido())),
                    HttpStatus.CREATED);
        }
        else {
            log.info("Usuario no se ha podido crear - Duplicado");
            return new ResponseEntity<>(new RespuestaDTO(false,"Usuario Ya Existe", null), HttpStatus.FORBIDDEN);
        }
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
                usuario.getMail(),
                usuario.getPassword(),
                usuario.getPhone(),
                usuario.getRol().toUpperCase(),
                usuario.getHasTeams()
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

        if(!solicitud.getEstado().equals("PENDIENTE-ADMIN")){
            return new ResponseEntity<>(new RespuestaDTO(false,"Estado de solicitud incorrecto", null), HttpStatus.FORBIDDEN);
        }
        // Se buscan solicitudes aceptadas del mismo tipo de solicitud
        // verificar que la licencia no esté vencida. Si ya tiene una licencia activa, se extiende el tiempo de la misma
        List<Solicitud> solicitudesAceptadas = solicitudRepository.findByUsuarioAndTipoAndEstado(solicitud.getUsuario(), solicitud.getTipo(), "ACEPTADA");
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

    public List<Licencia> getLicencias (){
        return licenciaRepository.findAll();
    }

}
