package com.devops.springboot_app_devops;

import com.devops.springboot_app_devops.model.Usuario;
import com.devops.springboot_app_devops.repository.UsuarioRepository;
import com.devops.springboot_app_devops.service.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository repo;

    @InjectMocks
    private UsuarioServiceImpl service;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setNombre("Juan Perez");
        usuario.setEmail("juan@test.com");
    }

    @Test
    void listar_retornaLista() {
        when(repo.findAll()).thenReturn(Arrays.asList(usuario));
        List<Usuario> result = service.listar();
        assertEquals(1, result.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    void obtenerPorId_existente_retornaUsuario() {
        when(repo.findById(1L)).thenReturn(Optional.of(usuario));
        Usuario result = service.obtenerPorId(1L);
        assertNotNull(result);
        assertEquals("Juan Perez", result.getNombre());
    }

    @Test
    void obtenerPorId_noExistente_retornaNull() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        Usuario result = service.obtenerPorId(99L);
        assertNull(result);
    }

    @Test
    void crear_guardaYRetornaUsuario() {
        when(repo.save(usuario)).thenReturn(usuario);
        Usuario result = service.crear(usuario);
        assertNotNull(result);
        assertEquals("juan@test.com", result.getEmail());
        verify(repo, times(1)).save(usuario);
    }

    @Test
    void actualizar_existente_actualizaYRetorna() {
        Usuario actualizado = new Usuario();
        actualizado.setNombre("Pedro");
        actualizado.setEmail("pedro@test.com");
        when(repo.findById(1L)).thenReturn(Optional.of(usuario));
        when(repo.save(any(Usuario.class))).thenReturn(usuario);
        Usuario result = service.actualizar(1L, actualizado);
        assertNotNull(result);
        verify(repo, times(1)).save(any(Usuario.class));
    }

    @Test
    void actualizar_noExistente_retornaNull() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        Usuario result = service.actualizar(99L, usuario);
        assertNull(result);
        verify(repo, never()).save(any());
    }

    @Test
    void eliminar_llamaDeleteById() {
        doNothing().when(repo).deleteById(1L);
        service.eliminar(1L);
        verify(repo, times(1)).deleteById(1L);
    }
}
