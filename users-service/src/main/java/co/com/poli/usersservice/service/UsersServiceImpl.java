package co.com.poli.usersservice.service;

import co.com.poli.usersservice.clientFeign.BookingsClient;
import co.com.poli.usersservice.persistence.entity.Users;
import co.com.poli.usersservice.persistence.repository.UsersRepository;
import co.com.poli.usersservice.service.dto.UsersInDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService{

    private final UsersRepository usersRepository;

    private final BookingsClient bookingsClient;

    @Override
    public Users save(UsersInDTO users) {
        Users usersEntity = new Users();
        usersEntity.setName(users.getName());
        usersEntity.setLastname(users.getLastname());

        return this.usersRepository.save(usersEntity);
    }

    @Override
    public String delete(Long idUsers) {
        Boolean servicioCaido = false;
        Boolean movieBookingsVacio = false;
        Optional<Users> users = this.usersRepository.findById(idUsers);

        if(!users.isEmpty()){
            String mensajeRespuesta =  "Esta abajo el servicio de bookings";
            int clienteExistente = bookingsClient.validarUserRegistrado(idUsers).getCode();
            if(clienteExistente == 404){
                movieBookingsVacio = true;
            }else if(clienteExistente == 503){
                servicioCaido = true;
            }

            if(!servicioCaido){
                if(movieBookingsVacio){
                    this.usersRepository.delete(users.get());
                    return "eliminado";
                }else{
                    mensajeRespuesta = "Existe registro en bookings para el user con id:" + idUsers;
                }
            }

            return mensajeRespuesta;
        }

        return "inexistente";
    }

    @Override
    public List<Users> findAll() {
        return this.usersRepository.findAll();
    }

    @Override
    public Users findById(Long id) {
        Optional<Users> user = this.usersRepository.findById(id);

        if(!user.isEmpty()){
            return user.get();
        }

        return null;
    }

}
