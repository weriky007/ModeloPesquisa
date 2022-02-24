package com.alphazer0.pesquisaexemplo.database.roomDAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.alphazer0.pesquisaexemplo.model.Cliente;
import java.util.List;

@Dao
public interface RoomClienteDAO {
    @Insert
    void salvaCliente(Cliente cliente);

    @Query("SELECT * FROM cliente")
    List<Cliente> todosClientes();

    @Delete
    void removeCliente(Cliente cliente);

    @Update
    void editaCliente(Cliente cliente);
}
